package com.flipkart.factory;

import com.flipkart.models.Doctor;

public class DoctorFactory {
    public static Doctor createDoctor(String name, String speciality) {
        return new Doctor(name, speciality);
    }
}