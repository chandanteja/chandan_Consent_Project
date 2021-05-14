package com.iiitb.consentmanagement2.nurse.Beans;

import org.springframework.stereotype.Component;

@Component
public class VitalDetails {

    //Need  to add patient id as well
    private String Age;
    private String Height;
    private String Weight;
    private String HeartRate;
    private String BloodPressure;
    private String SpO2;
    private String ResRate;
    private String Temperature;

    private String operation;
    private String purpose;
    private String loginEmail;

    public VitalDetails(){}

    public VitalDetails(String age, String height, String weight,String purpose, String heartRate, String bloodPressure, String spO2, String resRate, String temperature, String operation, String loginEmail) {
        Age = age;
        Height = height;
        Weight = weight;
        this.purpose = purpose;
        HeartRate = heartRate;
        BloodPressure = bloodPressure;
        SpO2 = spO2;
        ResRate = resRate;
        Temperature = temperature;
        this.operation = operation;
        this.loginEmail = loginEmail;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getHeartRate() {
        return HeartRate;
    }

    public void setHeartRate(String heartRate) {
        HeartRate = heartRate;
    }

    public String getBloodPressure() {
        return BloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        BloodPressure = bloodPressure;
    }

    public String getSpO2() {
        return SpO2;
    }

    public void setSpO2(String spO2) {
        SpO2 = spO2;
    }

    public String getResRate() {
        return ResRate;
    }

    public void setResRate(String resRate) {
        ResRate = resRate;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
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
