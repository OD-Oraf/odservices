package com.odoraf.customer;

import com.odoraf.customer.response.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    @RequestBody - take request body and convert to the Java Object type

* */

// REST-API Layer
@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Object> registerCustomer(
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest
    ) {
        try {
            log.info("new customer registration {}", customerRegistrationRequest);
            customerService.registerCustomer(customerRegistrationRequest);

            return ResponseHandler.generateResponse(
                    "User successfully added",
                    HttpStatus.OK,
                    customerRegistrationRequest
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,null);
        }

    }
}
