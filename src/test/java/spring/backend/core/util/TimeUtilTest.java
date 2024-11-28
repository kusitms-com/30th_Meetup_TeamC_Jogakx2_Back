package spring.backend.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeUtilTest {

    @DisplayName("오전 10시 30분을 입력하면 10시 30분으로 변환된다.")
    @Test
    void testToLocalTime_AM() {
        LocalTime time = TimeUtil.toLocalTime("오전", 10, 30);
        assertEquals(10, time.getHour());
        assertEquals(30, time.getMinute());
    }

    @DisplayName("오후 10시 30분을 입력하면 22시 30분으로 변환된다.")
    @Test
    void testToLocalTime_PM() {
        LocalTime time = TimeUtil.toLocalTime("오후", 10, 30);
        assertEquals(22, time.getHour());
        assertEquals(30, time.getMinute());
    }

    @DisplayName("오전 0시 입력 시 '오전' 반환")
    @Test
    void testToMeridiem_Midnight() {
        LocalTime time = LocalTime.of(0, 0);
        String meridiem = TimeUtil.toMeridiem(time);
        assertEquals("오전", meridiem);
    }

    @DisplayName("오후 12시 입력 시 '오후' 반환")
    @Test
    void testToMeridiem_Noon() {
        LocalTime time = LocalTime.of(12, 0);
        String meridiem = TimeUtil.toMeridiem(time);
        assertEquals("오후", meridiem);
    }

    @DisplayName("오전 0시, 오후 12시는 12로 변환된다.")
    @Test
    void testToHour() {
        LocalTime time = LocalTime.of(0, 0);
        assertEquals(12, TimeUtil.toHour(time));

        time = LocalTime.of(12, 0);
        assertEquals(12, TimeUtil.toHour(time));
    }
}