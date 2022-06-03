package com.humga.currencyinfo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class RatesDTO {
    String base;
    Map<String, BigDecimal> rates;
}
