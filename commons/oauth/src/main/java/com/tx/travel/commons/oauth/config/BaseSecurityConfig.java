/*
 * Copyright (C) Tamedia AG 2021
 */

package com.tx.travel.commons.oauth.config;

import lombok.experimental.UtilityClass;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@UtilityClass
public class BaseSecurityConfig {

  /**
   * Applies security configuration common for a spring boot microservice with resource server
   * functionality.
   */
  public void apply(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .cors()
        .and()
        .authorizeRequests()
        .antMatchers("/health", "/info", "/prometheus")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  }
}
