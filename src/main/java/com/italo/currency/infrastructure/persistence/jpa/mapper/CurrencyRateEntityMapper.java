package com.italo.currency.infrastructure.persistence.jpa.mapper;

import com.italo.currency.domain.model.CurrencyRate;
import com.italo.currency.infrastructure.persistence.jpa.entity.CurrencyRateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyRateEntityMapper {
    CurrencyRate toDomain(CurrencyRateEntity currencyRateEntity);
    CurrencyRateEntity toEntity(CurrencyRate currencyRate);
}
