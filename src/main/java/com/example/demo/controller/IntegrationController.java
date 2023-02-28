package com.example.demo.controller;

import com.example.demo.models.NewAgreementDto;
import com.example.demo.services.integration.IntegrationService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/api")
public class IntegrationController {

    private final IntegrationService integrationService;

    @Autowired
    public IntegrationController(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @POST
    @Path("/agreement")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAgreement(@Valid NewAgreementDto newAgreementDto) {
        var agreement = this.integrationService.createAgreement(newAgreementDto.toServiceModel());
        return Response.ok(agreement).build();
    }
}
