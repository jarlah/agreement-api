package com.example.demo.models;

import com.example.demo.services.integration.models.NewAgreement;
import com.example.demo.util.validation.ValidCustomerPid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record NewAgreementDto(
    @ValidCustomerPid String customerPid,
    @Size(min = 1, max = 100) String customerName,
    // work in progress fields for more customer data
    String customerAddress,
    String customerEmail,
    String customerPhoneNumber,
    // avoid horrendously large agreements
    @Min(1) @Max(100000000) BigDecimal agreementPrice,
    @NotNull LocalDate agreementDate) {
  public NewAgreement toServiceModel() {
    return new NewAgreement(customerPid, customerName, agreementPrice, agreementDate);
  }
}
