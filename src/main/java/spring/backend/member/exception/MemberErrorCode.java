package spring.backend.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode<DomainException> {

    NOT_EXIST_CONDITION(HttpStatus.BAD_REQUEST, "요청 조건이 존재하지 않습니다."),
    ALREADY_REGISTERED_WITH_DIFFERENT_OAUTH2(HttpStatus.BAD_REQUEST, "이미 다른 소셜 로그인으로 가입된 계정입니다."),
    MEMBER_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 정보를 저장하는데 실패하였습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
