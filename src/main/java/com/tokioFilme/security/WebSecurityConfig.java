package com.tokioFilme.security;

import com.tokioFilme.security.jwt.JwtAuthenticationEntryPoint;
import com.tokioFilme.security.jwt.JwtRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.tokioFilme.util.Constants.*;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtRequestFilter jwtRequestFilter;

  public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,
                           JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                           JwtRequestFilter jwtRequestFilter)
  {
    this.userDetailsService = userDetailsService;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf()
        .disable()
      .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/register").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/film/**").permitAll()
        .antMatchers("/films/**").permitAll()
        .antMatchers("/search").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers("/v3/api-docs/**" ,"/swagger-ui/**", "swagger-ui.html").permitAll()
        .antMatchers("/admin/**").hasAnyAuthority(ADMIN_ROLE)
        .anyRequest().authenticated()
    .and()
      .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
    .and()
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
    .and()
      .formLogin()
        .loginPage(LOGIN_URL)
        .loginProcessingUrl(LOGIN_URL)
        .successForwardUrl(LOGIN_SUCCESS_URL)
        .failureUrl(LOGIN_FAILURE)
    .and()
      .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
        .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
    .and()
      .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
      // in order to allow access to h2-console for testing
      // TODO remove before release
      .headers().frameOptions().disable();

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(
      "/resources/**",
      "/static/**",
      "/templates/**",
      "/images/**",
      "/styles/**",
      "/css/**",
      "/js/**",
      "/fonts/**",
      "/webjars/**",
      "/api/auth"
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(-1);
  }
}
