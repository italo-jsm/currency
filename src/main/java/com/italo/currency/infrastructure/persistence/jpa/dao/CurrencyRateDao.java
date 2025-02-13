package com.italo.currency.infrastructure.persistence.jpa.dao;

import com.italo.currency.infrastructure.persistence.jpa.entity.CurrencyRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateDao extends JpaRepository<CurrencyRateEntity, String> {
}
