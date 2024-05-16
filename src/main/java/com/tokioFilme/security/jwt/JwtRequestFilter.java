package com.tokioFilme.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsService jwtUserDetailsService;

  public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService jwtUserDetailsService) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.jwtUserDetailsService = jwtUserDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");
    if (requestTokenHeader != null) {
      if (requestTokenHeader.startsWith("Bearer ")) {
        log.info("Authenticating JWT authentication request");
        String jwtToken = requestTokenHeader.substring(7);
        try {
          String username = jwtTokenUtil.getUsername(jwtToken);
          if (!username.isEmpty()
            && null == SecurityContextHolder.getContext().getAuthentication()) {
            log.info("Authenticating User {} JWT auth request", username);
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
              usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
            }
          }
        } catch (IllegalArgumentException e) {
          logger.error("Unable to fetch JWT Token");
        } catch (ExpiredJwtException e) {
          logger.error("JWT Token is expired");
        } catch (Exception e) {
          logger.error(e.getMessage());
        }
      } else {
        logger.warn("JWT Token does not begin with Bearer String");
      }
    }
    chain.doFilter(request, response);
  }

}

