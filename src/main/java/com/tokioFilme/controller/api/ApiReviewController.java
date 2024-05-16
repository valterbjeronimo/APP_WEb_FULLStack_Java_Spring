package com.tokioFilme.controller.api;

import com.tokioFilme.domain.Review;
import com.tokioFilme.domain.dto.ReviewDTO;
import com.tokioFilme.service.FilmService;
import com.tokioFilme.service.ReviewService;
import com.tokioFilme.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/review")
@Slf4j
@Tag(name = "Reviews", description = "POST and GET User Reviews of Films")
public class ApiReviewController {

  private final ReviewService reviewService;
  private final UserService userService;
  private final FilmService filmService;
  private final ModelMapper modelMapper;

  public ApiReviewController(ReviewService reviewService,
                             UserService userService,
                             FilmService filmService,
                             ModelMapper modelMapper) {
    this.reviewService = reviewService;
    this.userService = userService;
    this.filmService = filmService;
    this.modelMapper = modelMapper;
  }

  @Operation(description = "Create new Review of Film belonging to Authenticated User")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Successfully created",
    content = @Content(schema = @Schema(implementation = ReviewDTO.class),
      mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "409", description = "Review already Created for Film by User",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
      mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "401", description = "Unauthorized",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class),
        mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "500", description = "Generic Error",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping(path = "/new", consumes = "application/json", produces = {"application/json", "text/xml"})
  public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO, Principal principal) {
    if (!reviewDTO.getUser().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }
    Review received = convertToEntity(reviewDTO);
    Review added = reviewService.addReview(received);
    ReviewDTO addedDTO = convertToDto(added);
    return ResponseEntity.status(HttpStatus.CREATED).body(addedDTO);
  }

  @Operation(description = "GET all Reviews belonging to authenticated User")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success!",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReviewDTO.class)),
        mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "401", description = "Unauthorized",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class),
        mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "500", description = "Generic Error",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class),
        mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(path = "/user/{username}")
  public ResponseEntity<?> getUserReviews(@PathVariable String username, Authentication auth) {
    if (!username.equals(auth.getName())) {
      if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
      }
    }
    Set<Review> userReviews = reviewService.findByUsername(username);
    return ResponseEntity.ok(convertToDtos(userReviews));
  }


  private Review convertToEntity(ReviewDTO reviewDTO) {
    Review review = modelMapper.map(reviewDTO, Review.class);
    review.setTextReview(reviewDTO.getText());
    review.setUser(userService.getUser(reviewDTO.getUser()));
    review.setFilm(filmService.findByTitleExact(reviewDTO.getFilm()));
    return review;
  }

  private ReviewDTO convertToDto(Review review) {
    ReviewDTO dto = modelMapper.map(review, ReviewDTO.class);
    dto.setFilm(review.getFilm().getTitle());
    return dto;
  }

  private Set<ReviewDTO> convertToDtos(Set<Review> reviews) {
    return reviews.stream().map(this::convertToDto).collect(Collectors.toSet());
  }





}
