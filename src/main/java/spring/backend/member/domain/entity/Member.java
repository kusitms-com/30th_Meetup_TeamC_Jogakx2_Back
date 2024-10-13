package spring.backend.member.domain.entity;

import lombok.Builder;
import lombok.Getter;
import spring.backend.member.domain.value.Provider;
import spring.backend.member.domain.value.Role;
import spring.backend.member.infrastructure.persistence.jpa.entity.MemberJpaEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Member {

    private final UUID id;

    private final Provider provider;

    private final Role role;

    private final String email;

    private final String nickname;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final Boolean deleted;

    public static Member toDomainEntity(MemberJpaEntity memberJpaEntity) {
        return Member.builder()
                .id(memberJpaEntity.getId())
                .provider(memberJpaEntity.getProvider())
                .role(memberJpaEntity.getRole())
                .email(memberJpaEntity.getEmail())
                .nickname(memberJpaEntity.getNickname())
                .createdAt(memberJpaEntity.getCreatedAt())
                .updatedAt(memberJpaEntity.getUpdatedAt())
                .deleted(memberJpaEntity.getDeleted())
                .build();
    }

    public boolean isSameProvider(Provider otherProvider) {
        return this.provider.equals(otherProvider);
    }
}
