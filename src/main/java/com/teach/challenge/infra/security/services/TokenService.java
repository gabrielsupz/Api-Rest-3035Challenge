package com.teach.challenge.infra.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.teach.challenge.domain.models.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String gerarToken(User user) {
        return JWT.create().withIssuer("teach")
                .withSubject(user.getUserName())
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(60)
                        .toInstant(ZoneOffset.of("-03:00"))

                ).sign(Algorithm.HMAC256("secret"));

    }

    public String getSubject(String TokenJWT) {

        try {

            return JWT.require(Algorithm.HMAC256("secret"))
                    .withIssuer("teach")
                    .build()
                    .verify(TokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token ivalido ou expirado");
        }
    }

    public Long getID(String TokenJWT) {

        try {

            Claim idClaim = JWT.require(Algorithm.HMAC256("secret"))
                    .withIssuer("teach")
                    .build()
                    .verify(TokenJWT)
                    .getClaim("id");


            var id = idClaim.asLong();

            return id;

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token ivalido ou expirado");
        }
    }

  public String retriveToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
