package com.italo.currency.infrastructure.client;

import com.italo.currency.infrastructure.client.config.FeignConfig;
import com.italo.currency.infrastructure.client.response.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchangeRateClient", url = "${exchange-rate.client.url}", configuration = FeignConfig.class)
public interface ExchangeRateClient {

    @GetMapping("/{apiKey}/latest/{currency}")
    ExchangeRateResponse getExchangeRate(@PathVariable("apiKey") String apiKey, @PathVariable("currency") String currency);

}
