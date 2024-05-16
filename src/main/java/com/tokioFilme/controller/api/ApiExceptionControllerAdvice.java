package com.tokioFilme.controller.api;

import com.tokioFilme.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


public class ApiExceptionControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handelGenericError(HttpServletRequest request, Exception ex) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatusException(HttpServletRequest request, ResponseStatusException ex) {
    return buildResponse(ex.getStatus(), ex, request);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorizedException(HttpServletRequest request, UnauthorizedException ex) {
    return buildResponse(HttpStatus.UNAUTHORIZED, ex, request);
  }

  @ExceptionHandler({UserNotFoundException.class, FilmNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleNotFoundExceptions(HttpServletRequest request, Exception ex) {
    return buildResponse(HttpStatus.NOT_FOUND, ex, request);
  }

  @ExceptionHandler({UsernameAlreadyExistsException.class, ReviewAlreadyExistsException.class})
  public ResponseEntity<ErrorResponse> handleAlreadyExistsException(HttpServletRequest request, Exception ex) {
    return buildResponse(HttpStatus.CONFLICT, ex, request);
  }

  private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, Exception exception, HttpServletRequest request) {
    return ResponseEntity.status(status).body(
      new ErrorResponse(LocalDateTime.now(), status, exception.getMessage(), request.getContextPath())
    );
  }


}
