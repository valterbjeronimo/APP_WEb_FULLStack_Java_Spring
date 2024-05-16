package com.tokioFilme.security;

import com.tokioFilme.domain.User;
import com.tokioFilme.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SuccessfulAuthenticationEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

  private final UserService userService;

  public SuccessfulAuthenticationEventListener(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    User user = (User) event.getAuthentication().getPrincipal();
    log.info("User {} successfully logged in", user.getUsername());
    userService.userLoggedIn(user);
  }
}
