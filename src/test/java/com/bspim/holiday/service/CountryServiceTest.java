package com.bspim.holiday.service;

import com.bspim.holiday.exception.CountryNotFoundException;
import com.bspim.holiday.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CountryServiceTest {
    @Mock
    private HolidayApiClient holidayApiClient;

    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryService = new CountryService(holidayApiClient);
    }

    @Test
    void fetchCountries_shouldReturnListOfCountries() {
        List<Country> mockCountryList = List.of(
                new Country("US", new String[]{"en"}),
                new Country("CA", new String[]{"en"}));
        when(holidayApiClient.getCountries()).thenReturn(mockCountryList);

        List<Country> result = countryService.fetchCountries();

        assertNotNull(result);
        assertEquals(mockCountryList.size(), result.size());
        assertEquals("US", result.get(0).code());
    }

    @Test
    void fetchCountries_shouldReturnCountry_whenCodeExists() {
        List<Country> mockCountryList = List.of(
                new Country("US", new String[]{"en"}),
                new Country("CA", new String[]{"en"}));

        when(holidayApiClient.getCountries()).thenReturn(mockCountryList);

        Country result = countryService.getCountry("US");
        assertNotNull(result);
        assertEquals("US", result.code());
    }

    @Test
    void getCountry_shouldThrowException_whenCodeNotExists() {
        List<Country> mockCountryList = List.of(
                new Country("US", new String[]{"en"}),
                new Country("CA", new String[]{"en"}));

        when(holidayApiClient.getCountries()).thenReturn(mockCountryList);

        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class, () -> countryService.getCountry("XX"));
        assertEquals("Country with code XX not found", exception.getMessage());
    }

    @Test
    void fetchValidCountryCodes_shouldReturnListOfValidCountryCodes() {
        List<Country> mockCountryList = List.of(
                new Country("US", new String[]{"en"}),
                new Country("CA", new String[]{"en"}));

        when(holidayApiClient.getCountries()).thenReturn(mockCountryList);

        Set<String> result = countryService.fetchValidCountryCodes();
        assertNotNull(result);
        assertEquals(mockCountryList.size(), result.size());
        assertTrue(result.contains("US"));
        assertTrue(result.contains("CA"));
    }
}