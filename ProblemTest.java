import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;

public class ProblemTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZeroTurnaroundTime() {
        new Problem(0, LocalDateTime.of(2019, Month.JUNE, 14, 10, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOutOfWorkingHours() {
        new Problem(1, LocalDateTime.of(2019, Month.JUNE, 14, 8, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWeekend() {
        new Problem(1, LocalDateTime.of(2019, Month.JUNE, 15, 10, 0));
    }

    @Test
    public void testDueDateWithWeekend() {
        Problem problem = new Problem(2, LocalDateTime.of(2019, Month.JUNE, 14, 16, 0));

        LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.JUNE, 17, 10, 0);
        assertEquals(expectedDateTime, problem.getDueDate());
    }

    @Test
    public void testDueDateWithZeroRemainingTime() {
        Problem problem = new Problem(8,
                LocalDateTime.of(2019, Month.JUNE, 13, 9, 0));
        LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.JUNE, 14, 9, 0);

        assertEquals(expectedDateTime, problem.getDueDate());
    }

    @Test
    public void testDueDateWithZeroRemainingTimeWithWeekend() {
        Problem problem = new Problem(16,
                LocalDateTime.of(2019, Month.JUNE, 14, 9, 0));
        LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.JUNE, 18, 9, 0);

        assertEquals(expectedDateTime, problem.getDueDate());
    }

    @Test
    public void testDueDateWithTwoWeeks() {
        Problem problem = new Problem(48,
                LocalDateTime.of(2019, Month.JUNE, 14, 9, 0));
        LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.JUNE, 24, 9, 0);

        assertEquals(expectedDateTime, problem.getDueDate());
    }
}
