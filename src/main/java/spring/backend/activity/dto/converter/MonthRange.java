package spring.backend.activity.dto.converter;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Getter
public class MonthRange {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public MonthRange(YearMonth yearMonth) {
        this.start = yearMonth.atDay(1).atStartOfDay();
        this.end = yearMonth.atEndOfMonth().atTime(23, 59, 59);
    }
}
