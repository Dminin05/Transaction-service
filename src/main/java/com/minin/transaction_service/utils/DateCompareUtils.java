package com.minin.transaction_service.utils;

import com.minin.transaction_service.model.Limit;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateCompareUtils {

    public boolean compareMonths(Limit newLimit, Limit previousLimit) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(previousLimit.getCreationDate());
        int previousLimitMonth = calendar.get(Calendar.MONTH);
        int previousLimitYear = calendar.get(Calendar.YEAR);

        calendar.setTime(newLimit.getCreationDate());
        int newLimitMonth = calendar.get(Calendar.MONTH);
        int newLimitYear = calendar.get(Calendar.YEAR);

        return previousLimitMonth == newLimitMonth && previousLimitYear == newLimitYear;
    }

    public boolean compareMonths(Limit limit, Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(limit.getUpdatedDate());
        int limitMonth = calendar.get(Calendar.MONTH);
        int limitYear = calendar.get(Calendar.YEAR);

        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        return limitMonth == currentMonth && limitYear == currentYear;
    }

}
