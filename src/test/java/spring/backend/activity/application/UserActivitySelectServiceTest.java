package spring.backend.activity.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.domain.repository.ActivityRepository;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;
import spring.backend.activity.dto.request.UserActivitySelectRequest;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.core.exception.DomainException;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.value.Role;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserActivitySelectServiceTest {
    @InjectMocks
    private UserActivitySelectService userActivitySelectService;

    @Mock
    private ActivityRepository activityRepository;

    private Member member;
    private Member guest;
    private UserActivitySelectRequest userActivitySelectRequest;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .role(Role.MEMBER)
                .build();

        Set<Keyword> keywords = Set.of(
                Keyword.create(Keyword.Category.CULTURE_ART, "example-image1.png"),
                Keyword.create(Keyword.Category.HEALTH, "example-image2.png")
        );


        userActivitySelectRequest = new UserActivitySelectRequest(
                Type.OFFLINE,
                150,
                keywords,
                "title",
                "content",
                "location"
        );
    }

    @DisplayName("요청이 null인 경우 예외가 발생한다")
    @Test
    public void throwsExceptionWhenUserActivitySelectRequestIsNull() {
        // when
        DomainException ex = assertThrows(DomainException.class, () -> userActivitySelectService.userActivitySelection(member, null));

        // then
        assertEquals(ActivityErrorCode.NOT_EXIST_ACTIVITY_CONDITION.getMessage(), ex.getMessage());
    }

    @DisplayName("유효한 활동 선택인 경우 저장된 ID를 반환한다")
    @Test
    public void returnsSavedActivityIdWhenValidActivitySelection() {
        // when
        Activity activity = Activity.create(member.getId(), userActivitySelectRequest.spareTime(), userActivitySelectRequest.type(), userActivitySelectRequest.keywords(), userActivitySelectRequest.title(), userActivitySelectRequest.content(), userActivitySelectRequest.location());
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        // then
        Long savedActivityId = userActivitySelectService.userActivitySelection(member, userActivitySelectRequest);

        // then
        assertEquals(activity.getId(), savedActivityId);
        verify(activityRepository).save(any(Activity.class));
    }

}
