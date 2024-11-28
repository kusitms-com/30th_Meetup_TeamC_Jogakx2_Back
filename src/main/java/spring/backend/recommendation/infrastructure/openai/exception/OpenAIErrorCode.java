package spring.backend.recommendation.infrastructure.openai.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum OpenAIErrorCode implements BaseErrorCode<DomainException> {

    NOT_FOUND_RECOMMENDATION(HttpStatus.NOT_FOUND, "OpenAI에서의 추천이 존재하지 않습니다."),
    FAILED_RECOMMENDATION_GENERATION(HttpStatus.INTERNAL_SERVER_ERROR, "추천 생성 중 에러가 발생하였습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
