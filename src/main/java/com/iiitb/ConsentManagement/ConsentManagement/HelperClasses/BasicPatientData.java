package com.iiitb.ConsentManagement.ConsentManagement.HelperClasses;


import org.springframework.stereotype.Component;

@Component
public class BasicPatientData {

    private String patientID;
    private String patientName;
    private String bloodGroup;
    private Integer age;

    public BasicPatientData(String patientID, String patientName, String bloodGroup, Integer age) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.age = age;
    }

    public BasicPatientData() {
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
