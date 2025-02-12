package com.italo.currency.infrastructure.client.config;

import com.italo.currency.infrastructure.client.exception.ExchangeException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;

public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String responseBody = response.body() != null
                    ? new String(response.body().asInputStream().readAllBytes())
                    : "No body";


            if (response.status() != 200) {
                return new ExchangeException(responseBody);
            }

            return defaultErrorDecoder.decode(methodKey, response);
        } catch (IOException e) {
            return new RuntimeException("Error reading response body", e);
        }
    }
}

