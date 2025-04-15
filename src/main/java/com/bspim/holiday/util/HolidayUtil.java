package com.bspim.holiday.util;

import com.bspim.holiday.exception.HolidayNotFoundException;
import com.bspim.holiday.model.Holiday;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class HolidayUtil {

    public static String getHolidayNameByDate(final Set<Holiday> list1, final String commonDate) {
        return list1.stream()
                .filter(
                        holiday -> holiday.date().equals(commonDate))
                .findFirst()
                .orElseThrow(() -> new HolidayNotFoundException("Cannot find holiday for given date: " + commonDate))
                .name();
    }

    public static String findFirstDateAfter(final LocalDate localDate, final Set<Holiday> inputSet) {
        Set<Holiday> sortedSet = new TreeSet<>(Comparator.comparing(Holiday::date));
        sortedSet.addAll(inputSet);
        return sortedSet
                .stream()
                .filter(
                        holiday -> LocalDate.parse(holiday.date()).isAfter(localDate))
                .findFirst()
                .orElseThrow(() -> new HolidayNotFoundException("Cannot find common holiday after given date: " + localDate))
                .date();
    }
}
