package com.example.mynotes.data;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class DateConverter {
    static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return dateFormat.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(Date value) {

        return value == null ? null : dateFormat.format(value);
    }
}
