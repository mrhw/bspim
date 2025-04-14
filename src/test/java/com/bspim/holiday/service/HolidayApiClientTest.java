package com.bspim.holiday.service;

import com.bspim.holiday.config.ClientProperties;
import com.bspim.holiday.exception.ExternalApiException;
import com.bspim.holiday.model.Country;
import com.bspim.holiday.model.ExternalCountriesResponse;
import com.bspim.holiday.model.ExternalHolidayResponse;
import com.bspim.holiday.model.Holiday;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HolidayApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ClientProperties clientProperties;

    private HolidayApiClient holidayApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        holidayApiClient = new HolidayApiClient(restTemplate, clientProperties);
    }

    @Test
    void getHolidays_shouldReturnListOfHolidays() {
        Country country = new Country("US", new String[]{"en"});
        LocalDate date = LocalDate.of(2023, 12, 25);
        List<Holiday> mockHolidays = List.of(new Holiday("2023-12-25", "Christmas"));
        ExternalHolidayResponse mockResponse = new ExternalHolidayResponse(mockHolidays);

        when(clientProperties.getUrl()).thenReturn("http://example.com/api/");
        when(clientProperties.getKey()).thenReturn("test-key");
        when(restTemplate.getForObject("http://example.com/api/holidays?country=US&year=2023&key=test-key&language=en", ExternalHolidayResponse.class)).thenReturn(mockResponse);

        List<Holiday> holidays = holidayApiClient.getHolidays(country, date);

        assertNotNull(holidays);
        assertEquals(holidays.size(), mockHolidays.size());
        assertEquals("Christmas", mockHolidays.get(0).name());
    }

    @Test
    void getHolidays_shouldThrowException_whenApiCallFailsy() {
        Country country = new Country("US", new String[]{"en"});
        LocalDate date = LocalDate.of(2023, 12, 25);

        when(clientProperties.getUrl()).thenReturn("http://example.com/api/");
        when(clientProperties.getKey()).thenReturn("test-key");
        when(restTemplate.getForObject("http://example.com/api/holidays?country=US&year=2023&key=test-key&language=en", ExternalHolidayResponse.class)).thenThrow(new RuntimeException("API Call Failed"));

        ExternalApiException exception = assertThrows(ExternalApiException.class, () -> holidayApiClient.getHolidays(country, date));

        assertEquals("API Call Failed", exception.getMessage());
    }

    @Test
    void getCountries_shouldReturnListOfCountries() {
        List<Country> mockCountries = List.of(new Country("US", new String[]{"en"}), new Country("CA", new String[]{"en"}));
        ExternalCountriesResponse mockResponse = new ExternalCountriesResponse(mockCountries);

        when(clientProperties.getUrl()).thenReturn("http://example.com/api/");
        when(clientProperties.getKey()).thenReturn("test-key");
        when(restTemplate.getForObject("http://example.com/api/countries?key=test-key", ExternalCountriesResponse.class)).thenReturn(mockResponse);
        List<Country> countries = holidayApiClient.getCountries();

        assertNotNull(countries);
        assertEquals(countries.size(), mockCountries.size());
        assertEquals("US", countries.get(0).code());
    }

    @Test
    void getCountries_shouldThrowException_whenApiCallFails() {
        when(clientProperties.getUrl()).thenReturn("http://example.com/api/");
        when(clientProperties.getKey()).thenReturn("test-key");
        when(restTemplate.getForObject("http://example.com/api/countries?key=test-key", ExternalCountriesResponse.class)).thenThrow(new RuntimeException("API Call Failed"));

        ExternalApiException exception = assertThrows(ExternalApiException.class, () -> holidayApiClient.getCountries());
        assertEquals("API Call Failed", exception.getMessage());
    }
}