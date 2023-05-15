package com.example.clients.fraud;


import org.springframework.cloud.openfeign.FeignClient;

// Open Feign - Used to call 3rd party API's
@FeignClient()
public interface FraudClient {
}
