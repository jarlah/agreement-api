package com.example.demo.services.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// Not record because of maven formatter plugin doesn't like records
public class NewCustomer {
  private String name;
  private String pid;
}
