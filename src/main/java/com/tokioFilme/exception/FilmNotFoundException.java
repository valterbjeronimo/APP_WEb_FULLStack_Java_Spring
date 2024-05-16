package com.tokioFilme.exception;

public class FilmNotFoundException extends RuntimeException {

  public FilmNotFoundException() {
    super("Film not found.");
  }
}
