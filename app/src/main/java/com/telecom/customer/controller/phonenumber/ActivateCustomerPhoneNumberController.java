package com.telecom.customer.controller.phonenumber;

import com.telecom.customer.controller.ControllerConstants;
import com.telecom.customer.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Active phone numbers", description = "active customer's phone number")
public class ActivateCustomerPhoneNumberController {
    private final PhoneNumberService phoneNumberService;

    @GetMapping(
            value = "/" + ControllerConstants.PATH_FRAGMENT_CUSTOMERS
                    + "/" + ControllerConstants.PATH_VARIABLE_CUSTOMER_ID
                    + "/" + ControllerConstants.PATH_FRAGMENT_ACTIVATE_PHONE_NUMBER
                    + "/" + ControllerConstants.PATH_VARIABLE_PHONE_NUMBER,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "The phone number has been successfully activated.")
    @ResponseStatus(HttpStatus.OK)
    public void activatePhoneNumber(final @PathVariable Long customerId, final @PathVariable String phoneNumber) {
        log.debug("Activating phone number {} for customer id {}", phoneNumber, customerId);

        phoneNumberService.activateCustomerPhoneNumber(customerId, phoneNumber);
    }
}
