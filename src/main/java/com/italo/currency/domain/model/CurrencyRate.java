package com.italo.currency.domain.model;

import com.italo.currency.infrastructure.client.response.ExchangeRateResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CurrencyRate {

    private long timeLastUpdateUnix;
    private long timeNextUpdateUnix;
    private String baseCode;
    private String targetCode;
    private double conversionRate;
    private LocalDateTime timestamp;

    public static CurrencyRate fromExchangeRateResponse(ExchangeRateResponse exchangeRateResponse, String targetCode){
        return CurrencyRate
                .builder()
                .timeLastUpdateUnix(exchangeRateResponse.getTimeLastUpdateUnix())
                .timeNextUpdateUnix(exchangeRateResponse.getTimeNextUpdateUnix())
                .baseCode(exchangeRateResponse.getBaseCode())
                .targetCode(targetCode)
                .conversionRate(exchangeRateResponse.getConversionRates().get(targetCode))
                .timestamp(LocalDateTime.now())
                .build();
    }
}
