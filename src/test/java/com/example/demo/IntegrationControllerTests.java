package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.services.business.models.Agreement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import no.bekk.bekkopen.person.FodselsnummerCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationControllerTests {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void createAgreementWithValidPayload() {
    // Given:
    var pid = FodselsnummerCalculator.getFodselsnummerForDate(new Date()).toString();
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", pid);
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 1000);
    obj.put("agreementDate", "1999-01-24");

    // When:
    ResponseEntity<Agreement> entity =
        this.restTemplate.postForEntity("/api/agreement", obj, Agreement.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getBody().agreementPrice()).isEqualTo(BigDecimal.valueOf(1000));
    assertThat(entity.getBody().id()).isNotNull();
    assertThat(entity.getBody().customerId()).isNotNull();
    assertThat(entity.getBody().status().name()).isEqualTo("AGREEMENT_SENT");
    assertThat(entity.getBody().agreementDate()).isEqualTo(LocalDate.of(1999, 1, 24));
    // TODO find a way to assert json without resorting to referencing Agreement class
  }

  @Test
  public void createAgreementWithBadCustomerPid() {
    // Given:
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "111111111111111111");
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 1000);
    obj.put("agreementDate", "1999-01-24");

    // When:
    ResponseEntity<String> entity =
        this.restTemplate.postForEntity("/api/agreement", obj, String.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            """
                        {"errors":[{"field":"createNewAgreement.newAgreementDto.customerPid","message":"Customer pid is not valid"}]}""");
  }

  @Test
  public void createAgreementWithBadAgreementPrice() {
    // Given:
    var pid = FodselsnummerCalculator.getFodselsnummerForDate(new Date()).toString();
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", pid);
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 0);
    obj.put("agreementDate", "1999-01-24");

    // When:
    ResponseEntity<String> entity =
        this.restTemplate.postForEntity("/api/agreement", obj, String.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            """
                        {"errors":[{"field":"createNewAgreement.newAgreementDto.agreementPrice","message":"must be greater than or equal to 1"}]}""");
  }

  @Test
  public void createAgreementWithBadCustomerName() {
    // Given:
    var pid = FodselsnummerCalculator.getFodselsnummerForDate(new Date()).toString();
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", pid);
    obj.put("customerName", "");
    obj.put("agreementPrice", 1000);
    obj.put("agreementDate", "1999-01-24");

    // When:
    ResponseEntity<String> entity =
        this.restTemplate.postForEntity("/api/agreement", obj, String.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            """
                     {"errors":[{"field":"createNewAgreement.newAgreementDto.customerName","message":"size must be between 1 and 100"}]}""");
  }

  @Test
  public void createAgreementWithMissingAgreementDate() {
    // Given:
    var pid = FodselsnummerCalculator.getFodselsnummerForDate(new Date()).toString();
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", pid);
    obj.put("customerName", "Test customer");
    obj.put("agreementPrice", 1000);

    // When:
    ResponseEntity<String> entity =
        this.restTemplate.postForEntity("/api/agreement", obj, String.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            """
                             {"errors":[{"field":"createNewAgreement.newAgreementDto.agreementDate","message":"must not be null"}]}""");
  }
}
