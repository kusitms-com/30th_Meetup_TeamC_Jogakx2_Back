package spring.backend.activity.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    private Activity activity;

    @BeforeEach
    void setUp() {
        activity = Activity.builder()
                .memberId(UUID.randomUUID())
                .quickStartId(100L)
                .spareTime(120)
                .type(Type.ONLINE)
                .keywords(Set.of(Keyword.create(Keyword.Category.SELF_DEVELOPMENT, "test.url"), Keyword.create(Keyword.Category.ENTERTAINMENT, "test1.url")))
                .title("Test Activity")
                .content("This is a test activity.")
                .location("Test Location")
                .finished(false)
                .finishedAt(null)
                .savedTime(30)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    @Test
    void testSaveAndFindActivity() {
        Activity savedActivity = activityRepository.save(activity);
        Activity foundActivity = activityRepository.findById(savedActivity.getId());

        assertThat(foundActivity).isNotNull();
        assertThat(foundActivity.getKeywords()).isEqualTo(activity.getKeywords());
    }
}