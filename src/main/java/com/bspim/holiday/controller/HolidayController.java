package com.bspim.holiday.controller;

import com.bspim.holiday.dto.HolidayRequest;
import com.bspim.holiday.dto.HolidayResponse;
import com.bspim.holiday.dto.ValidCountryCode;
import com.bspim.holiday.service.HolidayService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/holidays")
@Validated
public class HolidayController {

    private final HolidayService holidayService;
    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("common")
    public ResponseEntity<HolidayResponse> getCommonHoliday(
            @Valid @ValidCountryCode @RequestParam("countryCode1") String countryCode1,
            @Valid @ValidCountryCode @RequestParam("countryCode2") String countryCode2,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        HolidayRequest holidayRequest = new HolidayRequest(countryCode1, countryCode2, date);

        return ResponseEntity.ok().body(holidayService.findFirstCommonHoliday(holidayRequest));
    }
}
