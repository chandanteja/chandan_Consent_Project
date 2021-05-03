package com.iiitb.consentmanagement1.receptionist.Beans;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 *  This class is used for receviving actor data from front end or access data from corresponding Repository
 */

/*enum Permission {
    CREATE,
    UPDATE,
    VIEW,
    DELETE,
    NONE        // To specify that no permissions are given to the role
}

/*class Permission
{
    public static final int CREATE = 0;
    public static final int UPDATE = 1;
    public static final int VIEW = 2;
    public static final int DELETE = 3;
    public static final int NONE = 4;

}
*/


    @Entity
    @Component
    @Table(name="actor_table")

    public class Actor {


        @Id
        private String actorID;

        @Column(nullable = false)
        private String fullName;

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private ROLE role;

        @Column(nullable = false,unique = true)
        private String emailID;

        @Column(nullable = false,unique = true)
        private String contact;

        // Start and end times are the duty hours.
        @Column
        private LocalTime startTime;

        @Column
        private LocalTime endTime;

        // We maintain 4 times. Start and end times define the working hours and  login and logout  are the actual working hours

        @Column
        private LocalTime loginTime;

        @Column
        private LocalTime logoutTime;


        @Column
        private Integer status;

        @Column
        private String password;

        // Handle the OverTime case later.

        /*  @Column(nullable = false)
        private List<Permission> allowedPerm;

       */


        public Actor() {
        }

        public Actor(String actorID, String fullName, ROLE role, String emailID, String contact, LocalTime startTime, LocalTime endTime, LocalTime loginTime, LocalTime logoutTime, Integer status, String password) {
            this.actorID = actorID;
            this.fullName = fullName;
            this.role = role;
            this.emailID = emailID;
            this.contact = contact;
            this.startTime = startTime;
            this.endTime = endTime;
            this.loginTime = loginTime;
            this.logoutTime = logoutTime;
            this.status = status;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getActorID() {
            return actorID;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public void setActorID(String actorID) {
            this.actorID = actorID;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public ROLE getRole() {
            return role;
        }

        public void setRole(ROLE role) {
            this.role = role;
        }

        public String getEmailID() {
            return emailID;
        }

        public void setEmailID(String emailID) {
            this.emailID = emailID;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public LocalTime getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(LocalTime loginTime) {
            this.loginTime = loginTime;
        }

        public LocalTime getLogoutTime() {
            return logoutTime;
        }

        public void setLogoutTime(LocalTime logoutTime) {
            this.logoutTime = logoutTime;
        }
    }
