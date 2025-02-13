package com.italo.currency.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italo.currency.application.exception.ExchangeInputException;
import com.italo.currency.application.exception.MessageDetails;
import com.italo.currency.application.mapper.CurrencyRateMapper;
import com.italo.currency.domain.model.CurrencyRate;
import com.italo.currency.domain.repository.CurrencyRateRepository;
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
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyRateMapper currencyRateMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${exchange-rate.client.api-key}")
    private String apiKey;

    public CurrencyRate getCurrencyRate(String baseCode, String targetCode){
        try{
            ExchangeRateResponse exchangeRate = exchangeRateClient.getExchangeRate(apiKey, baseCode);
            if(exchangeRate.getConversionRates().get(targetCode) == null){
                throw new ExchangeInputException(MessageDetails.getMessageDetails("unsupported-code"));
            }
            CurrencyRate currencyRate = CurrencyRate.fromExchangeRateResponse(exchangeRate, targetCode);
            currencyRateRepository.saveCurrencyRate(currencyRate);
            return currencyRate;
        }catch (ExchangeException e){
            try {
                throw new ExchangeInputException(MessageDetails.getMessageDetails(objectMapper.readValue(e.getMessage(), ExchangeRateResponse.class).getErrorType()));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
