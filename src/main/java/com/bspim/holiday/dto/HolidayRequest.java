package com.bspim.holiday.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class HolidayRequest {

    @NotNull
    @ValidCountryCode
    private final String countryCode1;

    @NotNull
    @ValidCountryCode
    private final String countryCode2;

    @NotNull
    private final LocalDate date;

    public HolidayRequest(String countryCode1, String countryCode2, LocalDate date) {
        this.countryCode1 = countryCode1;
        this.countryCode2 = countryCode2;
        this.date = date;
    }

    public String getCountryCode1() {
        return countryCode1;
    }


    public String getCountryCode2() {
        return countryCode2;
    }

    public LocalDate getDate() {
        return date;
    }
}
