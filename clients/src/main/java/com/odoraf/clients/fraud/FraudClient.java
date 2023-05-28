package com.odoraf.clients.fraud;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Open Feign - Used to call 3rd party API's, kinda like postman but in you application
@FeignClient(value = "fraud")
public interface FraudClient {
    // Interface to target fraud controller
    @GetMapping(path = "api/v1/fraud-check/{customerId}")
    FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId);
}
