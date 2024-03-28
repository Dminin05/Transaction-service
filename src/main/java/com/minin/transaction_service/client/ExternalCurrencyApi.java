package com.minin.transaction_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency")
public interface ExternalCurrencyApi {

    @GetMapping("/eod")
    String getActualCurrency(
        @RequestParam("symbol") String symbol,
        @RequestParam("apikey") String apiKey
    );

}

