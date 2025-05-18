package com.flipkart.strategy;

import java.util.List;
import java.util.stream.Collectors;
import com.flipkart.models.Slot;

public class TimeBasedRanking implements SlotRankingStrategy {
    @Override
    public List<Slot> rank(List<Slot> slots) {
        return slots.stream()
                .sorted((a, b) -> a.getStart().compareTo(b.getStart()))
                .collect(Collectors.toList());
    }
}