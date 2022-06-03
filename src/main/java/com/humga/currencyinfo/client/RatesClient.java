package com.humga.currencyinfo.client;

import com.humga.currencyinfo.dto.RatesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "currency-rates", url = "https://openexchangerates.org/api/")
public interface RatesClient {

    @GetMapping("/historical/{date}")
    RatesDTO getHistoricalRates(
            @RequestHeader("Authorization") String token,
            @PathVariable String date);
}

