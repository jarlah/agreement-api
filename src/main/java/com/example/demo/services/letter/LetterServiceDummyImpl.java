package com.example.demo.services.letter;

import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.business.models.Customer;
import org.springframework.stereotype.Service;

@Service
public class LetterServiceDummyImpl implements LetterService {
  @Override
  public void sendAgreementLetterToCustomer(Agreement agreement, Customer customer) {}
}
