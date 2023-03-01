package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationControllerTests {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void createAgreementWithValidPayload() {
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "11111111111");
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 1000);
    ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/agreement", obj, String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    // TODO find a way to assert json without resorting to POJOs
  }

  @Test
  public void createAgreementWithBadCustomerPid() {
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "111111111111111111");
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 1000);
    ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/agreement", obj, String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            "{\"errors\":[{\"field\":\"createAgreement.newAgreementDto.customerPid\","
                + "\"message\":\"size must be between 1 and 11\"}]}");
  }

  @Test
  public void createAgreementWithBadAgreementPrice() {
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "01019912345");
    obj.put("customerName", "Donald Duck");
    obj.put("agreementPrice", 0);
    ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/agreement", obj, String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            "{\"errors\":[{\"field\":\"createAgreement.newAgreementDto.agreementPrice\","
                + "\"message\":\"must be greater than or equal to 1\"}]}");
  }

  @Test
  public void createAgreementWithBadCustomerName() {
    Map<String, Object> obj = new HashMap<>();
    obj.put("customerPid", "01019912345");
    obj.put("customerName", "");
    obj.put("agreementPrice", 1000);
    ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/agreement", obj, String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(entity.getBody())
        .isEqualTo(
            "{\"errors\":[{\"field\":\"createAgreement.newAgreementDto.customerName\","
                + "\"message\":\"size must be between 1 and 100\"}]}");
  }
}
