package com.example.cntt196_hotrodulichfirebase.adapters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeToString {
    public static String Format(LocalDateTime localDateTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        return  formattedDateTime;
    }
    public static String GenarateID(String IdNguoiDung)
    {
        LocalDateTime localDateTime=LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = localDateTime.format(formatter);
        return  IdNguoiDung + formattedDateTime;
    }
    public static String FormatVND(double ChiPhi)
    {
        NumberFormat numberFormat = DecimalFormat.getCurrencyInstance( new Locale("vi", "VN"));
        return numberFormat.format(ChiPhi / 1.0);
    }
}
