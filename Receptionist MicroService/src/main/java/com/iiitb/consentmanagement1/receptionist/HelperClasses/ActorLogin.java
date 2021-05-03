package com.iiitb.consentmanagement1.receptionist.HelperClasses;

import org.springframework.stereotype.Component;

/**
 * This is not a model class> This class is used to collect the information in form of Object from front end for Login Details
 *  This  is a helper class.
 */

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
