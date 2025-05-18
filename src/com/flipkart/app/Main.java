package com.flipkart.app;

import com.flipkart.service.AppointmentService;

public class Main {
    public static void main(String[] args) {
        AppointmentService service = AppointmentService.getInstance();

        service.registerDoctor("Curious", "Cardiologist");
        
        // Invalid slot duration - should trigger error
        service.markDoctorAvailability("Curious", "9:30-10:30"); // Invalid
        
        //Valid slots
        service.markDoctorAvailability("Curious", "9:30-10:00", "12:30-13:00", "16:00-16:30");

        service.registerDoctor("Dreadful", "Dermatologist");
        service.markDoctorAvailability("Dreadful", "9:30-10:00", "12:30-13:00", "16:00-16:30");

        // Show available Cardiologist slots before booking
        service.showAvailableSlotsBySpeciality("Cardiologist");
        
        // Book a slot for PatientA with Dr. Curious
        int bookingId = service.bookAppointment("PatientA", "Curious", "12:30");
        service.showAvailableSlotsBySpeciality("Cardiologist");
        
        // Cancel the appointment and restore the slot
        service.cancelAppointment(bookingId);
        service.showAvailableSlotsBySpeciality("Cardiologist");

        // Book again to test waitlist later
        service.bookAppointment("PatientB", "Curious", "12:30");
        
        // Register another doctor with slots
        service.registerDoctor("Daring", "Dermatologist");
        service.markDoctorAvailability("Daring", "11:30-12:00", "14:00-14:30");
        
        // Display Dermatologist slots across doctors
        service.showAvailableSlotsBySpeciality("Dermatologist");
    }
}