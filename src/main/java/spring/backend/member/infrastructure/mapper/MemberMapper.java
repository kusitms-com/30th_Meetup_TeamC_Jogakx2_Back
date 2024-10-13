package spring.backend.member.infrastructure.mapper;

import org.springframework.stereotype.Component;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.infrastructure.persistence.jpa.entity.MemberJpaEntity;

@Component
public class MemberMapper {

    public Member toDomainEntity(MemberJpaEntity member) {
        return Member.toDomainEntity(member);
    }

    public MemberJpaEntity toJpaEntity(Member member) {
        return MemberJpaEntity.toJpaEntity(member);
    }
}
