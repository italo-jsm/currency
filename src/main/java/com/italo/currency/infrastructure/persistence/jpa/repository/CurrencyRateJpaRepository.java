package com.italo.currency.infrastructure.persistence.jpa.repository;

import com.italo.currency.domain.model.CurrencyRate;
import com.italo.currency.domain.repository.CurrencyRateRepository;
import com.italo.currency.infrastructure.persistence.jpa.dao.CurrencyRateDao;
import com.italo.currency.infrastructure.persistence.jpa.mapper.CurrencyRateEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CurrencyRateJpaRepository implements CurrencyRateRepository {

    private final CurrencyRateDao currencyRateDao;
    private final CurrencyRateEntityMapper currencyRateEntityMapper;

    @Override
    public CurrencyRate saveCurrencyRate(CurrencyRate currencyRate) {
        return currencyRateEntityMapper.toDomain(currencyRateDao.save(currencyRateEntityMapper.toEntity(currencyRate)));
    }

    @Override
    public List<CurrencyRate> findAll() {
        return currencyRateDao.findAll().stream().map(currencyRateEntityMapper::toDomain).toList();
    }


}
