package com.example.demo.services.letter;

import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.business.models.Customer;
import com.example.demo.services.letter.exceptions.LetterFailedException;
import com.example.demo.services.letter.models.LetterStatus;

/** This service might call out to an external api */
public interface LetterService {
  LetterStatus sendAgreementLetterToCustomer(Agreement agreement, Customer customer)
      throws LetterFailedException;
}
