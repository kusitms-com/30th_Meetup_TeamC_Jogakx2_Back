package spring.backend.recommendation.infrastructure.map.kakao.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum KakaoMapErrorCode implements BaseErrorCode<DomainException> {
    RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 지도 API 응답에 오류가 발생했습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 지도 API 요청 중 알 수 없는 오류가 발생했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
