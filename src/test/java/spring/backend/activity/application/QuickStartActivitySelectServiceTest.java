package spring.backend.activity.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.domain.entity.QuickStart;
import spring.backend.activity.domain.repository.ActivityRepository;
import spring.backend.activity.domain.repository.QuickStartRepository;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;
import spring.backend.activity.dto.request.QuickStartActivitySelectRequest;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.activity.exception.QuickStartErrorCode;
import spring.backend.core.exception.DomainException;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.value.Role;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuickStartActivitySelectServiceTest {
    @InjectMocks
    private QuickStartActivitySelectService quickStartActivitySelectService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private QuickStartRepository quickStartRepository;

    private Member member;
    private QuickStartActivitySelectRequest quickStartActivitySelectRequest;
    private final Long quickStartId = 1L;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .role(Role.MEMBER)
                .build();

        Keyword keyword = Keyword.create(Keyword.Category.CULTURE_ART, "example-image1.png");

        quickStartActivitySelectRequest = new QuickStartActivitySelectRequest(
                Type.OFFLINE,
                150,
                keyword,
                "title",
                "content",
                "location"
        );
    }

    @DisplayName("QuickStart가 존재하지 않는 경우 예외가 발생한다")
    @Test
    public void throwsExceptionWhenUserActivitySelectRequestIsNull() {
        // given & when
        when(quickStartRepository.findById(anyLong())).thenReturn(null);
        DomainException ex = assertThrows(DomainException.class, () ->
                quickStartActivitySelectService.quickStartUserActivitySelect(member, quickStartId, quickStartActivitySelectRequest)
        );
        // then
        assertEquals(QuickStartErrorCode.NOT_EXIST_QUICK_START.getMessage(), ex.getMessage());
    }

    @DisplayName("quickStartActivitySelectRequest가 null인 경우 예외를 반환한다.")
    @Test
    public void throwsExceptionWhenQuickStartActivitySelectRequestIsNull() {
        QuickStart quickStart = QuickStart.create(
                UUID.randomUUID(),
                "name",
                LocalTime.now(),
                150,
                Type.ONLINE
        );
        when(quickStartRepository.findById(quickStartId)).thenReturn(quickStart);

        // when
        DomainException ex = assertThrows(DomainException.class, () ->
                quickStartActivitySelectService.quickStartUserActivitySelect(member, quickStartId, null)
        );

        // then
        assertEquals(ActivityErrorCode.NOT_EXIST_ACTIVITY_CONDITION.getMessage(), ex.getMessage());
    }

    @DisplayName("빠른시작 활동 선택에 문제가 없는 경우, 저장된 활동의 ID를 반환한다.")
    @Test
    public void returnSavedActivityIdWhenNothingWrong() {
        // when
        QuickStart quickStart = QuickStart.create(
                UUID.randomUUID(),
                "name",
                LocalTime.now(),
                150,
                Type.ONLINE
        );
        when(quickStartRepository.findById(quickStartId)).thenReturn(quickStart);
        Activity activity = Activity.create(member.getId(), quickStartId, quickStartActivitySelectRequest.spareTime(), quickStartActivitySelectRequest.type(), quickStartActivitySelectRequest.keyword(), quickStartActivitySelectRequest.title(), quickStartActivitySelectRequest.content(), quickStartActivitySelectRequest.location());
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        // then
        Long savedActivityId = quickStartActivitySelectService.quickStartUserActivitySelect(member, quickStartId, quickStartActivitySelectRequest);

        // then
        assertEquals(activity.getId(), savedActivityId);
        assertEquals(activity.getQuickStartId(), quickStartId);
        verify(activityRepository).save(any(Activity.class));
    }

//    @DisplayName("유효한 활동 선택인 경우 저장된 ID를 반환한다")
//    @Test
//    public void returnsSavedActivityIdWhenValidActivitySelection() {
//        // when
//        Activity activity = Activity.create(member.getId(), null, userActivitySelectRequest.spareTime(), userActivitySelectRequest.type(), userActivitySelectRequest.keyword(), userActivitySelectRequest.title(), userActivitySelectRequest.content(), userActivitySelectRequest.location());
//        when(activityRepository.save(any(Activity.class))).thenReturn(activity);
//
//        // then
//        Long savedActivityId = userActivitySelectService.userActivitySelect(member, userActivitySelectRequest);
//
//        // then
//        assertEquals(activity.getId(), savedActivityId);
//        verify(activityRepository).save(any(Activity.class));
//    }

}
