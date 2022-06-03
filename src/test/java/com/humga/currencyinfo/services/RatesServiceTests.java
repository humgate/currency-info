package com.humga.currencyinfo.services;

import com.humga.currencyinfo.client.RatesClient;
import com.humga.currencyinfo.dto.RatesDTO;
import com.humga.currencyinfo.service.RatesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@SpringBootTest()
class RatesServiceTests {
    @MockBean
    RatesClient mockRatesClient;
    @Autowired
    RatesService ratesService;
    private static final ObjectMapper mapper = new ObjectMapper();

    private static RatesDTO todayRates;
    private static RatesDTO yesterdayLowerRates;
    private static RatesDTO yesterdayHigerRates;


    @BeforeAll
    static void initTestRates() throws IOException {
        //Init test rates
        todayRates =
                mapper.readValue("{\"base\": \"USD\",\"rates\": {\"EUR\": 0.933463}}", RatesDTO.class);
        yesterdayLowerRates =
                mapper.readValue("{\"base\": \"USD\",\"rates\": {\"EUR\": 0.903463}}", RatesDTO.class);
        yesterdayHigerRates =
                mapper.readValue("{\"base\": \"USD\",\"rates\": {\"EUR\": 0.953463}}", RatesDTO.class);
    }

    @Test
    public void ratesServiceRateGrownRisenTest() {
        //given rates initialized in @BeforeAll

        //when
        when(mockRatesClient.getHistoricalRates(
                any(),
                eq(LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".json")))
                .thenReturn(todayRates);

        when(mockRatesClient.getHistoricalRates(
                any(),
                eq(LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE) + ".json")))
                .thenReturn(yesterdayLowerRates);

        //then
        assertTrue(ratesService.rateGrown("EUR"));
    }

    @Test
    public void ratesServiceRateGrownFallenTest() {
        //given rates initialized in @BeforeAll

        //when
        when(mockRatesClient.getHistoricalRates(
                any(),
                eq(LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".json")))
                .thenReturn(todayRates);

        when(mockRatesClient.getHistoricalRates(
                any(),
                eq(LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE) + ".json")))
                .thenReturn(yesterdayHigerRates);

        //then
        assertFalse(ratesService.rateGrown("EUR"));
    }
}


