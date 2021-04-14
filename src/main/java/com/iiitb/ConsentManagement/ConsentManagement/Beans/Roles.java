package com.iiitb.ConsentManagement.ConsentManagement.Beans;


import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name="role_table")
public class Roles {

    enum ROLE {
        ROLE_RECEPTIONIST,
        ROLE_NURSE,
        ROLE_DOCTOR,
        ROLE_PHARMACIST,
        ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;     // This is auto generated ID


    @Column(nullable = false,unique = true)
    private String roleID;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLE role;




}
