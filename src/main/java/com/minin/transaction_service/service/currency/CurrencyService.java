package com.minin.transaction_service.service.currency;

import com.minin.transaction_service.facade.dto.CurrencyResponse;
import com.minin.transaction_service.model.Currency;

import java.math.BigDecimal;

public interface CurrencyService {

    Currency saveActualCurrency(CurrencyResponse currencyResponse);

    BigDecimal convertCurrency(Currency.Type from, Currency.Type to, BigDecimal amount);

}
