package com.telecom.customer.repository.converter

import com.telecom.customer.repository.entity.CustomerEntity
import com.telecom.customer.repository.entity.PhoneNumberEntity
import spock.lang.Specification
import spock.lang.Title

@Title("[Utility] Convert PhoneNumber Entity to Model")
class PhoneNumberEntityToPhoneNumberConverterTest extends Specification {
    def converter = new PhoneNumberEntityToPhoneNumberConverter()

    def 'Convert PhoneNumber entity to model object'() {
        setup:
        def customerEntity = new CustomerEntity(id: 1, firstName: 'First', lastName: 'Last')
        def phoneEntity = new PhoneNumberEntity(id: 1, number: '0400000001', activated: true, customer: customerEntity)

        when: 'converting phone entity'
        def phone = converter.convert(phoneEntity)

        then: 'phone fields should be populated'
        phone.number == phoneEntity.number
        phone.activated == phoneEntity.activated
    }
}
