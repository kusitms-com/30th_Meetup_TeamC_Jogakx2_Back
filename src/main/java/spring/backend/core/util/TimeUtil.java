package spring.backend.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtil {

    private static final String ANTE_MERIDIEM = "오전";

    private static final String POST_MERIDIEM = "오후";

    public static LocalTime toLocalTime(String meridiem, Integer hour, Integer minute) {
        if (meridiem == null || hour == null || minute == null) {
            return null;
        }
        try {
            return switch (meridiem) {
                case ANTE_MERIDIEM -> LocalTime.of(hour % 12, minute);
                case POST_MERIDIEM -> LocalTime.of((hour % 12) + 12, minute);
                default -> null;
            };
        } catch (DateTimeException e) {
            return null;
        }
    }

    public static String toMeridiem(LocalTime time) {
        if (time == null) {
            return null;
        }
        return time.getHour() < 12 ? ANTE_MERIDIEM : POST_MERIDIEM;
    }

    public static Integer toHour(LocalTime time) {
        if (time == null) {
            return null;
        }
        int hour = time.getHour() % 12;
        return hour == 0 ? 12 : hour;
    }

    public static Integer toMinute(LocalTime time) {
        if (time == null) {
            return null;
        }
        return time.getMinute();
    }
}
