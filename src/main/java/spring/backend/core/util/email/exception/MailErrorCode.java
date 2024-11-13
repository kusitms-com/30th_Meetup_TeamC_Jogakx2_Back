package spring.backend.core.util.email.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum MailErrorCode implements BaseErrorCode<DomainException> {

    FAILED_TO_PARSE_MAIL(HttpStatus.BAD_REQUEST, "메일 파싱 중 오류가 발생했습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "메일 서버 인증에 실패했습니다."),
    ERROR_OCCURRED_SENDING_MAIL(HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송 중 오류가 발생했습니다."),
    GENERAL_MAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "메일 처리 중 예기치 않은 오류가 발생했습니다."),
    INVALID_MAIL_ADDRESS(HttpStatus.BAD_REQUEST, "올바르지 않은 이메일 주소입니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
