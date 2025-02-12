package com.italo.currency.api.controller;

import com.italo.currency.api.dto.CurrencyRateDto;
import com.italo.currency.application.mapper.CurrencyRateMapper;
import com.italo.currency.application.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currency-rate")
@RequiredArgsConstructor
public class CurrencyRateController {

    private final ExchangeRateService exchangeRateService;
    private final CurrencyRateMapper currencyRateMapper;

    @GetMapping
    public ResponseEntity<CurrencyRateDto> getRate(@RequestParam String baseCode, @RequestParam String targetCode){
        return ResponseEntity.ok(currencyRateMapper.toDto(exchangeRateService.getCurrencyRate(baseCode, targetCode)));
    }
}
