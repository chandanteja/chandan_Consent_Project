package com.iiitb.ConsentManagement.ConsentManagement.Beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Rules {

    @Id
    private String Id;
    private String tableName;
    private MethodType methodType;


    private String allowedActorTypes;

    public List<ROLE> getAllowedActorTypes()
    {
        return Arrays.asList(allowedActorTypes.split(",")).stream()
                .map(actorType -> ROLE.valueOf(actorType)).collect(Collectors.toList());

    }






}
