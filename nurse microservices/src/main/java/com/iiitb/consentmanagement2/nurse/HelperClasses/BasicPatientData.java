package com.iiitb.consentmanagement2.nurse.HelperClasses;

import org.springframework.stereotype.Component;

@Component
public class BasicPatientData {


    private String patientName;
    private Integer age;
    private String bloodGroup;
    private String patientID;

    public BasicPatientData(String patientName, Integer age, String bloodGroup, String patientID) {
        this.patientName = patientName;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.patientID = patientID;
    }

    public BasicPatientData() {
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
