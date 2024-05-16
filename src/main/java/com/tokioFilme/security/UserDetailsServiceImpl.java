package com.tokioFilme.security;

import com.tokioFilme.exception.UserNotFoundException;
import com.tokioFilme.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {


  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      log.info("Fetching UserDetails for {}", username);
      return userService.getUser(username);

    } catch (UserNotFoundException e) {
      logger.error("User {} not found", username, e);
      throw new UsernameNotFoundException(e.getMessage(), e);
    }
  }

}
