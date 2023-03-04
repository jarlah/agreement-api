package com.example.demo.controller;

import com.example.demo.models.NewAgreementDto;
import com.example.demo.services.business.exceptions.CreateAgreementFailed;
import com.example.demo.services.business.exceptions.CreateCustomerFailed;
import com.example.demo.services.business.exceptions.UpdateAgreementStatusFailed;
import com.example.demo.services.integration.IntegrationService;
import com.example.demo.services.letter.exceptions.LetterFailedException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/api")
public class IntegrationController {
  private final Logger logger = LoggerFactory.getLogger(IntegrationController.class);

  private final IntegrationService integrationService;

  @Autowired
  public IntegrationController(IntegrationService integrationService) {
    this.integrationService = integrationService;
  }

  @POST
  @Path("/agreement")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createAgreement(@Valid NewAgreementDto newAgreementDto)
      throws LetterFailedException, CreateAgreementFailed, CreateCustomerFailed,
          UpdateAgreementStatusFailed {
    var agreement = this.integrationService.createAgreement(newAgreementDto.toServiceModel());
    logger.info("Successfully created agreement with id [%s]".formatted(agreement.id()));
    return Response.ok(agreement).build();
  }
}
