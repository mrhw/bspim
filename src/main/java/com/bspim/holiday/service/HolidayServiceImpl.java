package com.bspim.holiday.service;

import com.bspim.holiday.dto.HolidayRequest;
import com.bspim.holiday.dto.HolidayResponse;
import com.bspim.holiday.model.Holiday;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.bspim.holiday.util.HolidayUtil.findFirstDateAfter;
import static com.bspim.holiday.util.HolidayUtil.getHolidayNameByDate;

@Service
public class HolidayServiceImpl implements HolidayService {

    private final HolidayApiClient holidayApiClient;
    private final CountryCodeService countryCodeService;

    public HolidayServiceImpl(HolidayApiClient holidayApiClient, CountryCodeService countryCodeService) {
        this.holidayApiClient = holidayApiClient;
        this.countryCodeService = countryCodeService;
    }

    @Override
    @Cacheable(value = "holidaysCache", key = "#request.countryCode1 + '-'+ #request.countryCode2 +'-'+#request.date")
    public HolidayResponse findFirstCommonHoliday(HolidayRequest request) {
        List<Holiday> holidaysCountry1 = holidayApiClient.getHolidays(countryCodeService.getCountry(request.getCountryCode1()), request.getDate());
        List<Holiday> holidaysCountry2 = holidayApiClient.getHolidays(countryCodeService.getCountry(request.getCountryCode2()), request.getDate());

        return findFirstCommonHoliday(holidaysCountry1, holidaysCountry2, request.getDate());
    }

    public static HolidayResponse findFirstCommonHoliday(List<Holiday> holidays1, List<Holiday> holidays2, LocalDate date) {
        holidays1.retainAll(holidays2);
        final String commonDate = findFirstDateAfter(date, holidays1);
        return new HolidayResponse(
                commonDate,
                getHolidayNameByDate(holidays1, commonDate),
                getHolidayNameByDate(holidays2, commonDate)
        );

    }
}
