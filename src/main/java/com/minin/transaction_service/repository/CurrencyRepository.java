package com.minin.transaction_service.repository;

import com.minin.transaction_service.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {

    Optional<Currency> findByFromCurrencyAndToCurrency(Currency.Type fromCurrency, Currency.Type toCurrency);

}
