package com.minin.transaction_service.facade.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CurrencyResponse {
    private String symbol;
    private String exchange;
    private Date datetime;
    private String timestamp;
    private BigDecimal close;
}
