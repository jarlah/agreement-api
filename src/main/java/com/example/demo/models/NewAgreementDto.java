package com.example.demo.models;

import com.example.demo.services.integration.models.NewAgreement;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Data;

@Data
// Not record because of maven formatter plugin doesn't like records
public class NewAgreementDto {

  // a norwegian pid is 11 numbers
  // TODO possible to actually validate the pid ?
  @Size(min = 1, max = 11)
  private String customerPid;

  // limit name to 100 characters including space
  // Could be increased, but having a limit avoids a client sending in several megabytes ..
  @Size(min = 1, max = 100)
  private String customerName;

  // avoid horrendously large agreements
  // Also, should there be some domain specific limit to how low the agreements can be?
  // For now, the agreement must be at least 1 NOK (not that this service cares about currency)
  @Min(1)
  @Max(100000000)
  private BigDecimal agreementPrice;

  public NewAgreement toServiceModel() {
    return new NewAgreement(customerPid, customerName, agreementPrice);
  }
}
