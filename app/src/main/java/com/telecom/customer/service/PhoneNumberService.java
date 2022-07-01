package com.telecom.customer.service;

import com.telecom.customer.controller.model.PaginatedResponse;
import com.telecom.customer.exception.CustomerDoesntOwePhoneNumberException;
import com.telecom.customer.exception.CustomerIdNotFoundException;
import com.telecom.customer.model.PhoneNumber;
import com.telecom.customer.repository.CustomerRepository;
import com.telecom.customer.repository.PhoneNumberRepository;
import com.telecom.customer.repository.entity.CustomerEntity;
import com.telecom.customer.repository.entity.PhoneNumberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;

    private final CustomerRepository customerRepository;

    private final ConversionService conversionService;

    @SuppressWarnings("PMD.PreserveStackTrace")
    public List<PhoneNumber> getCustomerPhoneNumbers(final long customerId) throws CustomerIdNotFoundException {
        log.debug("Retrieving all phone numbers for customer id {}", customerId);

        try {
            final CustomerEntity customerEntity = customerRepository.getById(customerId);

            final List<PhoneNumberEntity> phoneNumberEntityList = customerEntity.getPhoneNumbers();
            log.debug("Retrieved phone numbers {} for customer id {} from database", phoneNumberEntityList, customerId);

            final List<PhoneNumber> phoneNumberList = phoneNumberEntityList
                    .stream()
                    .map(phoneNumberEntity -> conversionService.convert(phoneNumberEntity, PhoneNumber.class))
                    .collect(Collectors.toUnmodifiableList());
            log.debug("Converted phone numbers {}", phoneNumberList);

            return phoneNumberList;
        } catch (EntityNotFoundException exc) {
            log.debug("Can't find customer with customer id {}", customerId);

            throw new CustomerIdNotFoundException(customerId);
        }
    }

    public PaginatedResponse<PhoneNumber> getAllPhoneNumbers(final Pageable pageable) {
        log.debug("Retrieving all phone numbers with the pageable {}", pageable);

        final Page<PhoneNumberEntity> page = phoneNumberRepository.findAll(pageable);

        final List<PhoneNumber> phoneNumberList = page.getContent()
                .stream()
                .map(phoneNumberEntity -> conversionService.convert(phoneNumberEntity, PhoneNumber.class))
                .collect(Collectors.toUnmodifiableList());
        log.debug("Converted phone numbers {}", phoneNumberList);

        return new PaginatedResponse<>(phoneNumberList, phoneNumberList.size(), page.getNumber(), page.getTotalPages());
    }

    public void activateCustomerPhoneNumber(final Long customerId, final String phoneNumber) throws CustomerDoesntOwePhoneNumberException {
        final Optional<PhoneNumberEntity> phoneNumberEntityOptional = phoneNumberRepository.findByCustomerIdAndNumber(customerId, phoneNumber);

        if (phoneNumberEntityOptional.isEmpty()) {
            log.debug("Customer with customer id {} doesn't have phone number {}", customerId, phoneNumber);

            throw new CustomerDoesntOwePhoneNumberException(customerId, phoneNumber);
        }

        final PhoneNumberEntity phoneNumberEntity = phoneNumberEntityOptional.get();
        if (!phoneNumberEntity.isActivated()) {
            log.debug("Activating phone number {} for customer id {}", phoneNumber, customerId);
            phoneNumberEntity.setActivated(true);

            phoneNumberRepository.save(phoneNumberEntity);
        }
    }
}
