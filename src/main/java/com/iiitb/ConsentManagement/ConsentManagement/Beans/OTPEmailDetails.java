package com.iiitb.ConsentManagement.ConsentManagement.Beans;

public class OTPEmailDetails {
    Integer otp;
    String email;

    public OTPEmailDetails(Integer otp, String email) {
        this.otp = otp;
        this.email = email;
    }

    public OTPEmailDetails() {
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
