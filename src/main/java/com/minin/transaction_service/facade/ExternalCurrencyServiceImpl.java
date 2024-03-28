package com.minin.transaction_service.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minin.transaction_service.client.ExternalCurrencyApi;
import com.minin.transaction_service.facade.dto.CurrencyResponse;
import com.minin.transaction_service.model.Currency;
import com.minin.transaction_service.service.currency.CurrencyService;
import com.minin.transaction_service.properties.CurrencyProperties;
import com.minin.transaction_service.utils.CurrencyPairsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalCurrencyServiceImpl implements ExternalCurrencyService {

    private final ExternalCurrencyApi externalCurrencyApi;
    private final ObjectMapper objectMapper;
    private final CurrencyProperties currencyProperties;

    private final CurrencyService currencyService;

    @Override
    @Scheduled(cron = "0 0 23 * * *")
    @Async
    public void updateAllCurrencies() {

        Currency currencyUsdRubPair = updateUsdRubPair();
        CurrencyResponse response = null;

        try {
            response = objectMapper.readValue(externalCurrencyApi.getActualCurrency(CurrencyPairsUtils.RUB_KZT.value, currencyProperties.getApikey()), CurrencyResponse.class);
            log.info(response.toString());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        response.setClose(currencyUsdRubPair.getValue().divide(response.getClose(), 4));
        response.setSymbol(CurrencyPairsUtils.KZT_USD.value);

        currencyService.saveActualCurrency(response);
    }

    private Currency updateUsdRubPair() {

        CurrencyResponse response = null;

        try {
            response = objectMapper.readValue(externalCurrencyApi.getActualCurrency(CurrencyPairsUtils.RUB_USD.value, currencyProperties.getApikey()), CurrencyResponse.class);
            log.info(response.toString());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return currencyService.saveActualCurrency(response);
    }

}
