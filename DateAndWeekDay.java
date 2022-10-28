import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateAndWeekDay {

//     -- Always setting Monday as the first day to the first panel label --
    public static LocalDate calculateDate(int createDayPanel) {
        return (LocalDate.now().minusDays
                (LocalDate.now().getDayOfWeek().getValue()
                        - (createDayPanel)));
    }

//     -- Setting correct dates for all days of the current week --
    public static DayOfWeek calculateDayOfWeek(int createDayPanel) {
        return LocalDate.now().getDayOfWeek().minus
                (LocalDate.now().getDayOfWeek().getValue()).plus(createDayPanel);

    }
}
