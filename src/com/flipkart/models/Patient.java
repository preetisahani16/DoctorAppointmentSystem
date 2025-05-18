package com.flipkart.models;

import java.util.HashSet;
import java.util.Set;

public class Patient {
    private String name;
    private Set<String> bookedSlots = new HashSet<>();

    public Patient(String name) {
        this.name = name;
    }

    public boolean isSlotFree(String slot) {
        return !bookedSlots.contains(slot);
    }

    public void bookSlot(String slot) {
        bookedSlots.add(slot);
    }

    public void cancelSlot(String slot) {
        bookedSlots.remove(slot);
    }

    public String getName() { return name; }
}