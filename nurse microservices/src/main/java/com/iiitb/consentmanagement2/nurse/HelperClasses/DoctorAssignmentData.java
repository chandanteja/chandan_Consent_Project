package com.iiitb.consentmanagement2.nurse.HelperClasses;

public class DoctorAssignmentData {

    private String patientID;
    private String department;
    private String loginEmail;

    public DoctorAssignmentData(String patientID, String department, String loginEmail) {
        this.patientID = patientID;
        this.department = department;
        this.loginEmail = loginEmail;
    }

    public DoctorAssignmentData() {
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }
}
