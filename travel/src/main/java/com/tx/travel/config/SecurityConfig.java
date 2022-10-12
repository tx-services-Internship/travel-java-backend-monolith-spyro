/*
 * Copyright (C) Tamedia AG 2021
 */

package com.tx.travel.config;

import com.tx.travel.commons.oauth.config.BaseSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  /** ResourceServerConfiguration. */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    BaseSecurityConfig.apply(http);
    return http.build();
  }
}
