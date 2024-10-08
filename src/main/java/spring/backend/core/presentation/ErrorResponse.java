package spring.backend.core.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

  private final String message;

  private final HttpStatus httpStatus;

  public static ErrorResponse of(String message, HttpStatus httpStatus) {
    return new ErrorResponse(message, httpStatus);
  }
}
