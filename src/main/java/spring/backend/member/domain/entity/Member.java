package spring.backend.member.domain.entity;

import lombok.Builder;
import lombok.Getter;
import spring.backend.member.domain.value.Gender;
import spring.backend.member.domain.value.Provider;
import spring.backend.member.domain.value.Role;
import spring.backend.member.exception.MemberErrorCode;
import spring.backend.member.infrastructure.persistence.jpa.entity.MemberJpaEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Member {

    private UUID id;

    private Provider provider;

    private Role role;

    private String email;

    private String nickname;

    private int birthYear;

    private Gender gender;

    private String profileImage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted;

    public static Member toDomainEntity(MemberJpaEntity memberJpaEntity) {
        return Member.builder()
                .id(memberJpaEntity.getId())
                .provider(memberJpaEntity.getProvider())
                .role(memberJpaEntity.getRole())
                .email(memberJpaEntity.getEmail())
                .nickname(memberJpaEntity.getNickname())
                .birthYear(memberJpaEntity.getBirthYear())
                .gender(memberJpaEntity.getGender())
                .profileImage(memberJpaEntity.getProfileImage())
                .createdAt(memberJpaEntity.getCreatedAt())
                .updatedAt(memberJpaEntity.getUpdatedAt())
                .deleted(memberJpaEntity.getDeleted())
                .build();
    }

    public boolean isSameProvider(Provider otherProvider) {
        return this.provider.equals(otherProvider);
    }

    public boolean isMember() {
        return Role.MEMBER.equals(this.role);
    }

    public void convertGuestToMember(String nickname, int birthYear, Gender gender, String profileImage) {
        if (isMember()) {
            throw MemberErrorCode.ALREADY_REGISTERED_MEMBER.toException();
        }
        this.role = Role.MEMBER;
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.gender = gender;
        this.profileImage = profileImage;
    }

    public static Member createGuestMember(Provider provider, String email, String nickname) {
        return Member.builder()
                .provider(provider)
                .role(Role.GUEST)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
