package spring.backend.activity.domain.entity;

import lombok.Builder;
import lombok.Getter;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class Activity {

    private Long id;

    private UUID memberId;

    private Long quickStartId;

    private Integer spareTime;

    private Type type;

    private Set<Keyword> keywords;

    private String title;

    private String content;

    private String location;

    private Boolean finished;

    private LocalDateTime finishedAt;

    private Integer savedTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted;
}
