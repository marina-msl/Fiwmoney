package com.mmsl.fiwmoney.adapters.out.queries;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mmsl.fiwmoney.application.exception.APIUnavailableException;
import com.mmsl.fiwmoney.domain.ports.Fetch;
import com.mmsl.fiwmoney.dto.StockResultMin;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
public class FetchImpl implements Fetch {

    private static final Logger log = LoggerFactory.getLogger(FetchImpl.class);
   
    @Value("${stock.api.url}")
    private String stockSearchUrl; 
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @CircuitBreaker(name = "searchStockApi", fallbackMethod = "getStockPriceFallback")
    public BigDecimal getStockPrice(String code) {
        log.info("Attempting to call stock API for: " + code);

        String url = stockSearchUrl + code;

        try {
            StockResultMin response = restTemplate.getForObject(url, StockResultMin.class);
            return response != null ? response.getPrice() : BigDecimal.ZERO;
        } catch (HttpClientErrorException.NotFound e) {
            return BigDecimal.valueOf(-1);
        } catch (ResourceAccessException e) {
            throw new APIUnavailableException();
        }
    }

    public BigDecimal getStockPriceFallback(String code, Throwable throwable) {
        log.info("Circuit breaker open - skipping call for: " + code);
        throw new APIUnavailableException();
    }
}
