package com.iiitb.consentmanagement1.receptionist.Beans;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Component
public class DemographicDetails {



    private String firstName;
    private String lastName;
    private String email;       // This is the email of patient
    private String phoneNumber;
    private String address;
    private Integer age;
    private String bloodGroup;
    private String gender;
    private String purpose;

    private Integer otp; //no column needs to be created for this field
    private String consent;
    private String operation;      // This variable is to track what operation is being performed on the registration form.
    // this is the email of either receptionist/nurse etc
    private String loginEmail;      // This variable is just to keep track of what is the email of the entity which is performing the add patient operation by accessing registration form.


    public DemographicDetails() {
    }

    public DemographicDetails( String firstName, String lastName, @Email String email, String phoneNumber, String address, Integer age, String bloodGroup, String gender, String purpose, Integer otp, String consent, String operation, String loginEmail) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
        this.purpose = purpose;
        this.otp = otp;
        this.consent = consent;
        this.operation = operation;
        this.loginEmail = loginEmail;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getConsent() {
        return consent;
    }

    public void setConsent(String consent) {
        this.consent = consent;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
