package com.minin.transaction_service.mappers;

import com.minin.transaction_service.model.Currency;
import org.mapstruct.Mapper;
import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    Currency toEntity(Currency.Type fromCurrency, Currency.Type toCurrency, BigDecimal value);

}
