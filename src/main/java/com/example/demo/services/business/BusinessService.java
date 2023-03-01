package com.example.demo.services.business;

import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.business.models.Customer;

import java.math.BigDecimal;
import java.util.UUID;

public interface BusinessService {

  Customer createCustomer(String customerPid, String name);

  Agreement createAgreement(UUID customerId, BigDecimal agreementPrice);
}
