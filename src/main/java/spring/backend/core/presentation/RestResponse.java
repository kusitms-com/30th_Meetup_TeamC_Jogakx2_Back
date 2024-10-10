package spring.backend.core.presentation;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RestResponse<T> extends BaseResponse {

  private final T data;

  public RestResponse(T data) {
    super(true, LocalDateTime.now());
    this.data = data;
  }
}
