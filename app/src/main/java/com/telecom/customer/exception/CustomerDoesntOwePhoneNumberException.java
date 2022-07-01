package com.telecom.customer.exception;

import com.telecom.customer.controller.exception.InvalidRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerDoesntOwePhoneNumberException extends InvalidRequestException {
    private static final long serialVersionUID = 1;

    private long customerId;
    private String phoneNumber;
}
