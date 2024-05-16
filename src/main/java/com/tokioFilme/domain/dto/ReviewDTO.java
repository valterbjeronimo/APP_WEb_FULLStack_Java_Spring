package com.tokioFilme.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO {
  @Schema(description = "Title of Review", example = "A Really Good Film")
  private String title;
  @Schema(description = "Review text", example = "When I watched this film I was amazed!...")
  private String text;
  @Schema(description = "Date of Review creation", example = "2021-02-02")
  private LocalDate date;
  @Schema(description = "Username of Review owner", example = "username")
  private String user;
  @Schema(description = "Title of Film reviewed", example = "A Really Good Film")
  private String film;
}
