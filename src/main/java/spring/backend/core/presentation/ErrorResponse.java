package spring.backend.core.presentation;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import spring.backend.core.exception.DomainException;

@Getter
public class ErrorResponse extends BaseResponse {

  private final int statusCode;

  private final String code;

  private final String message;

  @Builder(builderClassName = "CreateErrorResponse", builderMethodName = "createErrorResponse")
  public ErrorResponse(int statusCode, Exception exception) {
    super(false, LocalDateTime.now());
    this.statusCode = statusCode;
    this.code = exception.getClass().getSimpleName();
    this.message = exception.getMessage();
  }

  @Builder(builderClassName = "CreateDomainErrorResponse", builderMethodName = "createDomainErrorResponse")
  public ErrorResponse(int statusCode, DomainException exception) {
    super(false, LocalDateTime.now());
    this.statusCode = statusCode;
    this.code = exception.getCode();
    this.message = exception.getMessage();
  }
}
