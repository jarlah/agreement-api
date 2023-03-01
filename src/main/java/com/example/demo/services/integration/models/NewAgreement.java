package com.example.demo.services.integration.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
// Not record because of maven formatter plugin doesn't like records
public class NewAgreement {
  private String customerPid;
  private String customerName;
  private BigDecimal agreementPrice;
}
