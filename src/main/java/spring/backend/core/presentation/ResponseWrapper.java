package spring.backend.core.presentation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "spring.backend")
@Log4j2
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    log.info("execute AOP - supports");
    log.info("execute AOP - returnType :: {}", returnType);
    log.info("execute AOP - converterType :: {}", converterType);
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {
    log.info("execute AOP - beforeBodyWrite");
    HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
    int status = servletResponse.getStatus();
    if (body instanceof ErrorResponse) {
      HttpStatus errorStatus = ((ErrorResponse) body).getHttpStatus();
      return ApiResponse.of(false, errorStatus.value(), body);
    }
    return ApiResponse.of(true, status, body);
  }
}
