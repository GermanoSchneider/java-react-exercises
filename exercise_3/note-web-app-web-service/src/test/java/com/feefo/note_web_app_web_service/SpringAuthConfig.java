package com.feefo.note_web_app_web_service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class SpringAuthConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      http.authorizeHttpRequests(request -> request
              .requestMatchers("/notes/**")
              .authenticated())
          .httpBasic(Customizer.withDefaults())
          .csrf(AbstractHttpConfigurer::disable)
          .userDetailsService(userDetailsService());

      return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
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
