package com.example.demo.config;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Provider
public class BeanValidationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(ValidationErrors.fromViolationException(exception))
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}

@Data
// Not record because of maven formatter plugin doesn't like records
class ValidationError {
  private final String field;
  private final String message;

  static ValidationError fromViolation(ConstraintViolation<?> violation) {
    return new ValidationError(violation.getPropertyPath().toString(), violation.getMessage());
  }
}

@Data
// Not record because of maven formatter plugin doesn't like records
class ValidationErrors {
  private final List<ValidationError> errors;

  static ValidationErrors fromViolationException(ConstraintViolationException exception) {
    return new ValidationErrors(
        exception.getConstraintViolations().stream().map(ValidationError::fromViolation).toList());
  }
}
