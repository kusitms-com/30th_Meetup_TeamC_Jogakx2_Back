package spring.backend.core.presentation;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

  private boolean success;

  private int status;

  private T body;

  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();

  public static ApiResponse of(boolean success, int status, Object body) {
    return ApiResponse.builder()
        .success(success)
        .status(status)
        .body(body)
        .build();
  }
}