package com.jwt.springsecurity.services.impl;

import com.jwt.springsecurity.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

   public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSiginKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24*7))
                .signWith(getSiginKey(), SignatureAlgorithm.HS384)
                .compact();
    }



    private <T> T exstractClaim(String token, Function<Claims,T> claimsResolvers){
        final Claims claims = exstractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private  Claims exstractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUserName(String token){
        return exstractClaim(token,Claims::getSubject);
    }
    private Key getSiginKey(){
        byte [] key = Decoders.BASE64.decode("bXkgamF2YSBzcHJpbmdib290IHByb2plY3Qgand0IGF1dGhlbnRpY2F0aW9uIHdpdGggcmVmZXJzaCB0b2tlbg==");
        return Keys.hmacShaKeyFor(key);
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return exstractClaim(token,Claims :: getExpiration).before(new Date());
    }


}
