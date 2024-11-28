package spring.backend.quickstart.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.backend.activity.domain.value.Type;
import spring.backend.quickstart.domain.entity.QuickStart;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuickStartRepositoryTest {

    @Autowired
    private QuickStartRepository quickStartRepository;

    private QuickStart quickStart;

    @BeforeEach
    void setUp() {
        quickStart = QuickStart.builder()
                .memberId(UUID.randomUUID())
                .name("Test QuickStart")
                .startTime(LocalTime.of(12, 30))
                .spareTime(60)
                .type(Type.ONLINE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    @Test
    void testSaveAndFindQuickStart() {
        QuickStart savedQuickStart = quickStartRepository.save(quickStart);
        QuickStart foundQuickStart = quickStartRepository.findById(savedQuickStart.getId());

        assertThat(foundQuickStart).isNotNull();
        assertThat(foundQuickStart.getStartTime()).isEqualTo(quickStart.getStartTime());
    }
}
