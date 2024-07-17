package com.example.demo.com.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GetDate {
    public static LocalDateTime pareLocalDataTime(String timeFormat) {
        return LocalDateTime.parse(
                new SimpleDateFormat(timeFormat).format(System.currentTimeMillis())
                , DateTimeFormatter.ofPattern(timeFormat, Locale.KOREA)
        );
    }
}
