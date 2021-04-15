package com.diazs.notify.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {
    public static String FORMAT_ONE = "dd/mm/yyyy";
    public static String FORMAT_TWO = "dd-mm-yyyy";
    public static String FORMAT_THREE = "dd MMM yyyy HH:mm:ss";
    public static String FORMAT_FOUR = "dd MMMMM yyyy";
    public static String FORMAT_FIVE = "EEE MMMMM yyyy";

    public static String getDateFormated(long millis, String pattern){
        DateFormat simple = new SimpleDateFormat(pattern);
        Date result = new Date(millis);

        return simple.format(result);
    }
}
