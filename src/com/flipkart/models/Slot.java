package com.flipkart.models;

import java.util.LinkedList;
import java.util.Queue;

public class Slot {
    private String start;
    private String end;
    private boolean isBooked = false;
    private String bookedBy;
    private Queue<String> waitlist = new LinkedList<>();

    public Slot(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() { return start; }
    public String getEnd() { return end; }
    public boolean isBooked() { return isBooked; }

    public String book(String patient) {
        if (!isBooked) {
            isBooked = true;
            bookedBy = patient;
            return "Booked. Booking id: " + hashCode();
        } else {
            waitlist.add(patient);
            return "Slot full. Added to waitlist.";
        }
    }

    public String cancel() {
        if (!waitlist.isEmpty()) {
            bookedBy = waitlist.poll();
            return "Reassigned to: " + bookedBy;
        } else {
            isBooked = false;
            bookedBy = null;
            return "Booking Cancelled";
        }
    }

    public String getBookedBy() { return bookedBy; }
}