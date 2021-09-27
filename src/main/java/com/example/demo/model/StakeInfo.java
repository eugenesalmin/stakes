package com.example.demo.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StakeInfo implements Comparable<StakeInfo> {
    private final UUID customerId;
    private final BigDecimal stake;

    @Override
    public int compareTo(final StakeInfo other) {
        return other.getStake().compareTo(this.stake);
    }
}
