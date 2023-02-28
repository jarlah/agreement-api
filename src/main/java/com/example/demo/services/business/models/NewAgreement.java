package com.example.demo.services.business.models;

import java.math.BigDecimal;
import java.util.UUID;

public record NewAgreement(BigDecimal amount, UUID customerId) {}
