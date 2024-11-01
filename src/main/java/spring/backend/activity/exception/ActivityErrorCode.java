package spring.backend.activity.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum ActivityErrorCode implements BaseErrorCode<DomainException> {

    ACTIVITY_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "활동을 저장하는데 실패하였습니다."),
    NOT_EXIST_ACTIVITY(HttpStatus.BAD_REQUEST, "활동이 존재하지 않습니다."),
    MEMBER_ID_MISMATCH(HttpStatus.FORBIDDEN, "활동과 멤버 ID가 일치하지 않습니다."),
    INVALID_ACTIVITY_DURATION(HttpStatus.BAD_REQUEST, "활동 지속 시간이 허용된 범위를 초과했습니다."),
    ALREADY_FINISHED_ACTIVITY(HttpStatus.BAD_REQUEST, "이미 종료된 활동입니다."),
    NOT_EXIST_ACTIVITY_CONDITION(HttpStatus.BAD_REQUEST, "요청이 비어있습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
