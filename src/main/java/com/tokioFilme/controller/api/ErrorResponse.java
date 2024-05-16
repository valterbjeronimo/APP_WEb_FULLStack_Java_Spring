package com.tokioFilme.controller.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

  @Schema(description = "Time error was raised", example = "2021-04-04T12:42:05")
  private String timestamp;
  @Schema(description = "HTTP Status code returned", example = "400")
  private int status;
  @Schema(description = "Brief description of the error", example = "Bad Request")
  private String error;
  @Schema(description = "Message from server with information regarding the error", example = "This is an error message.")
  private String message;
  @Schema(description = "Path of request which raised the error", example = "/path/of/request")
  private String path;

  public ErrorResponse(LocalDateTime time, HttpStatus status, String message, String path) {
    this.timestamp = time.toString();
    this.status = status.value();
    this.error = status.getReasonPhrase();
    this.message = message;
    this.path = path;
  }

}
