package com.telecom.customer.service

import com.google.common.collect.Lists
import com.telecom.customer.exception.CustomerDoesntOwePhoneNumberException
import com.telecom.customer.exception.CustomerIdNotFoundException
import com.telecom.customer.model.PhoneNumber
import com.telecom.customer.repository.PhoneNumberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import spock.lang.Title

import javax.transaction.Transactional

@Title("[Service] Phone Number Service")
@SpringBootTest
@Transactional
class PhoneNumberServiceTest extends Specification {
    @Autowired
    PhoneNumberService phoneNumberService

    @Autowired
    PhoneNumberRepository phoneNumberRepository

    def 'Retrieve all phone numbers'() {
        setup:
        def page = 0
        def size = 10

        when: 'retrieving all phone numbers'
        def paginatedResponse = phoneNumberService.getAllPhoneNumbers(PageRequest.of(page, size))

        then: 'all phone numbers should be returned'
        paginatedResponse.page == page
        paginatedResponse.size == 2
        paginatedResponse.totalPages == 1
        paginatedResponse.content == Lists.asList(
                new PhoneNumber(number: '0400000001', activated: false),
                new PhoneNumber(number: '0400000002', activated: false))
    }

    def 'Activate customer phone number'() {
        setup:
        def customerId = 1
        def phoneNumber = '0400000001'

        when: 'activating existing customer phone number'
        phoneNumberService.activateCustomerPhoneNumber(customerId, phoneNumber)

        then: 'it is activated in the database'
        def phoneNumberEntityOptional1 = phoneNumberRepository.findByCustomerIdAndNumber(customerId, phoneNumber)

        phoneNumberEntityOptional1.isPresent()
        phoneNumberEntityOptional1.get().number == phoneNumber
        phoneNumberEntityOptional1.get().activated

        when: 'activating existing customer phone number second time'
        phoneNumberService.activateCustomerPhoneNumber(customerId, phoneNumber)

        then: 'it remains activated in the database'
        def phoneNumberEntityOptional2 = phoneNumberRepository.findByCustomerIdAndNumber(customerId, phoneNumber)

        phoneNumberEntityOptional2.isPresent()
        phoneNumberEntityOptional2.get().number == phoneNumber
        phoneNumberEntityOptional2.get().activated

        when: 'activating non-existing customer phone number'
        phoneNumberService.activateCustomerPhoneNumber(-1, phoneNumber)

        then:
        thrown(CustomerDoesntOwePhoneNumberException)
    }

    def 'Get customer phone numbers'() {
        setup:
        def customerId = 1

        when: 'get existing customer phone numbers'
        def phoneNumbers = phoneNumberService.getCustomerPhoneNumbers(customerId)

        then: 'list of phone numbers is returned'
        phoneNumbers == Lists.asList(
                new PhoneNumber(number: '0400000001', activated: false),
                new PhoneNumber(number: '0400000002', activated: false))

        when: 'get non-existing customer phone numbers'
        phoneNumberService.getCustomerPhoneNumbers(-1)

        then:
        thrown(CustomerIdNotFoundException)
    }
}
