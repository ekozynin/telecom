package com.telecom.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerIdNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    private long customerId;
}
