package com.example.demo.services.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
// Not record because of maven formatter plugin doesn't like records
public class Customer {
  private final UUID id;
  private final String pid;
  private final String name;
}
