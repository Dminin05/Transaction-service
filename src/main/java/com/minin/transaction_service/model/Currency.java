package com.minin.transaction_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "currency")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "from_currency")
    @Enumerated(EnumType.STRING)
    private Type fromCurrency;

    @Column(name = "to_currency")
    @Enumerated(EnumType.STRING)
    private Type toCurrency;

    @Column(name = "value")
    private BigDecimal value;

    public enum Type {
        RUB, KZT, USD
    }

}
