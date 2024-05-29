package com.feefo.note_web_app_web_service.infrastructure.auth;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
public record KeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) { }
