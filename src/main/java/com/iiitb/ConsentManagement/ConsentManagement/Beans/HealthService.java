package com.iiitb.ConsentManagement.ConsentManagement.Beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name="health_service_table")
public class HealthService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID; // auto generated ID

    @Column(nullable = false,unique = true)
    private String healthServiceID;

    @Column(nullable = false)
    private String healthServiceName;




    public HealthService(Integer ID, String healthServiceID, String healthServiceName) {
        this.ID = ID;
        this.healthServiceID = healthServiceID;
        this.healthServiceName = healthServiceName;
        }

    public HealthService() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getHealthServiceID() {
        return healthServiceID;
    }

    public void setHealthServiceID(String healthServiceID) {
        this.healthServiceID = healthServiceID;
    }

    public String getHealthServiceName() {
        return healthServiceName;
    }

    public void setHealthServiceName(String healthServiceName) {
        this.healthServiceName = healthServiceName;
    }

}
