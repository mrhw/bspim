package com.bspim.holiday.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalHolidayResponse(Set<Holiday> holidays) {
}