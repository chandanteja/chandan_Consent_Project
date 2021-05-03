package com.iiitb.ConsentManagement.ConsentManagement.Beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Component
@Table(name="rules_operation")
public class Rules {

    @Id
    private String Id;

    @Column(nullable = false)
    private String tableName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MethodType methodType;


    private String allowedActorTypes;       // These are roles not actor instances. ActorTypes --> ROLES


    public Rules() {
    }

    public Rules(String id, String tableName, MethodType methodType, String allowedActorTypes) {
        Id = id;
        this.tableName = tableName;
        this.methodType = methodType;
        this.allowedActorTypes = allowedActorTypes;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    public void setAllowedActorTypes(String allowedActorTypes) {
        this.allowedActorTypes = allowedActorTypes;
    }


    public List<ROLE> getAllowedActorTypes()
    {
        return Arrays.asList(allowedActorTypes.split(",")).stream()
                .map(actorType -> ROLE.valueOf(actorType)).collect(Collectors.toList());

    }
}


