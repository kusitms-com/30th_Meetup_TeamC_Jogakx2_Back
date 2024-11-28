package spring.backend.recommendation.infrastructure.clova.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum ClovaErrorCode implements BaseErrorCode<DomainException> {

    NO_RESPONSE_FROM_CLOVA(HttpStatus.INTERNAL_SERVER_ERROR, "클로바 서버로부터 응답이 없습니다."),
    NULL_RESPONSE_FROM_CLOVA(HttpStatus.INTERNAL_SERVER_ERROR, "클로바 서버로부터 NULL값을 받았습니다."),
    OFFLINE_TYPE_CONTAIN_SOCIAL(HttpStatus.BAD_REQUEST, "선호하는 활동 타입이 OFFLINE인 경우, SOCIAL(소셜) 키워드를 사용할 수 없습니다."),
    INVALID_KEYWORD_IN_RECOMMENDATIONS(HttpStatus.BAD_REQUEST, "추천 활동의 키워드가 올바르지 않습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
