package com.odoraf.fraud;

import com.odoraf.clients.fraud.FraudCheckResponse;
import com.odoraf.fraud.response.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;

@RequestMapping(path="api/v1/fraud-check")
@RestController
@AllArgsConstructor
@Slf4j
public class FraudController {
    private final FraudCheckService fraudCheckService;

    // TODO: Update existing customer from valid to fraudster and vice-versa

    @GetMapping(path = "{email}")
    public FraudCheckResponse isFraudster(
            @PathVariable("email") String email
    ) {
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(email);
        log.info("fraud check request for customer {}", email);
        return new FraudCheckResponse(isFraudulentCustomer);
    }

    @PostMapping(path = "register")
    public ResponseEntity<Object> registerFraudster(@RequestBody FraudRegistrationRequest fraudRegistrationRequest) {
        try {
            fraudCheckService.createFraudster(fraudRegistrationRequest);
            log.info("Email {} registered as fraudster", fraudRegistrationRequest.email());
            return ResponseHandler.generateResponse(
                    "fraudster successfully registered",
                    HttpStatus.OK,
                    fraudRegistrationRequest.email()
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

}
