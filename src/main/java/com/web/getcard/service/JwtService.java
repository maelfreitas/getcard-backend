package com.web.getcard.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtService {

    public UUID extractUserId(String token) {
        String jwt = token.replace("Bearer ", "");

        DecodedJWT decodedJWT = JWT.decode(jwt);
        String userId = decodedJWT.getSubject();

        return UUID.fromString(userId);
    }
}

