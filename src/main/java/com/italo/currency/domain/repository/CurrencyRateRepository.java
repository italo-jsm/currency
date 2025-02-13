package com.italo.currency.domain.repository;

import com.italo.currency.domain.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateRepository {

    CurrencyRate saveCurrencyRate(CurrencyRate currencyRate);
    List<CurrencyRate> findAll();
}
