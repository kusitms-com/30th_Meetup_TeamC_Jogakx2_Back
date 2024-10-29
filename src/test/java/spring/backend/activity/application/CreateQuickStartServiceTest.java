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
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.value.Role;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateQuickStartServiceTest {

    @InjectMocks
    private CreateQuickStartService createQuickStartService;

    @Mock
    private QuickStartRepository quickStartRepository;

    private Member member;
    private Member guest;
    private QuickStartRequest request;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .role(Role.MEMBER)
                .build();
        guest = Member.builder()
                .role(Role.GUEST)
                .build();
        request = new QuickStartRequest(
                "등교",
                Time.valueOf("12:30:00"),
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

    @DisplayName("비회원이 요청하는 경우 예외가 발생한다")
    @Test
    public void createQuickStart_NonMember_ThrowsException() {
        // when
        DomainException ex = assertThrows(DomainException.class, () -> createQuickStartService.createQuickStart(guest, request));

        // then
        assertEquals(QuickStartErrorCode.NOT_A_MEMBER.getMessage(), ex.getMessage());
    }

    @DisplayName("유효한 빠른 시작 요청인 경우 저장된 ID를 반환한다")
    @Test
    public void createQuickStart_ValidRequest_ReturnsSavedQuickStartId() {
        QuickStart quickStart = QuickStart.create(member.getId(), request.name(), request.startTime(), request.spareTime(), request.type());
        when(quickStartRepository.save(any(QuickStart.class))).thenReturn(quickStart);

        // when
        Long savedQuickStartId = createQuickStartService.createQuickStart(member, request);

        // then
        assertEquals(quickStart.getId(), savedQuickStartId);
        verify(quickStartRepository).save(any(QuickStart.class));
    }
}