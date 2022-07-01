package com.telecom.customer.repository.converter;

import com.telecom.customer.model.PhoneNumber;
import com.telecom.customer.repository.entity.PhoneNumberEntity;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public class PhoneNumberEntityToPhoneNumberConverter implements Converter<PhoneNumberEntity, PhoneNumber> {
    @Override
    public PhoneNumber convert(final PhoneNumberEntity phoneNumberEntity) {
        final PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setNumber(phoneNumberEntity.getNumber());
        phoneNumber.setActivated(phoneNumberEntity.isActivated());

        return phoneNumber;
    }
}
