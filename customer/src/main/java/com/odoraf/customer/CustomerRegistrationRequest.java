package com.odoraf.customer;

// Using record for string mutability
public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {

}
