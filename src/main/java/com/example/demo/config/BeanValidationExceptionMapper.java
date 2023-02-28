package com.example.demo.config;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        var errors = exception.getConstraintViolations()
                .stream()
                .map(violation ->
                        new ValidationError(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .toList();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ValidationErrors(errors))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

record ValidationError(String field, String message) {}

record ValidationErrors(List<ValidationError> errors) {}
