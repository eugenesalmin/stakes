package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.StakeInfo;
import com.example.demo.model.request.StakeRequest;

@SpringBootTest
class StakeServiceTest {
    @Autowired
    private StakeService underTest;

    @Test
    public void testStakeAccepted() {
        UUID customerId = UUID.randomUUID();
        UUID betOfferId = UUID.randomUUID();
        underTest.acceptStake(customerId, new StakeRequest(BigDecimal.ONE, betOfferId));
    }

    @Test
    public void testStakeAcceptedAndReturnedSorted() {
        UUID customerId = UUID.randomUUID();
        UUID betOfferId = UUID.randomUUID();
        List<Integer> randomInts = IntStream.generate(ThreadLocalRandom.current()::nextInt).limit(21).boxed()
                .collect(Collectors.toList());

        randomInts.forEach(i -> underTest.acceptStake(customerId, new StakeRequest(BigDecimal.valueOf(i), betOfferId)));

        Collections.sort(randomInts);
        List<Integer> sortedLimitedSubset = randomInts.subList(0, 20);
        Collections.reverse(randomInts);

        List<StakeInfo> maxStakes = underTest.getMaxStakes(betOfferId);
        assertEquals(20, maxStakes.size());
        assertTrue(maxStakes.stream().allMatch(i -> i.getCustomerId().equals(customerId)));
        assertEquals(sortedLimitedSubset, maxStakes.stream().map(StakeInfo::getStake).map(BigDecimal::intValue)
                .collect(Collectors.toList()));
    }
}