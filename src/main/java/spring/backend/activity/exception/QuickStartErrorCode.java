package spring.backend.activity.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum QuickStartErrorCode implements BaseErrorCode<DomainException> {

    QUICK_START_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "빠른 시작 정보를 저장하는데 실패하였습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
