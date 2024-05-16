package com.tokioFilme.exception;

public class ImageUploadException extends RuntimeException {

  public ImageUploadException(String message) {
  }

  public ImageUploadException(String message, Throwable cause) {
    super(message, cause);
  }
}
