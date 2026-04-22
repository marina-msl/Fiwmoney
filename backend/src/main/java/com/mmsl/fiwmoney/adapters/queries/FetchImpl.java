package com.mmsl.fiwmoney.adapters.queries;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mmsl.fiwmoney.domain.ports.Fetch;
import com.mmsl.fiwmoney.dto.StockResultMin;

@Component
public class FetchImpl implements Fetch {

   
    @Value("${stock.api.url}")
    private String stockSearchUrl; 
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BigDecimal getStockPrice(String code) {
        String url = stockSearchUrl + code;

        try {
            StockResultMin response = restTemplate.getForObject(url, StockResultMin.class);
            return response != null ? response.getPrice() : BigDecimal.ZERO;
        } catch (HttpClientErrorException.NotFound e) {
            return BigDecimal.valueOf(-1);
        }
    }
}
