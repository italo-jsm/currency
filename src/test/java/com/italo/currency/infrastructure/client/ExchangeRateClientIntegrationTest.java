package com.italo.currency.infrastructure.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.italo.currency.infrastructure.client.exception.ExchangeException;
import com.italo.currency.infrastructure.client.response.ExchangeRateResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
public class ExchangeRateClientIntegrationTest {

    static ObjectMapper objectMapper;

    static WireMockContainer wiremock = new WireMockContainer("wiremock/wiremock");

    @Autowired
    private ExchangeRateClient client;

    @BeforeAll
    static void beforeAll() {
        wiremock.start();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @BeforeEach
    void setup() {
        WireMock.configureFor(wiremock.getHost(), wiremock.getPort());
        WireMock.reset();
    }

    @AfterAll
    static void afterAll() {
        wiremock.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("exchange-rate.client.url", wiremock::getBaseUrl);
    }

    @Test
    void shouldReturnExchangeRate_whenApiRespondsSuccessfully() {
        String jsonResponse = """
                {
                    "result": "success",
                    "documentation": "https://www.exchangerate-api.com/docs",
                    "terms_of_use": "https://www.exchangerate-api.com/terms",
                    "time_last_update_unix": 1739318401,
                    "time_last_update_utc": "Wed, 12 Feb 2025 00:00:01 +0000",
                    "time_next_update_unix": 1739404801,
                    "time_next_update_utc": "Thu, 13 Feb 2025 00:00:01 +0000",
                    "base_code": "BRL",
                    "conversion_rates": {
                        "BRL": 1
                    }
                }
                """;

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api-key/latest/BRL"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(jsonResponse)
                        .withStatus(200)));


        ExchangeRateResponse response = client.getExchangeRate("api-key", "BRL");
        Assertions.assertNotNull(response.getConversionRates().get("BRL"));
    }

    @Test
    void shouldThrowExchangeException_whenApiRespondsUnsuccessful() {
        String jsonResponse = """
                {
                    "result": "error",
                    "documentation": "https://www.exchangerate-api.com/docs",
                    "terms-of-use": "https://www.exchangerate-api.com/terms",
                    "error-type": "unsupported-code"
                }
                """;

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api-key/latest/BRLy"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(jsonResponse)
                        .withStatus(404)));


        try{
            ExchangeRateResponse response = client.getExchangeRate("api-key", "BRLy");
            Assertions.fail("Should throw exception");
        }catch (Exception e){
            Assertions.assertInstanceOf(ExchangeException.class, e);
            Assertions.assertTrue(e.getMessage().contains("unsupported-code"));
        }
    }
}
