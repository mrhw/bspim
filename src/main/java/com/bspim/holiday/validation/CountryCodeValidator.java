package com.bspim.holiday.validation;

import com.bspim.holiday.dto.ValidCountryCode;
import com.bspim.holiday.service.CountryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {

    private Set<String> validCountryCodes;
    private final CountryService countryService;

    @Autowired
    public CountryCodeValidator(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void initialize(ValidCountryCode constraintAnnotation) {
        this.validCountryCodes = countryService.fetchValidCountryCodes();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && validCountryCodes.contains(s.toUpperCase());
    }
}
