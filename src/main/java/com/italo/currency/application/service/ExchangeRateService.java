package com.italo.currency.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italo.currency.application.exception.ExchangeInputException;
import com.italo.currency.application.exception.MessageDetails;
import com.italo.currency.domain.CurrencyRate;
import com.italo.currency.infrastructure.client.ExchangeRateClient;
import com.italo.currency.infrastructure.client.exception.ExchangeException;
import com.italo.currency.infrastructure.client.response.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateClient exchangeRateClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${exchange-rate.client.api-key}")
    private String apiKey;

    public CurrencyRate getCurrencyRate(String baseCode, String targetCode){
        try{
            ExchangeRateResponse exchangeRate = exchangeRateClient.getExchangeRate(apiKey, baseCode);
            if(exchangeRate.getConversionRates().get(targetCode) == null){
                throw new ExchangeInputException(MessageDetails.getMessageDetails("unsupported-code"));
            }
            return CurrencyRate.fromExchangeRateResponse(exchangeRate, targetCode);
        }catch (ExchangeException e){
            try {
                throw new ExchangeInputException(MessageDetails.getMessageDetails(objectMapper.readValue(e.getMessage(), ExchangeRateResponse.class).getErrorType()));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
