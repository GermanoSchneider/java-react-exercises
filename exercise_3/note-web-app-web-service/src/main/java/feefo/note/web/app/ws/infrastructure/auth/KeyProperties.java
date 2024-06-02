package feefo.note.web.app.ws.infrastructure.auth;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

// This record loads the private and public key used during the JWT token generation
@ConfigurationProperties(prefix = "rsa")
public record KeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) { }
