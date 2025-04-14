package com.bspim.holiday.dto;

import com.bspim.holiday.validation.CountryCodeValidator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CountryCodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCountryCode {

    String message() default "Invalid Country Code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
