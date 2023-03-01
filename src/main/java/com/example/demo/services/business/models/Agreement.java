package com.example.demo.services.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
// Not record because of maven formatter plugin doesn't like records
public class Agreement {
  private final UUID id;
  private final AgreementStatus status;
  private final BigDecimal agreementPrice;
  private final UUID customerId;
}
