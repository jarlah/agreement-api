package com.example.demo.services.integration;

import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.integration.models.NewAgreement;

public interface IntegrationService {

    Agreement createAgreement(NewAgreement newAgreementDto);
}
