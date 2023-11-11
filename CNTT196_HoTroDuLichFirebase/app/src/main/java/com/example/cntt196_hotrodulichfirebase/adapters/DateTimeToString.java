package com.example.cntt196_hotrodulichfirebase.adapters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeToString {
    public static String Format(LocalDateTime localDateTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        return  formattedDateTime;
    }
}
