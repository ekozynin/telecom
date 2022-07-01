package com.telecom.customer.controller.phonenumber

import com.google.common.collect.Lists
import com.telecom.customer.controller.ControllerConstants
import com.telecom.customer.exception.CustomerIdNotFoundException
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

@Title("[Controller] Get Customer Phone Numbers")
@WebMvcTest(controllers = GetCustomerPhoneNumbersController.class)
class GetCustomerPhoneNumbersControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    PhoneNumberService phoneNumberService = Mock()

    def jsonSlurper = new JsonSlurper()

    def "Retrieve all customer phone numbers"() {
        setup: "2 phone numbers for the customer"
        def customerId = 1
        def nonExistentCustomerId = 999

        def phoneNumber1 = new PhoneNumber(number: "0400000001")
        def phoneNumber2 = new PhoneNumber(number: "0400000002")

        def phoneNumber1Json = jsonSlurper.parseText(JsonOutput.toJson(phoneNumber1))
        def phoneNumber2Json = jsonSlurper.parseText(JsonOutput.toJson(phoneNumber2))

        when: "getting all phone numbers for the existing customer"
        def result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/" + ControllerConstants.PATH_FRAGMENT_CUSTOMERS
                                + "/" + customerId
                                + "/" + ControllerConstants.PATH_FRAGMENT_PHONE_NUMBERS)
                        .accept(MediaType.APPLICATION_JSON)
        )

        then: "JSON response and HTTP status code '200 OK' are returned"
        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))

        and: 'response has 2 phone numbers'
        def responseJson = jsonSlurper.parseText(result.andReturn().getResponse().getContentAsString())

        responseJson.size() == 2
        responseJson[0] == phoneNumber1Json
        responseJson[1] == phoneNumber2Json

        and:
        1 * phoneNumberService.getCustomerPhoneNumbers(customerId) >> {
            Lists.asList(phoneNumber1, phoneNumber2)
        }

        when: "getting all phone numbers for the non-existing customer"
        result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/" + ControllerConstants.PATH_FRAGMENT_CUSTOMERS
                                + "/" + nonExistentCustomerId
                                + "/" + ControllerConstants.PATH_FRAGMENT_PHONE_NUMBERS)
                        .accept(MediaType.APPLICATION_JSON)
        )

        then: "HTTP status code '400 Bad Request' is returned"
        result
                .andExpect(MockMvcResultMatchers.status().isBadRequest())

        and:
        1 * phoneNumberService.getCustomerPhoneNumbers(nonExistentCustomerId) >> {
            throw new CustomerIdNotFoundException(nonExistentCustomerId);
        }
    }
}
