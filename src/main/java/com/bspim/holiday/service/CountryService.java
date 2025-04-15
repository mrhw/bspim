package com.bspim.holiday.service;

import com.bspim.holiday.exception.CountryNotFoundException;
import com.bspim.holiday.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private final HolidayApiClient holidayApiClient;

    public CountryService(HolidayApiClient holidayApiClient) {
        this.holidayApiClient = holidayApiClient;
    }

    List<Country> fetchCountries() {
        return holidayApiClient.getCountries();
    }

    public Country getCountry(String code) {
        Optional<Country> countryOpt = fetchCountries().stream().filter(country -> code.equals(country.code())).findFirst();
        if (countryOpt.isPresent()) {
            return countryOpt.get();
        } else {
            throw new CountryNotFoundException(String.format("Country with code %s not found", code));
        }
    }

    public Set<String> fetchValidCountryCodes() {
        return holidayApiClient.getCountries().stream().map(Country::code).collect(Collectors.toSet());
    }

}
