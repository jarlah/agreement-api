package com.example.demo.services.business.models;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// Not record because of maven formatter plugin doesn't like records
public class NewAgreement {
  private final BigDecimal amount;
  private final UUID customerId;
}
