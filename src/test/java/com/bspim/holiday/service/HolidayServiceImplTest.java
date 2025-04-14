package com.bspim.holiday.service;

import com.bspim.holiday.dto.HolidayRequest;
import com.bspim.holiday.dto.HolidayResponse;
import com.bspim.holiday.exception.HolidayNotFoundException;
import com.bspim.holiday.model.Country;
import com.bspim.holiday.model.Holiday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HolidayServiceImplTest {

    @Mock
    private HolidayApiClient holidayApiClient;
    @Mock
    private CountryCodeService countryCodeService;
    private HolidayServiceImpl holidayServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        holidayServiceImpl = new HolidayServiceImpl(holidayApiClient, countryCodeService);
    }

    @Test
    void findFirstCommonHoliday_shouldReturnCommonHoliday() {
        Holiday holidayUS = new Holiday("2024-12-25", "Christmas");
        Holiday holidayCA = new Holiday("2024-12-25", "Christmas");

        HolidayRequest request = new HolidayRequest("US", "CA", LocalDate.of(2024, 12, 24));
        Country countryUS = new Country("US", new String[]{"en"});
        Country countryCA = new Country("CA", new String[]{"en"});
        when(countryCodeService.getCountry("US")).thenReturn(countryUS);
        when(countryCodeService.getCountry("CA")).thenReturn(countryCA);

        when(holidayApiClient.getHolidays(countryUS, LocalDate.of(2024, 12, 24))).thenReturn(Arrays.asList(holidayUS));
        when(holidayApiClient.getHolidays(countryCA, LocalDate.of(2024, 12, 24))).thenReturn(Arrays.asList(holidayCA));

        HolidayResponse holidayResponse = holidayServiceImpl.findFirstCommonHoliday(request);

        assertNotNull(holidayResponse);
        assertEquals("2024-12-25", holidayResponse.date());
        assertEquals("Christmas", holidayResponse.name1());
        assertEquals("Christmas", holidayResponse.name2());
    }

    @Test
    void findFirstCommonHoliday_NoCommonHoliday() {
        HolidayRequest request = new HolidayRequest("US", "CA", LocalDate.of(2023, 12, 24));
        Holiday holidayUS = new Holiday("2023-07-04", "Independence Day");
        Holiday holidayCA = new Holiday("2023-07-01", "Canada Day");
        Country countryUS = new Country("US", new String[]{"en"});
        Country countryCA = new Country("CA", new String[]{"en"});
        when(countryCodeService.getCountry("US")).thenReturn(countryUS);
        when(countryCodeService.getCountry("CA")).thenReturn(countryCA);

        when(holidayApiClient.getHolidays(countryUS, LocalDate.of(2024, 12, 24))).thenReturn(Arrays.asList(holidayUS));
        when(holidayApiClient.getHolidays(countryCA, LocalDate.of(2024, 12, 24))).thenReturn(Arrays.asList(holidayCA));

        Assertions.assertThrows(HolidayNotFoundException.class, () -> holidayServiceImpl.findFirstCommonHoliday(request));
    }
}