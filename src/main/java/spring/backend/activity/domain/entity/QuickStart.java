package spring.backend.activity.domain.entity;

import lombok.Builder;
import lombok.Getter;
import spring.backend.activity.domain.value.Type;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class QuickStart {

    private Long id;

    private UUID memberId;

    private String name;

    private Time startTime;

    private Integer spareTime;

    private Type type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted;
}
