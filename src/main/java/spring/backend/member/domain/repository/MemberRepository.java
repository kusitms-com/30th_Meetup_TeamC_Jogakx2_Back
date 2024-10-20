package spring.backend.member.domain.repository;

import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.value.Role;

import java.util.List;
import java.util.UUID;

public interface MemberRepository {

    Member findById(UUID id);
    Member save(Member member);
    Member findByEmail(String email);
    List<Member> findAllByEmail(String email);
    boolean existsByNicknameAndRole(String nickname, Role role);
}
