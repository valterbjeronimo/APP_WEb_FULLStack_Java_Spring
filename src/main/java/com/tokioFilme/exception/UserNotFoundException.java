package com.tokioFilme.exception;

public class UserNotFoundException extends RuntimeException {

  private final static String message = "username or password incorrect";

  public UserNotFoundException() {
    super(message);
  }


  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
