package com.bspim.holiday.validation;

import com.bspim.holiday.dto.ValidCountryCode;
import com.bspim.holiday.service.CountryCodeService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CountryCodeValidatorTest {

    @Mock
    private CountryCodeService countryCodeService;

    @Mock
    private ConstraintValidatorContext context;

    private CountryCodeValidator countryCodeValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryCodeValidator = new CountryCodeValidator(countryCodeService);
    }

    @Test
    void isValid_ShouldReturnTrue_ForValidCountryCode() {
        when(countryCodeService.fetchValidCountryCodes()).thenReturn(Set.of("US", "CA"));
        countryCodeValidator.initialize(mock(ValidCountryCode.class));

        assertTrue(countryCodeValidator.isValid("US", context));
        assertTrue(countryCodeValidator.isValid("CA", context));
    }

    @Test
    void isValid_ShouldReturnFalse_ForValidCountryCode() {
        when(countryCodeService.fetchValidCountryCodes()).thenReturn(Set.of("US", "CA"));
        countryCodeValidator.initialize(mock(ValidCountryCode.class));

        assertFalse(countryCodeValidator.isValid("XX", context));
        assertFalse(countryCodeValidator.isValid(null, context));
    }
}