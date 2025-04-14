package com.bspim.holiday.service;

import com.bspim.holiday.dto.HolidayRequest;
import com.bspim.holiday.dto.HolidayResponse;

public interface HolidayService {

    HolidayResponse findFirstCommonHoliday(HolidayRequest request);

}
