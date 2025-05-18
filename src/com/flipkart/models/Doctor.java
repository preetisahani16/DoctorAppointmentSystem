package com.flipkart.models;

import java.util.*;

public class Doctor {
    private String name;
    private String speciality;
    private Map<String, Slot> slots = new HashMap<>();
    private int appointmentCount = 0;

    public Doctor(String name, String speciality) {
        this.name = name;
        this.speciality = speciality;
    }

    public String getName() { return name; }
    public String getSpeciality() { return speciality; }
    public Collection<Slot> getSlots() { return slots.values(); }
    public void addSlot(Slot slot) { slots.put(slot.getStart(), slot); }
    public Slot getSlot(String time) { return slots.get(time); }
    public void incrementAppointments() { appointmentCount++; }
    public void decrementAppointments() { appointmentCount--; }
    public int getAppointmentCount() { return appointmentCount; }
}