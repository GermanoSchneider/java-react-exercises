package feefo.note.web.app.ws.infrastructure.auth;

import static org.springframework.security.config.Customizer.withDefaults;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(KeyProperties.class)
public class SecurityConfig  {

    private final UserDetailsService userDetailsService;

    private final KeyProperties keyProperties;

    public SecurityConfig(UserDetailsService userDetailsService, KeyProperties keyProperties) {
        this.userDetailsService = userDetailsService;
        this.keyProperties = keyProperties;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Disable CSRF

        http.csrf(AbstractHttpConfigurer::disable);

        // Apply CORS configuration

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // Set requests visibility (non-authenticated and authenticated)

        http.authorizeHttpRequests((auth) ->
            auth.requestMatchers("/auth/user")
                .permitAll()
                .anyRequest()
                .authenticated()
        );

        // Set that it will use the JWT configuration from Spring Security

        http.oauth2ResourceServer(oauth -> oauth.jwt(withDefaults()));

        // Set that it won't store session

        http.sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Set it will use the basic auth configuration

        http.httpBasic(withDefaults());

        // Set the user details configuration

        http.userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {

        // Authentication provider will load user data from database

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);

        // Password will be encoded with the defined password encoder bean in this class

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        // Enable CORS to the following configuration:

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3001"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT"));
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Bean to decode JWT
    @Bean
    public JwtDecoder jwtDecoder() {

        return NimbusJwtDecoder
            .withPublicKey(keyProperties.publicKey())
            .build();
    }

    // Bean to encode JWT
    @Bean
    public JwtEncoder jwtEncoder() {

        JWK jwk = new RSAKey.Builder(keyProperties.publicKey())
            .privateKey(keyProperties.privateKey())
            .build();

        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }

}
