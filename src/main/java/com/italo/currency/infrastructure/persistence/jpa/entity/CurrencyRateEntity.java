package com.italo.currency.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "currency-rate")
public class CurrencyRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDateTime timestamp;
    private Double conversionRate;
    private String targetCode;
    private String baseCode;
}
