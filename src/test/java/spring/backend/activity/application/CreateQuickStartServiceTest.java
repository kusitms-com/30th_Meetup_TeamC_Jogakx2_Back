package spring.backend.activity.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.backend.activity.domain.entity.QuickStart;
import spring.backend.activity.domain.repository.QuickStartRepository;
import spring.backend.activity.domain.value.Type;
import spring.backend.activity.dto.request.QuickStartRequest;
import spring.backend.activity.exception.QuickStartErrorCode;
import spring.backend.core.exception.DomainException;
import spring.backend.core.util.TimeUtil;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.value.Role;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateQuickStartServiceTest {

    @InjectMocks
    private CreateQuickStartService createQuickStartService;

    @Mock
    private QuickStartRepository quickStartRepository;

    private Member member;
    private QuickStartRequest request;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .role(Role.MEMBER)
                .build();
        request = new QuickStartRequest(
                "등교",
                12,
                30,
                "오전",
                300,
                Type.ONLINE
        );
    }

    @DisplayName("요청이 null인 경우 예외가 발생한다")
    @Test
    public void createQuickStart_NullRequest_ThrowsException() {
        // when
        DomainException ex = assertThrows(DomainException.class, () -> createQuickStartService.createQuickStart(member, null));

        // then
        assertEquals(QuickStartErrorCode.NOT_EXIST_QUICK_START_CONDITION.getMessage(), ex.getMessage());
    }

    @DisplayName("유효한 빠른 시작 요청인 경우 저장된 ID를 반환한다")
    @Test
    public void createQuickStart_ValidRequest_ReturnsSavedQuickStartId() {
        LocalTime startTime = TimeUtil.toLocalTime(request.meridiem(), request.hour(), request.minute());
        QuickStart quickStart = QuickStart.create(member.getId(), request.name(), startTime, request.spareTime(), request.type());
        when(quickStartRepository.save(any(QuickStart.class))).thenReturn(quickStart);

        // when
        Long savedQuickStartId = createQuickStartService.createQuickStart(member, request);

        // then
        assertEquals(quickStart.getId(), savedQuickStartId);
        verify(quickStartRepository).save(any(QuickStart.class));
    }
}