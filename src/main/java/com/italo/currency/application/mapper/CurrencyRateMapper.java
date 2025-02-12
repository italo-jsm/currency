package com.italo.currency.application.mapper;

import com.italo.currency.api.dto.CurrencyRateDto;
import com.italo.currency.domain.CurrencyRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyRateMapper {
    CurrencyRateDto toDto(CurrencyRate currencyRate);
}
