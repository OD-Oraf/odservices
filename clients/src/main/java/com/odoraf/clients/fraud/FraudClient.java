package com.odoraf.clients.fraud;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Open Feign - Used to call 3rd party API's, kinda like postman but in you application
@FeignClient(
        value = "fraud",
        url = "${clients.fraud.url}"
)
public interface FraudClient {
    // Interface to target fraud controller
    @GetMapping(path = "api/v1/fraud-check/{email}")
    FraudCheckResponse isFraudster(@PathVariable("email") String email);

    @PostMapping(path = "api/v1/fraud-check/register")
    FraudCheckResponse registerFraudster(@PathVariable("email") String email);
}
