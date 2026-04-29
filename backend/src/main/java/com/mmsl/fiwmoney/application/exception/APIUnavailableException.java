package com.mmsl.fiwmoney.application.exception;

import com.mmsl.fiwmoney.domain.exception.AbstractException;

public class APIUnavailableException extends AbstractException {

    public APIUnavailableException() {
        super(
            "API is currently unavailable",
            503,
            "The API search-stock-api  is currently unavailable"
        );
    }
}