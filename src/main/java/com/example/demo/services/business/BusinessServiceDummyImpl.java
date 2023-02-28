package com.example.demo.services.business;

import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.business.models.Customer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BusinessServiceDummyImpl implements BusinessService {
    @Override
    public Customer createCustomer(String customerPid, String name) {
        return new Customer(UUID.randomUUID(),customerPid, name);
    }

    @Override
    public Agreement createAgreement(UUID customerId, BigDecimal agreementPrice) {
        return new Agreement(UUID.randomUUID(), agreementPrice, customerId);
    }
}
