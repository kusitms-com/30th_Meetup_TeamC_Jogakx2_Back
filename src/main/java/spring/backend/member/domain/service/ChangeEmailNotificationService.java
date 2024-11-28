package spring.backend.member.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ChangeEmailNotificationService {

    private final MemberRepository memberRepository;

    public void changeEmailNotification(Member member) {
        boolean isEmailNotificationEnabled = member.isEmailNotification();
        member.changeEmailNotification(!isEmailNotificationEnabled);
        memberRepository.save(member);
    }
}
