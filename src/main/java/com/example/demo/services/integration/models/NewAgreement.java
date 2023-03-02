package com.example.demo.services.integration.models;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// Not record because of maven formatter plugin doesn't like records
public class NewAgreement {
  private final String customerPid;
  private final String customerName;
  private final BigDecimal agreementPrice;
}
