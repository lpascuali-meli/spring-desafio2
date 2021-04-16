package com.sprint.challenge2.utils;

import com.sprint.challenge2.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static int calculateDaysBetweenDates(String dateFrom, String dateTo) throws ApiException {
        Date d1;
        Date d2;
        String format = "dd/MM/yyyy";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            d1 = sdf.parse(dateFrom);
            d2 = sdf.parse(dateTo);
        } catch (ParseException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The date " + dateFrom + " must have the format " + format);
        }
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
    }
}
