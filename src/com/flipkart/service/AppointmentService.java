package com.flipkart.service;

import java.util.*;
import com.flipkart.models.*;
import com.flipkart.strategy.*;
import com.flipkart.factory.DoctorFactory;

public class AppointmentService {
    private static AppointmentService instance = new AppointmentService();
    private Map<String, Doctor> doctors = new HashMap<>();
    private Map<String, Patient> patients = new HashMap<>();
    private SlotRankingStrategy rankingStrategy = new TimeBasedRanking();
    private Map<Integer, String> bookingMap = new HashMap<>();

    private AppointmentService() {}
    public static AppointmentService getInstance() {
        return instance;
    }

    public void registerDoctor(String name, String speciality) {
        doctors.put(name, DoctorFactory.createDoctor(name, speciality));
        System.out.println("Welcome Dr. " + name + " !!");
    }

    public void markDoctorAvailability(String doctorName, String... times) {
        Doctor doc = doctors.get(doctorName);
        for (String time : times) {
            String[] t = time.split("-");
            if (t.length == 2) {
            	if (!isThirtyMinSlot(t[0], t[1])) {
            	    System.out.println("Sorry Dr. " + doctorName + " slots are 30 mins only");
            	    continue;
            	}
            	Slot s = new Slot(t[0], t[1]);
            	doc.addSlot(s);
            } else {
                System.out.println("Invalid slot: " + time);
            }
        }
        System.out.println("Done Doc!");
    }
    
    private boolean isThirtyMinSlot(String start, String end) {
        try {
            String[] s = start.split(":");
            String[] e = end.split(":");
            int startMin = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
            int endMin = Integer.parseInt(e[0]) * 60 + Integer.parseInt(e[1]);
            return (endMin - startMin) == 30;
        } catch (Exception ex) {
            return false;
        }
    }


    public void showAvailableSlotsBySpeciality(String speciality) {
        for (Doctor doc : doctors.values()) {
            if (doc.getSpeciality().equalsIgnoreCase(speciality)) {
                List<Slot> available = new ArrayList<>();
                for (Slot s : doc.getSlots()) {
                    if (!s.isBooked()) available.add(s);
                }
                rankingStrategy.rank(available)
                        .forEach(slot -> System.out.println("Dr." + doc.getName() + ": (" + slot.getStart() + "-" + slot.getEnd() + ")"));
            }
        }
    }

    public int bookAppointment(String patientName, String doctorName, String time) {
        Patient pat = patients.computeIfAbsent(patientName, Patient::new);
        Doctor doc = doctors.get(doctorName);
        Slot slot = doc.getSlot(time);
        if (slot != null && pat.isSlotFree(time)) {
            String result = slot.book(patientName);
            pat.bookSlot(time);
            doc.incrementAppointments();
            System.out.println(result);
            int bookingId = slot.hashCode();
            bookingMap.put(bookingId, patientName + ":" + doctorName + ":" + time);
            return bookingId;
        }
        return -1;
    }

    public void cancelAppointment(int bookingId) {
        String details = bookingMap.get(bookingId);
        if (details == null) {
            System.out.println("Invalid booking ID");
            return;
        }
        String[] parts = details.split(":");
        String patient = parts[0], doctor = parts[1], time = parts[2];
        Doctor doc = doctors.get(doctor);
        Slot slot = doc.getSlot(time);
        Patient pat = patients.get(patient);
        if (slot != null && pat != null) {
            System.out.println(slot.cancel());
            pat.cancelSlot(time);
            doc.decrementAppointments();
        }
    }
}