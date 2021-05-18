package com.iiitb.consentmanagement2.nurse.Beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;


@Component
public class VitalParams {


    private String vitalParamsID;

    private String patientName;

    private String patientID;

    private String age;

    private String weight;

    private String bloodPressure;

    private String temperature;


    private String height;


    private String heartRate;


    private String spO2;


    private String resRate;


    private String operation;


    private String purpose;


    private String loginEmail;

    public VitalParams(){}

    public VitalParams(String vitalParamsID, String patientName, String patientID, String age, String height, String weight, String heartRate, String bloodPressure, String spO2, String resRate, String temperature, String operation, String purpose, String loginEmail) {
        this.vitalParamsID = vitalParamsID;
        this.patientName = patientName;
        this.patientID = patientID;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.spO2 = spO2;
        this.resRate = resRate;
        this.temperature = temperature;
        this.operation = operation;
        this.purpose = purpose;
        this.loginEmail = loginEmail;
    }

    public String getVitalParamsID() {
        return vitalParamsID;
    }

    public void setVitalParamsID(String vitalParamsID) {
        this.vitalParamsID = vitalParamsID;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getSpO2() {
        return spO2;
    }

    public void setSpO2(String spO2) {
        this.spO2 = spO2;
    }

    public String getResRate() {
        return resRate;
    }

    public void setResRate(String resRate) {
        this.resRate = resRate;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }
}
