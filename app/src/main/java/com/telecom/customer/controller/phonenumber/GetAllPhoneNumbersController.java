package com.telecom.customer.controller.phonenumber;

import com.telecom.customer.controller.ControllerConstants;
import com.telecom.customer.controller.model.PaginatedResponse;
import com.telecom.customer.model.PhoneNumber;
import com.telecom.customer.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Get all phone numbers", description = "get all phone numbers in the system")
public class GetAllPhoneNumbersController {
    private static final String DEFAULT_PAGE_SIZE = "50";
    private static final String DEFAULT_PAGE_NUMBER = "0";

    private final PhoneNumberService phoneNumberService;

    @GetMapping(
            value = "/" + ControllerConstants.PATH_FRAGMENT_PHONE_NUMBERS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "List of the all phone numbers")
    @ResponseStatus(HttpStatus.OK)
    // TODO limit page size to a reasonable number
    public PaginatedResponse<PhoneNumber> getAllPhoneNumbers(
            final @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
            final @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.debug("Retrieving all phone numbers with page={}, size={}", page, size);

        final Pageable pageable = PageRequest.of(page, size);
        log.debug("Retrieving all phone numbers with pageable={}", pageable);

        final PaginatedResponse<PhoneNumber> phoneNumbers = phoneNumberService.getAllPhoneNumbers(pageable);
        log.debug("Retrieved all phone numbers {}", phoneNumbers);

        return phoneNumbers;
    }
}
