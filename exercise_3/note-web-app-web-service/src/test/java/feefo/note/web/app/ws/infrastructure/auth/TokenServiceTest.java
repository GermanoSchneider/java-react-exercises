package feefo.note.web.app.ws.infrastructure.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.RS256;

import feefo.note.web.app.ws.domain.TokenService;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@SpringBootTest(classes = {TokenAdapter.class, JwtEncoder.class})
public class TokenServiceTest {

  @Autowired
  private TokenService tokenService;

  @MockBean
  private JwtEncoder encoder;

  @Captor
  ArgumentCaptor<JwtEncoderParameters> jwtEncoderParametersArgumentCaptor;

  @Test
  void shouldGenerateANewToken() {

    String subject = "dummy subject";

    Instant now = Instant.now();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("note-web-app-web-service")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(3600L))
        .subject(subject)
        .build();

    String expectedToken = "token";

    Jwt jwt = new Jwt(
        expectedToken,
        now,
        now.plusSeconds(3600L),
        JwsHeader.with(RS256).build().getHeaders(),
        claims.getClaims()
    );

    doReturn(jwt)
        .when(encoder)
        .encode(jwtEncoderParametersArgumentCaptor.capture());

    String token = tokenService.generateTokenWith(subject);

    assertThat(expectedToken).isEqualTo(token);
  }
}
