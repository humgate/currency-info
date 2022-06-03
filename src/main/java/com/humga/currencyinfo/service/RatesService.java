package com.humga.currencyinfo.service;

import com.humga.currencyinfo.client.RatesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RatesService {
    private final RatesClient ratesClient;

    @Value("${app.config.open-exchange.appid}")
    private String appId;
    @Value("${app.config.open-exchange.appid-prefix}")
    private String prefix;

    public RatesService(RatesClient ratesClient) {
        this.ratesClient = ratesClient;
    }

    public boolean rateGrown(String symbol) {
        String token = prefix + appId;
        BigDecimal todayRate = ratesClient.getHistoricalRates(token,
                LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".json").getRates().get(symbol);

        BigDecimal yesterdayRate = ratesClient.getHistoricalRates(token,
                        LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE) + ".json")
                .getRates().get(symbol);

        return todayRate.compareTo(yesterdayRate) > 0;
    }
}
