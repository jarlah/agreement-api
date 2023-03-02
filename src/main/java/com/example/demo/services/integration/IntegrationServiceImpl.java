package com.example.demo.services.integration;

import com.example.demo.services.business.BusinessService;
import com.example.demo.services.business.exceptions.CreateAgreementFailed;
import com.example.demo.services.business.exceptions.CreateCustomerFailed;
import com.example.demo.services.business.exceptions.UpdateAgreementStatusFailed;
import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.business.models.AgreementStatus;
import com.example.demo.services.integration.exceptions.SendAgreementLetterFailed;
import com.example.demo.services.integration.models.NewAgreement;
import com.example.demo.services.letter.LetterService;
import com.example.demo.services.letter.exceptions.LetterFailedException;
import com.example.demo.services.letter.models.LetterStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
      throws LetterFailedException, CreateCustomerFailed, CreateAgreementFailed, UpdateAgreementStatusFailed,
          SendAgreementLetterFailed {
    var customer = businessService.createCustomer(newAgreement.getCustomerPid(), newAgreement.getCustomerName());
    var agreement = businessService.createAgreement(customer.getId(), newAgreement.getAgreementPrice());
    var status = letterService.sendAgreementLetterToCustomer(agreement, customer);
    if (status == LetterStatus.SENT_OK) {
      agreement = businessService.updateAgreementStatus(agreement, AgreementStatus.AGREEMENT_SENT);
    } else {
      throw new SendAgreementLetterFailed("Status for letter is: %s".formatted(status));
    }
    return agreement;
  }
}
