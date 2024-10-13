package spring.backend.member.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import spring.backend.core.infrastructure.jpa.shared.BaseEntity;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.value.Provider;
import spring.backend.member.domain.value.Role;

import java.util.Optional;

@Entity
@Table(name = "member")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String nickname;

    public static MemberJpaEntity toJpaEntity(Member member) {
        return MemberJpaEntity.builder()
                .id(member.getId())
                .provider(member.getProvider())
                .role(member.getRole())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .deleted(Optional.ofNullable(member.getDeleted()).orElse(false))
                .build();
    }
}
