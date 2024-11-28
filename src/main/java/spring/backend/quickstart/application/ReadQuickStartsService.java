package spring.backend.quickstart.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.quickstart.dto.response.QuickStartResponse;
import spring.backend.quickstart.presentation.dto.response.QuickStartsResponse;
import spring.backend.quickstart.query.dao.QuickStartDao;
import spring.backend.member.domain.entity.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class ReadQuickStartsService {

    private final QuickStartDao quickStartDao;

    public QuickStartsResponse readQuickStarts(Member member) {
        List<QuickStartResponse> quickStartResponses = quickStartDao.findByMemberId(member.getId(), Sort.by("createdAt").descending());
        return new QuickStartsResponse(quickStartResponses);
    }
}
