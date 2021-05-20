package com.iiitb.consentmanagement.doctormicroservice.HelperClasses;

import org.springframework.stereotype.Component;

@Component
public class ActorLogin {
    private String email;
    private String password;

    public ActorLogin() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ActorLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
