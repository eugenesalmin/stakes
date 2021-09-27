package com.example.demo.model.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class StakeRequest {
    private final BigDecimal stake;
    private final UUID betOfferId;
}
