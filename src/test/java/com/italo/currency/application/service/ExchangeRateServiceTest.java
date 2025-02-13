package com.italo.currency.application.service;

import com.italo.currency.application.exception.ExchangeInputException;
import com.italo.currency.domain.model.CurrencyRate;
import com.italo.currency.domain.repository.CurrencyRateRepository;
import com.italo.currency.infrastructure.client.ExchangeRateClient;
import com.italo.currency.infrastructure.client.exception.ExchangeException;
import com.italo.currency.infrastructure.client.response.ExchangeRateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Mock
    CurrencyRateRepository currencyRateRepository;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Test
    public void shouldReturnValidCurrencyRate_whenExchangeRateClientReturnsSuccessfully(){
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setBaseCode("BRL");
        exchangeRateResponse.setResult("success");
        exchangeRateResponse.setConversionRates(Map.of("BRL", 1.00F, "USD", 5.89F));
        Mockito.when(exchangeRateClient.getExchangeRate("api-key", "BRL")).thenReturn(exchangeRateResponse);
        Mockito.when(currencyRateRepository.saveCurrencyRate(any())).thenReturn(null);
        ReflectionTestUtils.setField(exchangeRateService, "apiKey", "api-key");
        CurrencyRate currencyRate = exchangeRateService.getCurrencyRate("BRL", "USD");
        Assertions.assertNotNull(currencyRate);
        Assertions.assertEquals(currencyRate.getBaseCode(), "BRL");
        Assertions.assertEquals(currencyRate.getTargetCode(), "USD");
    }

    @Test
    public void shouldThrowException_whenTargetCodeIsNotPresent(){
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setBaseCode("BRL");
        exchangeRateResponse.setResult("success");
        exchangeRateResponse.setConversionRates(Map.of("BRL", 1.00F, "USD", 5.89F));
        Mockito.when(exchangeRateClient.getExchangeRate("api-key", "BRL")).thenReturn(exchangeRateResponse);
        ReflectionTestUtils.setField(exchangeRateService, "apiKey", "api-key");
        try{
            exchangeRateService.getCurrencyRate("BRL", "EUR");
        }catch (Exception e){
            Assertions.assertInstanceOf(ExchangeInputException.class, e);
        }
    }

    @Test
    public void shouldThrowException_whenBaseBaseCodeIsNotSupported(){
        String jsonMessage = """
                {
                    "result": "error",
                    "documentation": "https://www.exchangerate-api.com/docs",
                    "terms-of-use": "https://www.exchangerate-api.com/terms",
                    "error-type": "unsupported-code"
                }
                """;
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setBaseCode("BRL");
        exchangeRateResponse.setResult("success");
        exchangeRateResponse.setConversionRates(Map.of("BRL", 1.00F, "USD", 5.89F));
        Mockito.when(exchangeRateClient.getExchangeRate("api-key", "BRL")).thenThrow(new ExchangeException(jsonMessage));
        ReflectionTestUtils.setField(exchangeRateService, "apiKey", "api-key");
        try{
            exchangeRateService.getCurrencyRate("BRL", "EUR");
        }catch (Exception e){
            Assertions.assertInstanceOf(ExchangeInputException.class, e);
        }
    }

    @Test
    public void shouldThrowRuntimeException_whenErrorResponseNotProcessable(){
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setBaseCode("BRL");
        exchangeRateResponse.setResult("success");
        exchangeRateResponse.setConversionRates(Map.of("BRL", 1.00F, "USD", 5.89F));
        Mockito.when(exchangeRateClient.getExchangeRate("api-key", "BRL")).thenThrow(new ExchangeException("testMessage"));
        ReflectionTestUtils.setField(exchangeRateService, "apiKey", "api-key");
        try{
            exchangeRateService.getCurrencyRate("BRL", "EUR");
        }catch (Exception e){
            Assertions.assertInstanceOf(RuntimeException.class, e);
        }
    }
}
