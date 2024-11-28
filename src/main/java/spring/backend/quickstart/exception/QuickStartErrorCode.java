package spring.backend.quickstart.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum QuickStartErrorCode implements BaseErrorCode<DomainException> {

    QUICK_START_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "빠른 시작 정보를 저장하는데 실패하였습니다."),
    NOT_EXIST_QUICK_START_CONDITION(HttpStatus.BAD_REQUEST, "빠른 시작 요청 조건이 유효하지 않습니다."),
    NOT_EXIST_QUICK_START(HttpStatus.BAD_REQUEST, "빠른 시작이 존재하지 않습니다."),
    MEMBER_ID_MISMATCH(HttpStatus.FORBIDDEN, "빠른 시작과 멤버 ID가 일치하지 않습니다."),
    START_TIME_CONVERSION_FAILED(HttpStatus.BAD_REQUEST, "빠른 시작 시작 시간 변환에 실패하였습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
