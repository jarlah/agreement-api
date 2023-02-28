package com.example.demo.services.letter;

import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.business.models.Customer;
import com.example.demo.services.letter.models.LetterStatus;
import org.springframework.stereotype.Service;

@Service
public class LetterServiceDummyImpl implements LetterService {
    @Override
    public LetterStatus sendAgreementLetterToCustomer(Agreement agreement, Customer customer) {
        // TODO logic
        return LetterStatus.SENT_OK;
    }
}
