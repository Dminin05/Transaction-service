package com.minin.transaction_service.utils;

public enum CurrencyPairsUtils {
    RUB_KZT("RUB/KZT"),
    KZT_USD("KZT/USD"),
    RUB_USD("RUB/USD");

    public final String value;

    CurrencyPairsUtils(String value) {
        this.value = value;
    }

}
