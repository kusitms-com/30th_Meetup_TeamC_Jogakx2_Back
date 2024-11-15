package spring.backend.activity.util;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Getter
public class MonthRangeUtil {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public MonthRangeUtil(YearMonth yearMonth) {
        this.start = yearMonth.atDay(1).atStartOfDay();
        this.end = yearMonth.atEndOfMonth().atTime(23, 59, 59);
    }
}
