package com.example.springsecurity.security;

import com.example.springsecurity.jwt.utils.JWTUtils;
import com.example.springsecurity.jwt.utils.security.AuthEntryPointJWT;
import com.example.springsecurity.jwt.utils.security.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private JWTUtils jwtUtils;
    private final AuthEntryPointJWT authEntryPointJWT;
    private UserDetailsService userDetailsService;
    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter(jwtUtils,userDetailsService);
    }

    public SecurityConfig(AuthEntryPointJWT authEntryPointJWT) {
        this.authEntryPointJWT = authEntryPointJWT;
    }
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/signin").permitAll()
                    .anyRequest().authenticated();
        });
        http.exceptionHandling(exceptionHandlingConfigurer->exceptionHandlingConfigurer.authenticationEntryPoint(authEntryPointJWT) );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin = User.withUsername("ravi")
                .password(passwordEncoder().encode("ravi"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("raj")
                .password(passwordEncoder().encode("ravi"))
                .roles("USER")
                .build();
        UserDetails normalUserDetails = User.withUsername("raja")
                .password(passwordEncoder().encode("ravi"))
                .build();
        return new InMemoryUserDetailsManager(user,admin,normalUserDetails);
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
