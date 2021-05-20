package com.iiitb.consentmanagement.doctormicroservice.HelperClasses;


import org.springframework.stereotype.Component;


@Component

public class DoctorFormDetails {


    private String doctorFormID;


    private String patientName;


    private String patientID;


    private String bloodPressure;


    private String temperature;


    private String operation;

    private String loginEmail;


    private String purpose;

    public DoctorFormDetails(String doctorFormID, String patientName, String patientID, String bloodPressure, String temperature, String operation, String loginEmail, String purpose) {
        this.doctorFormID = doctorFormID;
        this.patientName = patientName;
        this.patientID = patientID;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.operation = operation;
        this.loginEmail = loginEmail;
        this.purpose = purpose;
    }

    public DoctorFormDetails() {
    }

    public String getDoctorFormID() {
        return doctorFormID;
    }

    public void setDoctorFormID(String doctorFormID) {
        this.doctorFormID = doctorFormID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
