package spring.backend.member.domain.repository;

import spring.backend.member.domain.entity.Member;

import java.util.UUID;

public interface MemberRepository {

    Member findById(UUID id);
    void save(Member member);
    Member findByEmail(String email);
}
