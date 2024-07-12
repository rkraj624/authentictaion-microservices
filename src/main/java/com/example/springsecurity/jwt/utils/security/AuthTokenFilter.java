package com.example.springsecurity.jwt.utils.security;

import com.example.springsecurity.jwt.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    public AuthTokenFilter(JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Auth Token Filter called for Url : " + request.getRequestURI());
        try {
            String jwtToken = parseJwt(request);
            if(StringUtils.hasLength(jwtToken) && jwtUtils.validateToken(jwtToken)){
                String userName = jwtUtils.getUserNameFromToken(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                logger.debug("Role from jwt : {}",userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception exception){
            logger.error("Cannot set user authentication: {}", exception);
        }
        filterChain.doFilter(request,response);
    }
    private String parseJwt(HttpServletRequest request){
        String jwtFromHeader = jwtUtils.getJwtFromHeader(request);
        logger.debug(this.getClass()+jwtFromHeader);
        return jwtFromHeader;
    }
}
