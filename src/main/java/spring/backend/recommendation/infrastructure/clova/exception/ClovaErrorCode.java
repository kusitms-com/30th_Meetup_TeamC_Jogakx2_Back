package spring.backend.recommendation.infrastructure.clova.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum ClovaErrorCode implements BaseErrorCode<DomainException> {

    NOT_EXIST_LOCATION_WHEN_OFFLINE(HttpStatus.BAD_REQUEST, "오프라인의 경우 위치 정보가 필수입니다."),
    NO_RESPONSE_FROM_CLOVA(HttpStatus.INTERNAL_SERVER_ERROR, "클로바 서버로부터 응답이 없습니다."),
    NULL_RESPONSE_FROM_CLOVA(HttpStatus.INTERNAL_SERVER_ERROR, "클로바 서버로부터 NULL값을 받았습니다.");
    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
