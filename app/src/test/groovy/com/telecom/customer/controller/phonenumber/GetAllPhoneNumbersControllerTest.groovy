package com.telecom.customer.controller.phonenumber

import com.google.common.collect.Lists
import com.telecom.customer.controller.ControllerConstants
import com.telecom.customer.controller.model.PaginatedResponse
import com.telecom.customer.model.PhoneNumber
import com.telecom.customer.service.PhoneNumberService
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification
import spock.lang.Title

@Title("[Controller] Get All Phone Numbers")
@WebMvcTest(controllers = GetAllPhoneNumbersController.class)
class GetAllPhoneNumbersControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    PhoneNumberService phoneNumberService = Mock()

    def jsonSlurper = new JsonSlurper()

    def "Get all phone numbers"() {
        setup: "list of phone numbers"
        def phoneNumber1 = new PhoneNumber(number: "0400000001", activated: false)
        def phoneNumber2 = new PhoneNumber(number: "0400000002", activated: false)

        def phoneNumber1Json = jsonSlurper.parseText(JsonOutput.toJson(phoneNumber1))
        def phoneNumber2Json = jsonSlurper.parseText(JsonOutput.toJson(phoneNumber2))

        when: "retrieving all phone numbers"
        def result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/" + ControllerConstants.PATH_FRAGMENT_PHONE_NUMBERS)
                        .accept(MediaType.APPLICATION_JSON)
        )

        then: "HTTP status code '200 OK' is returned"
        result
                .andExpect(MockMvcResultMatchers.status().isOk())

        and:
        1 * phoneNumberService.getAllPhoneNumbers(_) >> { arguments ->
            Pageable pageable = arguments[0]

            assert pageable.getPageSize() == 50
            assert pageable.getPageNumber() == 0

            def phoneNumbersList = Lists.asList(phoneNumber1, phoneNumber2);
            return new PaginatedResponse<>(phoneNumbersList, phoneNumbersList.size(), 0, 1);
        }
    }

    def "Server error is handled when getting all phone numbers"() {
        when: "retrieving all phone numbers"
        def result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/" + ControllerConstants.PATH_FRAGMENT_PHONE_NUMBERS)
                        .accept(MediaType.APPLICATION_JSON)
        )

        then: "HTTP status code '500 Server Error' is returned"
        result.andExpect(MockMvcResultMatchers.status().is5xxServerError())

        and:
        1 * phoneNumberService.getAllPhoneNumbers(_) >> { throw new RuntimeException() }
    }
}
