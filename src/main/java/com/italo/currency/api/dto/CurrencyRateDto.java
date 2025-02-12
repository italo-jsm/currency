package com.italo.currency.api.dto;

import lombok.Data;

@Data
public class CurrencyRateDto {
    private String baseCode;
    private String targetCode;
    private double conversionRate;
}
