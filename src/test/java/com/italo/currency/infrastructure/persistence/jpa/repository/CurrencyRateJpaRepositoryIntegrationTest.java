package com.italo.currency.infrastructure.persistence.jpa.repository;

import com.italo.currency.domain.model.CurrencyRate;
import com.italo.currency.infrastructure.IntegrationTestBase;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class CurrencyRateJpaRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private CurrencyRateJpaRepository currencyRateJpaRepository;

    @Test
    public void shouldSaveExchangeRate(){
        CurrencyRate currencyRate = CurrencyRate
                .builder()
                .conversionRate(0.9)
                .timestamp(LocalDateTime.now())
                .baseCode("USD")
                .targetCode("BRL")
                .build();

        currencyRateJpaRepository.saveCurrencyRate(currencyRate);

        List<CurrencyRate> all = currencyRateJpaRepository.findAll();

        Assertions.assertEquals(1, all.size());
        CurrencyRate first = all.getFirst();
        Assertions.assertEquals(first.getConversionRate(), currencyRate.getConversionRate());
        Assertions.assertEquals(first.getBaseCode(), currencyRate.getBaseCode());
        Assertions.assertEquals(first.getTargetCode(), currencyRate.getTargetCode());
        Assertions.assertTrue(Duration.between(first.getTimestamp(), currencyRate.getTimestamp()).abs().compareTo(Duration.ofSeconds(1)) < 0);
    }


}
