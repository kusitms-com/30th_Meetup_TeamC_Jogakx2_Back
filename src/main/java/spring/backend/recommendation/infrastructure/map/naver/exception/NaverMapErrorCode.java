package spring.backend.recommendation.infrastructure.map.naver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum NaverMapErrorCode implements BaseErrorCode<DomainException> {
    API_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "API 요청과 응답이 실패했습니다."),
    FAILED_TO_CONNECT_API(HttpStatus.INTERNAL_SERVER_ERROR, "API 연결에 실패했습니다."),
    FAILED_TO_READ_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "API 응답을 읽는데 실패했습니다."),
    FAILED_TO_PARSE_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "응답을 파싱하는데 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
