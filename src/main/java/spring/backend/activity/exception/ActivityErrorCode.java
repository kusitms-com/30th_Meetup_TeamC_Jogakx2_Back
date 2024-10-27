package spring.backend.activity.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum ActivityErrorCode implements BaseErrorCode<DomainException> {

    ACTIVITY_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "활동을 저장하는데 실패하였습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
