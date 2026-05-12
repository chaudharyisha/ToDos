package com.code.springboot.todos.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expiration}")
    private long JWT_EXPIRATION;



    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private <T>T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
       final String username=extractUsername(token);
       return  (username.equals(userDetails.getUsername())) && !iisTokenExpired(token);

    }

    private boolean iisTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private  Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    @Override
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    private SecretKey getSigningKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
