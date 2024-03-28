package com.minin.transaction_service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.currency")
@Data
public class CurrencyProperties {
    private String apikey;
}
