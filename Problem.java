import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Problem {
    private final int workHourStart = 9;
    private final int workHourEnd = 17;

    private int turnaroundTime;
    private LocalDateTime timeOfReport;

    private LocalDateTime dueDate;

    public Problem(int turnaroundTime, LocalDateTime submitDate) {
        if (turnaroundTime < 1)
            throw new IllegalArgumentException("Turnaround time must be greater than zero!");
        else if (!isReportSubmittedInWorkingHours(submitDate))
            throw new IllegalArgumentException("The report time is out of office hours!");

        this.turnaroundTime = turnaroundTime;
        this.timeOfReport = submitDate;

        calculateDueDate();
    }

    LocalDateTime getDueDate() {
        return dueDate;
    }

    private boolean isReportSubmittedInWorkingHours(LocalDateTime timeOfReport) {
        return isOfficeHour(timeOfReport) && !isWeekend(timeOfReport);
    }

    private boolean isOfficeHour(LocalDateTime localDateTime) {
        int hourOfReport = localDateTime.getHour();
        return hourOfReport >= workHourStart
                && hourOfReport <= workHourEnd;
    }

    private boolean isWeekend(LocalDateTime localDateTime) {
        DayOfWeek dayOfReport = localDateTime.getDayOfWeek();
        return dayOfReport.equals(DayOfWeek.SATURDAY)
                || dayOfReport.equals(DayOfWeek.SUNDAY);
    }


    private void calculateDueDate() {
        dueDate = timeOfReport;

        int workDayLength = workHourEnd - workHourStart;
        int dueDateDays = turnaroundTime / workDayLength;
        int remainingHours = turnaroundTime % workDayLength;

        calculateDays(dueDateDays);
        calculateHours(remainingHours);
    }

    private void calculateHours(int remainingHours) {
        if (remainingHours > 0) {
            dueDate = dueDate.plusHours(remainingHours);

            if (!isOfficeHour(dueDate)) {
                int hoursToStartWork = 24 - workHourEnd + workHourStart;
                dueDate = dueDate.plusHours(hoursToStartWork);
                skipWeekend();
            }
        }
    }

    private void calculateDays(int dayCount) {
        for (int i = 0; i < dayCount; i++) {
            dueDate = dueDate.plusDays(1);
            skipWeekend();
        }
    }

    private void skipWeekend() {
        if (isWeekend(dueDate))
            dueDate = dueDate.plusDays(2);
    }
}
