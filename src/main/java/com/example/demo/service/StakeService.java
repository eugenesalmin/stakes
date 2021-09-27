package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.model.StakeInfo;
import com.example.demo.model.request.StakeRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StakeService {
    private final static Map<UUID, Set<StakeInfo>> BET_OFFER_TO_STAKES = new ConcurrentHashMap<>();
    private static final int QUERY_LIMIT = 20;

    public void acceptStake(final UUID customerId, final StakeRequest stakeRequest) {
        BET_OFFER_TO_STAKES.merge(stakeRequest.getBetOfferId(),
                new TreeSet<>(List.of(new StakeInfo(customerId, stakeRequest.getStake()))),
                (existingStakes, newStakes) -> {
                    existingStakes.addAll(newStakes);
                    return existingStakes;
                });
    }

    public List<StakeInfo> getMaxStakes(final UUID betOfferId) {
        return BET_OFFER_TO_STAKES.getOrDefault(betOfferId, Set.of()).stream().limit(QUERY_LIMIT)
                .collect(Collectors.toList());
    }
}
