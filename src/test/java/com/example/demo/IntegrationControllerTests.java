package com.example.demo;

import com.example.demo.services.business.models.Agreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationControllerTests {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void createAgreementWithValidPayload() {
    // Given:
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "11111111111");
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 1000);

    // When:
    ResponseEntity<Agreement> entity = this.restTemplate.postForEntity("/api/agreement", obj, Agreement.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getBody().getAgreementPrice()).isEqualTo(BigDecimal.valueOf(1000));
    assertThat(entity.getBody().getId()).isNotNull();
    assertThat(entity.getBody().getCustomerId()).isNotNull();
    assertThat(entity.getBody().getStatus().name()).isEqualTo("AGREEMENT_SENT");
    // TODO find a way to assert json without resorting to referencing Agreement class
  }

  @Test
  public void createAgreementWithBadCustomerPid() {
    // Given:
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "111111111111111111");
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 1000);

    // When:
    ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/agreement", obj, String.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            "{\"errors\":[{\"field\":\"createAgreement.newAgreementDto.customerPid\","
                + "\"message\":\"size must be between 1 and 11\"}]}");
  }

  @Test
  public void createAgreementWithBadAgreementPrice() {
    // Given:
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "01019912345");
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 0);

    // When:
    ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/agreement", obj, String.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            "{\"errors\":[{\"field\":\"createAgreement.newAgreementDto.agreementPrice\","
                + "\"message\":\"must be greater than or equal to 1\"}]}");
  }

  @Test
  public void createAgreementWithBadCustomerName() {
    // Given:
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "01019912345");
    obj.put("customerName", "");
    obj.put("agreementPrice", 1000);

    // When:
    ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/agreement", obj, String.class);

    // Then:
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            "{\"errors\":[{\"field\":\"createAgreement.newAgreementDto.customerName\","
                + "\"message\":\"size must be between 1 and 100\"}]}");
  }
}
