package com.telecom.customer

import com.telecom.customer.service.PhoneNumberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TelecomAppTest extends Specification {
    @Autowired
    PhoneNumberService phoneNumberService

    def "Spring application context loads"() {
        when: 'spring application context is loaded'
        // application context is loaded because of @SpringBootTest annotation

        then: 'phone number service is instantiated'
        phoneNumberService
    }
}
