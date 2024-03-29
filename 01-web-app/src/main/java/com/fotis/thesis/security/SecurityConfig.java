package com.fotis.thesis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

@Bean
public UserDetailsManager userDetailsManager(DataSource dataSource) {
  return new JdbcUserDetailsManager(dataSource);
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http.authorizeHttpRequests(configurer ->
    configurer
      .requestMatchers("/admin/**").hasRole("ADMIN")
      .requestMatchers("/webjars/**").permitAll()
      .requestMatchers("/cars/**").permitAll()
      .requestMatchers("/images/**").permitAll()
      .requestMatchers("/css/**").permitAll()
      .requestMatchers("/js/**").permitAll()
      .requestMatchers("/userTimeSpent/**").permitAll()
      .anyRequest().authenticated()
  ).formLogin(form ->
    form
      .loginPage("/showLoginPage")
      .loginProcessingUrl("/authenticateTheUser")
      .defaultSuccessUrl("/cars", true)
      .permitAll()
  ).logout(LogoutConfigurer::permitAll
  );

  return http.build();
}
}
