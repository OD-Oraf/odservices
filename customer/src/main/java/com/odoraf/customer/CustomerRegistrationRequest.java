package com.odoraf.customer;

// Using record for string mutability
/*
    Public record - Define instance variables with types in constructor parameter list
    Compiler automatically generate the constructor getter/setter methods, equals(), hashCode() and toString()
* */

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {

}
