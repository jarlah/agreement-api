package com.example.demo.services.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
// Not record because of maven formatter plugin doesn't like records
public class Agreement {
  private UUID id;
  private BigDecimal agreementPrice;
  private UUID customerId;
}
