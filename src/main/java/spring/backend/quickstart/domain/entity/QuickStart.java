package spring.backend.quickstart.domain.entity;

import lombok.Builder;
import lombok.Getter;
import spring.backend.activity.domain.value.Type;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
public class QuickStart {

    private Long id;

    private UUID memberId;

    private String name;

    private LocalTime startTime;

    private Integer spareTime;

    private Type type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted;

    public static QuickStart create(UUID memberId, String name, LocalTime startTime, Integer spareTime, Type type) {
        return QuickStart.builder()
                .memberId(memberId)
                .name(name)
                .startTime(startTime)
                .spareTime(spareTime)
                .type(type)
                .build();
    }

    public void update(String name, LocalTime startTime, Integer spareTime, Type type) {
        this.name = name;
        this.startTime = startTime;
        this.spareTime = spareTime;
        this.type = type;
    }
}
