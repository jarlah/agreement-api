package com.example.demo.services.integration.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NewAgreement(
    String customerPid, String customerName, BigDecimal agreementPrice, LocalDate agreementDate) {}
