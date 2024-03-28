package com.minin.transaction_service.service.currency;

import com.minin.transaction_service.exceptions.ConversionException;
import com.minin.transaction_service.facade.dto.CurrencyResponse;
import com.minin.transaction_service.mappers.CurrencyMapper;
import com.minin.transaction_service.model.Currency;
import com.minin.transaction_service.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    @Override
    public Currency saveActualCurrency(CurrencyResponse currencyResponse) {

        String[] currencyArray = currencyResponse.getSymbol().split("/");
        Currency.Type fromCurrency = Currency.Type.valueOf(currencyArray[0]);
        Currency.Type toCurrency = Currency.Type.valueOf(currencyArray[1]);

        Currency currency = currencyRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency)
                .orElse(null);

        if (currency != null) {
            currency.setValue(currencyResponse.getClose());
        } else {
            currency = currencyMapper.toEntity(fromCurrency, toCurrency, currencyResponse.getClose());
        }

        return currencyRepository.save(currency);
    }

    @Override
    public BigDecimal convertCurrency(Currency.Type from, Currency.Type to, BigDecimal amount) {

        Currency currency = currencyRepository.findByFromCurrencyAndToCurrency(from, to)
                .orElseThrow(() -> new ConversionException(String.format("something_go_wrong_in_conversion_from_%s_to_%s", from, to)));

        return amount.multiply(currency.getValue());
    }

}
