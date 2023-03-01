package com.example.demo.services.integration;

import com.example.demo.services.business.BusinessService;
import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.integration.models.NewAgreement;
import com.example.demo.services.letter.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationServiceImpl implements IntegrationService {

  private final BusinessService businessService;
  private final LetterService letterService;

  @Autowired
  public IntegrationServiceImpl(BusinessService businessService, LetterService letterService) {
    this.businessService =     businessService;
    this.letterService = letterService;
  }

  @Override
  public Agreement createAgreement(NewAgreement newAgreement) {
    var customer = businessService.createCustomer(newAgreement.getCustomerPid(), newAgreement.getCustomerName());
    var agreement = businessService.createAgreement(customer.getId(), newAgreement.getAgreementPrice());
    letterService.sendAgreementLetterToCustomer(agreement, customer);
    return agreement;
  }
}
