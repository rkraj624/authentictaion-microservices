package com.example.springsecurity.controller;

import com.example.springsecurity.entity.User;
import com.example.springsecurity.jwt.utils.JWTUtils;
import com.example.springsecurity.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/users")
    User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    String getUser(){
        try{
            return "Hello Ravi";
        }catch (Exception exception){
            throw exception;
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    String getAdmin(){
        return "Hello Admin";
    }

    @PostMapping("/signin")
    ResponseEntity<?> signIn(@RequestBody User user){
        Authentication authentication ;
        Map<String, Object> response = new HashMap<>();
        try{
            authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),
                            user.getPassword()));

        }catch (AuthenticationException ex){
            response.put("status", HttpStatus.UNAUTHORIZED);
            response.put("message", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtFromUserName(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
        response.put("token", jwt);
        response.put("roles", roles);
        return ResponseEntity.ok(response);
    }
}
