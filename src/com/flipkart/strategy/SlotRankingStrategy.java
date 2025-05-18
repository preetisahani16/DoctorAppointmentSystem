package com.flipkart.strategy;

import java.util.List;
import com.flipkart.models.Slot;

public interface SlotRankingStrategy {
    List<Slot> rank(List<Slot> slots);
}