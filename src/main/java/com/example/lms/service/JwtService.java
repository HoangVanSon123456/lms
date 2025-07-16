package com.example.lms.service;

import com.example.lms.entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.signerKey}")
    protected String signerKey;

    public  String generateToken(User user) {
        // Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Payload (Claims)
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getEmail()) //ai là chủ token này
                .issuer("com.example.lms") //ai phát hành
                .issueTime(new Date()) // thời điểm phát hành.
                .expirationTime(Date.from(Instant.now().plusSeconds(3600))) //khi nào hết hạn.
                .claim("scope", buildScope(user)) // trả về quyền của user
                .jwtID(UUID.randomUUID().toString()) //định danh duy nhất của token
                .build();

        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    private String buildScope(User user) {
        return user.getUserRoles().stream()
                .map(ur -> ur.getRole().getName())
                .collect(Collectors.joining(" "));
    }

    public String extractSubject(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

            if (!signedJWT.verify(verifier)) {
                throw new RuntimeException("Invalid JWT signature");
            }

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expirationTime.before(new Date())) {
                throw new RuntimeException("JWT expired");
            }

            return signedJWT.getJWTClaimsSet().getSubject();

        } catch (Exception e) {
            throw new RuntimeException("Error parsing JWT", e);
        }
    }

}
