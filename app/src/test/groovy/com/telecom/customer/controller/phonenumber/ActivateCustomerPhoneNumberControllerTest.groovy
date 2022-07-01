package com.telecom.customer.controller.phonenumber

import com.telecom.customer.controller.ControllerConstants
import com.telecom.customer.exception.CustomerDoesntOwePhoneNumberException
import com.telecom.customer.model.PhoneNumber
import com.telecom.customer.service.PhoneNumberService
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification
import spock.lang.Title

@Title("[Controller] Activate Customer Phone Number")
@WebMvcTest(controllers = ActivateCustomerPhoneNumberController.class)
class ActivateCustomerPhoneNumberControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    PhoneNumberService phoneNumberService = Mock()

    def jsonSlurper = new JsonSlurper()

    def "Activate customer phone number"() {
        setup: "a customer who has 2 not activate phone numbers"
        def customerId = 1
        def nonExistentCustomerId = 999

        def phoneNumber1 = new PhoneNumber(number: "0400000001", activated: false)
        def phoneNumber2 = new PhoneNumber(number: "0400000002", activated: false)

        def phoneNumber1Json = jsonSlurper.parseText(JsonOutput.toJson(phoneNumber1))
        def phoneNumber2Json = jsonSlurper.parseText(JsonOutput.toJson(phoneNumber2))

        when: "activating existing customer phone number"
        def result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/" + ControllerConstants.PATH_FRAGMENT_CUSTOMERS
                                + "/" + customerId
                                + "/" + ControllerConstants.PATH_FRAGMENT_ACTIVATE_PHONE_NUMBER
                                + "/" + phoneNumber1.getNumber())
                        .accept(MediaType.APPLICATION_JSON)
        )

        then: "HTTP status code '200 OK' is returned"
        result
                .andExpect(MockMvcResultMatchers.status().isOk())

        and:
        1 * phoneNumberService.activateCustomerPhoneNumber(customerId, phoneNumber1.getNumber())

        when: "activating non-existing customer phone number"
        result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/" + ControllerConstants.PATH_FRAGMENT_CUSTOMERS
                                + "/" + nonExistentCustomerId
                                + "/" + ControllerConstants.PATH_FRAGMENT_ACTIVATE_PHONE_NUMBER
                                + "/" + phoneNumber1.getNumber())
                        .accept(MediaType.APPLICATION_JSON)
        )

        then: "HTTP status code '400 Bad Request' is returned"
        result
                .andExpect(MockMvcResultMatchers.status().isBadRequest())

        and:
        1 * phoneNumberService.activateCustomerPhoneNumber(nonExistentCustomerId, phoneNumber1.getNumber()) >> {
            throw new CustomerDoesntOwePhoneNumberException(nonExistentCustomerId, phoneNumber1.getNumber());
        }
    }
}
