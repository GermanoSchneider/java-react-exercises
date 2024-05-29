package com.feefo.note_web_app_web_service.infrastructure.auth;

import static org.springframework.security.oauth2.jwt.JwtEncoderParameters.from;

import com.feefo.note_web_app_web_service.domain.TokenService;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Component;

@Component
class TokenAdapter implements TokenService {

  @Value("${spring.application.name}")
  private String issuer;

  private final JwtEncoder jwtEncoder;

  public TokenAdapter(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  @Override
  public String generate(String subject) {

    Instant now = Instant.now();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer(issuer)
        .issuedAt(now)
        .expiresAt(now.plusSeconds(3600L))
        .subject(subject)
        .build();

    return this.jwtEncoder.encode(from(claims)).getTokenValue();
  }
}
