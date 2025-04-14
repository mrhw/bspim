package com.bspim.holiday.validation;

import com.bspim.holiday.dto.ValidCountryCode;
import com.bspim.holiday.service.CountryCodeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {

    private Set<String> validCountryCodes;
    private final CountryCodeService countryCodeService;

    @Autowired
    public CountryCodeValidator(CountryCodeService countryCodeService) {
        this.countryCodeService = countryCodeService;
    }

    @Override
    public void initialize(ValidCountryCode constraintAnnotation) {
        this.validCountryCodes = countryCodeService.fetchValidCountryCodes();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && validCountryCodes.contains(s.toUpperCase());
    }
}
