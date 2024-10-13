package spring.backend.member.infrastructure.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.exception.MemberErrorCode;
import spring.backend.member.infrastructure.mapper.MemberMapper;
import spring.backend.member.infrastructure.persistence.jpa.entity.MemberJpaEntity;
import spring.backend.member.infrastructure.persistence.jpa.repository.MemberJpaRepository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Log4j2
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberMapper memberMapper;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member findById(UUID id) {
        MemberJpaEntity memberJpaEntity = memberJpaRepository.findById(id).orElse(null);
        if (memberJpaEntity == null) {
            return null;
        }
        return memberMapper.toDomainEntity(memberJpaEntity);
    }

    @Override
    public void save(Member member) {
        MemberJpaEntity memberJpaEntity = memberMapper.toJpaEntity(member);
        if (memberJpaEntity == null) {
            throw MemberErrorCode.MEMBER_SAVE_FAILED.toException();
        }
        memberJpaRepository.save(memberJpaEntity);
    }

    @Override
    public Member findByEmail(String email) {
        MemberJpaEntity memberJpaEntity = memberJpaRepository.findByEmail(email);
        if (memberJpaEntity == null) {
            return null;
        }
        return memberMapper.toDomainEntity(memberJpaEntity);
    }
}
