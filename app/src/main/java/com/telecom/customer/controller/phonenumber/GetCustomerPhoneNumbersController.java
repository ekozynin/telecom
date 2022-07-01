package com.telecom.customer.controller.phonenumber;

import com.telecom.customer.controller.ControllerConstants;
import com.telecom.customer.controller.exception.InvalidRequestException;
import com.telecom.customer.exception.CustomerIdNotFoundException;
import com.telecom.customer.model.PhoneNumber;
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

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Get customer phone numbers", description = "get all phone numbers of a single customer")
public class GetCustomerPhoneNumbersController {
    private final PhoneNumberService phoneNumberService;

    @GetMapping(
            value = "/" + ControllerConstants.PATH_FRAGMENT_CUSTOMERS
                    + "/" + ControllerConstants.PATH_VARIABLE_CUSTOMER_ID
                    + "/" + ControllerConstants.PATH_FRAGMENT_PHONE_NUMBERS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "List of the customer's phone numbers")
    @ApiResponse(responseCode = "400", description = "Invalid customer id supplied")
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("PMD.PreserveStackTrace")
    public List<PhoneNumber> getCustomerPhoneNumbers(@PathVariable final Long customerId) {
        log.debug("Retrieving all phone numbers for customer id {}", customerId);

        try {
            final List<PhoneNumber> customerPhoneNumbers = phoneNumberService.getCustomerPhoneNumbers(customerId);
            log.debug("Retrieved phone numbers {} for customer id {}", customerPhoneNumbers, customerId);

            return customerPhoneNumbers;
        } catch (CustomerIdNotFoundException exception) {
            log.debug("There is no customer with customer id {}", exception.getCustomerId());

            throw new InvalidRequestException();
        }
    }
}
