package com.feefo.note_web_app_web_service;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class SecurityConfigTest {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      http.csrf(AbstractHttpConfigurer::disable);

      http.authorizeHttpRequests((auth) ->
          auth.requestMatchers("/auth/user")
              .permitAll()
              .anyRequest()
              .authenticated()
      );

      http.sessionManagement(session ->
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );

      http.httpBasic(withDefaults());

      http.userDetailsService(userDetailsService());

      return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

      User.UserBuilder userBuilder = User.builder();

      UserDetails user = userBuilder.username("john")
          .password("1234")
          .build();

      return new InMemoryUserDetailsManager(user);
    }
}
