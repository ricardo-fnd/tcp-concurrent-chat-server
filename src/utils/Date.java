package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {

    public static String getDateAndTime() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");

        return "[" + time.format(formatter) + "]";
    }
}
