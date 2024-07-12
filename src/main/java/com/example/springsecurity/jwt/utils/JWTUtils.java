package com.example.springsecurity.jwt.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.jwtExpirationMs}")
    private long jwtExpirationMs;
    Logger logger = LoggerFactory.getLogger(JWTUtils.class);
    public String getJwtFromHeader(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");
        logger.debug("bearer token "+bearerToken);
        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
    public String generateJwtFromUserName(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();

    }
    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUserNameFromToken(String token){
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    public boolean validateToken(String token){
        try{
            logger.info("Validating token");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
            return true;
        }catch (Exception exception){
            logger.error(exception.getMessage(), exception);
        }
        return false;
    }
}
