package com.bspim.holiday.service;

import com.bspim.holiday.config.ClientProperties;
import com.bspim.holiday.exception.ExternalApiException;
import com.bspim.holiday.model.Country;
import com.bspim.holiday.model.ExternalCountriesResponse;
import com.bspim.holiday.model.ExternalHolidayResponse;
import com.bspim.holiday.model.Holiday;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class HolidayApiClient {
    private final RestTemplate restTemplate;
    private final ClientProperties clientProperties;

    public HolidayApiClient(RestTemplate restTemplate, ClientProperties clientProperties) {
        this.restTemplate = restTemplate;
        this.clientProperties = clientProperties;
    }

    public Set<Holiday> getHolidays(Country country, LocalDate date) {
        String query = String.format("holidays?country=%s&year=%s&key=%s&language=%s", country.code(), date.getYear(), clientProperties.getKey(), country.languages()[0]);
        try {
            ExternalHolidayResponse holidays = restTemplate.getForObject(clientProperties.getUrl() + query, ExternalHolidayResponse.class);
            return Objects.requireNonNull(holidays).holidays();
        } catch (Exception e) {
            throw new ExternalApiException(e.getMessage());
        }
    }

    @Cacheable(value = "countries")
    public List<Country> getCountries() {
        String query = String.format("countries?key=%s", clientProperties.getKey());
        try {
            ExternalCountriesResponse countriesResponse = restTemplate.getForObject(clientProperties.getUrl() + query, ExternalCountriesResponse.class);
            return Objects.requireNonNull(countriesResponse).countries();
        } catch (Exception e) {
            throw new ExternalApiException(e.getMessage());
        }
    }

}
