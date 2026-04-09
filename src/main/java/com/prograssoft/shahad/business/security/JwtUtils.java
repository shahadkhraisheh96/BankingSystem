package com.prograssoft.shahad.business.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {
    private final Key key;
    private final long expiration;

    public JwtUtils(String secret,
                    long expiration){
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.expiration = expiration;
    }
    public String generateToken(String username){
        Date now = new Date();
        return Jwts.builder().setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+expiration*1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
    }

