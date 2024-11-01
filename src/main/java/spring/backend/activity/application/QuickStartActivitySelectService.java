package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.domain.repository.ActivityRepository;
import spring.backend.activity.dto.request.QuickStartActivitySelectRequest;
import spring.backend.member.domain.entity.Member;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class QuickStartActivitySelectService {
    private final ActivityRepository activityRepository;

    public Long quickStartUserActivitySelect(Member member , Long quickStartId, QuickStartActivitySelectRequest quickStartActivitySelectRequest
    ) {}

}
