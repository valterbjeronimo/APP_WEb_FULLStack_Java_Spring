package com.tokioFilme.controller.api;

import com.tokioFilme.exception.UnauthorizedException;
import com.tokioFilme.security.jwt.JwtRequest;
import com.tokioFilme.security.jwt.JwtResponse;
import com.tokioFilme.security.jwt.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Authentication", description = "Entry Point for Authentication with RESTful API")
public class ApiAuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtTokenUtil jwtTokenUtil;

  public ApiAuthenticationController(AuthenticationManager authenticationManager,
                                     UserDetailsService userDetailsService,
                                     JwtTokenUtil jwtTokenUtil)
  {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Operation(summary = "Request Authentication Token for Provided Credentials")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Authentication Successful",
    content = @Content(schema = @Schema(implementation = JwtResponse.class),
      mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "401", description = "Unauthorized",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
      mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "500", description = "Generic Error",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
      mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @PostMapping(path = "/auth", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> login(@RequestBody JwtRequest authRequest) throws Exception
  {
    log.info("BEGIN login for username: {}", authRequest.getUsername());
    authenticate(authRequest.getUsername(), authRequest.getPassword());
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
    final String jwtToken = jwtTokenUtil.generateToken(userDetails);
    log.info("SUCCESS login:: token - {}", jwtToken);
    return ResponseEntity.ok(new JwtResponse(jwtToken));
  }

  private void authenticate(String username, String password) throws Exception
  {
    try {
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
      authenticationManager.authenticate(authenticationToken);
    } catch (DisabledException de) {
      throw new RuntimeException("User disabled", de);
    } catch (BadCredentialsException bce) {
      throw new UnauthorizedException("Bad credentials", bce);
    }
  }
}
