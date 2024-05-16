package com.tokioFilme.exception;

public class ReviewAlreadyExistsException extends RuntimeException {

  public ReviewAlreadyExistsException(String username, String filmTitle) {
    super(String.format("User %s has already reviewed %s", username, filmTitle));
  }
}
