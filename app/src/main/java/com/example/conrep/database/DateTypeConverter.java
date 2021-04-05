package com.example.conrep.database;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.logging.Logger;

//DateTypeConverter needed for DAO
public class DateTypeConverter {
    private static Logger log = Logger.getLogger("DateTypeConverter");
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        if(value != null) {
            log.info("Incoming long: " + value + "\nTo Date: " + new Date(value));
        }
        return value == null ? null : new Date(value);

    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        if(date != null) {
            log.info("Incoming date: " + date + "\n to Long: " + date.getTime());
        }
        return date == null ? null : date.getTime();
    }
}
