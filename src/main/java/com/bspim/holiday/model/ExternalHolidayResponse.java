package com.bspim.holiday.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalHolidayResponse(List<Holiday> holidays) {
}