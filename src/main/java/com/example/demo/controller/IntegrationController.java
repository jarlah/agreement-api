package com.example.demo.controller;

import com.example.demo.config.ValidationErrors;
import com.example.demo.models.NewAgreementDto;
import com.example.demo.services.business.exceptions.CreateAgreementFailed;
import com.example.demo.services.business.exceptions.CreateCustomerFailed;
import com.example.demo.services.business.exceptions.UpdateAgreementStatusFailed;
import com.example.demo.services.business.models.Agreement;
import com.example.demo.services.integration.IntegrationService;
import com.example.demo.services.letter.exceptions.LetterFailedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Integration controller")
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
  @Operation(summary = "New agreement")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = ValidationErrors.class))),
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = Agreement.class)))
      })
  public Response createNewAgreement(@Valid NewAgreementDto newAgreementDto)
      throws LetterFailedException, CreateAgreementFailed, CreateCustomerFailed,
          UpdateAgreementStatusFailed {
    var agreement = this.integrationService.createAgreement(newAgreementDto.toServiceModel());
    logger.info("Successfully created agreement with id [%s]".formatted(agreement.id()));
    return Response.ok(agreement).build();
  }
}
