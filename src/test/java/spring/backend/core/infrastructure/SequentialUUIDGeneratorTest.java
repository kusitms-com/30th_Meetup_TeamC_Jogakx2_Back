package spring.backend.core.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.domain.value.Provider;
import spring.backend.member.domain.value.Role;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class SequentialUUIDGeneratorTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("UUID가 순차적으로 생성되는지 확인한다.")
    @Test
    void testSequentialUUIDGenerator() {
        // given
        Member member1 = Member.builder()
                .provider(Provider.GOOGLE)
                .role(Role.GUEST)
                .email("test1@test.com")
                .build();

        Member member2 = Member.builder()
                .provider(Provider.GOOGLE)
                .role(Role.GUEST)
                .email("test2@test.com")
                .build();

        // when
        Member saverMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);

        // then
        assertNotNull(saverMember1.getId());
        assertNotNull(savedMember2.getId());

        long timestamp1 = saverMember1.getId().getMostSignificantBits() >>> 16;
        long timestamp2 = savedMember2.getId().getMostSignificantBits() >>> 16;
        assertTrue(timestamp2 >= timestamp1);
    }

    @DisplayName("생성순서를 변경할 경우, UUID가 순차적으로 생성되는지 확인한다.")
    @Test
    void failedTestSequentialUUIDGenerator() {
        // given
        Member member1 = Member.builder()
                .provider(Provider.GOOGLE)
                .role(Role.GUEST)
                .email("test1@test.com")
                .build();

        Member member2 = Member.builder()
                .provider(Provider.GOOGLE)
                .role(Role.GUEST)
                .email("test2@test.com")
                .build();

        // when
        Member savedMember2 = memberRepository.save(member2);
        Member saverMember1 = memberRepository.save(member1);

        // then
        assertNotNull(saverMember1.getId());
        assertNotNull(savedMember2.getId());

        long timestamp1 = saverMember1.getId().getMostSignificantBits() >>> 16;
        long timestamp2 = savedMember2.getId().getMostSignificantBits() >>> 16;

        assertTrue(timestamp2 <= timestamp1);
    }
}
