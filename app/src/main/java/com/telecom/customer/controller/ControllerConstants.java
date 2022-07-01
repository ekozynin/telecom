package com.telecom.customer.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class ControllerConstants {
    public static final String PATH_FRAGMENT_CUSTOMERS = "customers";
    public static final String PATH_FRAGMENT_PHONE_NUMBERS = "phone-numbers";
    public static final String PATH_FRAGMENT_ACTIVATE_PHONE_NUMBER = "activate-phone-number";
    public static final String PATH_VARIABLE_PHONE_NUMBER = "{phoneNumber}";
    public static final String PATH_VARIABLE_CUSTOMER_ID = "{customerId}";
}
