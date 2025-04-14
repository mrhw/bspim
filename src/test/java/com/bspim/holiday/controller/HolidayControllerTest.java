package com.bspim.holiday.controller;

import com.bspim.holiday.dto.HolidayRequest;
import com.bspim.holiday.dto.HolidayResponse;
import com.bspim.holiday.exception.GlobalExceptionHandler;
import com.bspim.holiday.exception.InvalidInputException;
import com.bspim.holiday.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HolidayControllerTest {

    private MockMvc mockMvc;
    @Mock
    private HolidayService holidayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new HolidayController(holidayService)).setControllerAdvice(TestControllerAdvice.class).build();
    }

    @Test
    void getCommonHoliday_shouldReturnCOmmonHoliday() throws Exception {
        HolidayResponse response = new HolidayResponse("2023-12-25", "Christmas", "Christmas");
        when(holidayService.findFirstCommonHoliday(any(HolidayRequest.class))).thenReturn(response);

        mockMvc.perform(get("/api/holidays/common")
                .param("countryCode1", "US")
                .param("countryCode2", "CA")
                .param("date","2023-12-25")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name1").value("Christmas"));
    }

    @Test
    void getCommonHoliday_InvalidCountryCode_badRequest() throws Exception {
        when(holidayService.findFirstCommonHoliday(any(HolidayRequest.class))).thenThrow(new InvalidInputException("Invalid country code"));
        mockMvc.perform(get("/api/holidays/common")
                .param("countryCode1", "XX")
                .param("countryCode2", "CA")
                .param("date","2023-12-25")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

@ControllerAdvice
class TestControllerAdvice extends GlobalExceptionHandler {

}