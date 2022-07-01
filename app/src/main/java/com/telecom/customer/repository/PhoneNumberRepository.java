package com.telecom.customer.repository;

import com.telecom.customer.repository.entity.CustomerEntity;
import com.telecom.customer.repository.entity.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {
    List<PhoneNumberEntity> findByCustomer(CustomerEntity customerEntity);

    Optional<PhoneNumberEntity> findByCustomerIdAndNumber(Long customerId, String phoneNumber);
}
