package com.example.demo.services.integration;

import com.example.demo.services.business.BusinessService;
import com.example.demo.services.business.exceptions.CreateAgreementFailed;
import com.example.demo.services.business.exceptions.CreateCustomerFailed;
import com.example.demo.services.business.exceptions.UpdateAgreementStatusFailed;
import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.business.models.AgreementStatus;
import com.example.demo.services.integration.models.NewAgreement;
import com.example.demo.services.letter.LetterService;
import com.example.demo.services.letter.exceptions.LetterFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationServiceImpl implements IntegrationService {

  private final BusinessService businessService;
  private final LetterService letterService;

  @Autowired
  public IntegrationServiceImpl(BusinessService businessService, LetterService letterService) {
    this.businessService = businessService;
    this.letterService = letterService;
  }

  @Override
  public Agreement createAgreement(NewAgreement newAgreement)
      throws LetterFailedException, CreateCustomerFailed, CreateAgreementFailed,
          UpdateAgreementStatusFailed {
    var customer =
        businessService.createCustomer(newAgreement.customerPid(), newAgreement.customerName());
    var agreement = businessService.createAgreement(customer.id(), newAgreement.agreementPrice());
    // Can atm only be successful
    var status = letterService.sendAgreementLetterToCustomer(agreement, customer);
    return businessService.updateAgreementStatus(agreement, AgreementStatus.AGREEMENT_SENT);
  }
}
