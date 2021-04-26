package com.iiitb.ConsentManagement.ConsentManagement.Beans;


import org.springframework.stereotype.Component;

/**
 * This is not a DB Model/Bean class. This class is used for creating an object of patient which can be used to pass around to NURSE,DOCTOR etc.
 * This class is used to be passed around for accessing patient electronically in the hospital.
 */
@Component
public class Patient {

    private String patientFirstName;   // firstname
    private String patientLastName;
    private String patientAge;
    private String bloodGroup;
    private String patientGender;

    public Patient() {
    }

    public Patient(String patientFirstName, String patientLastName, String patientAge, String bloodGroup, String patientGender) {
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientAge = patientAge;
        this.bloodGroup = bloodGroup;
        this.patientGender = patientGender;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }
}
