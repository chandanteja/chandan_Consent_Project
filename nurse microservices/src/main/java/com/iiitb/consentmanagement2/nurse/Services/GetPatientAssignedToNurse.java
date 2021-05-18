package com.iiitb.consentmanagement2.nurse.Services;



import com.iiitb.consentmanagement2.nurse.Beans.Actor;
import com.iiitb.consentmanagement2.nurse.DAO.ActorRepository;
import com.iiitb.consentmanagement2.nurse.HelperClasses.BasicPatientData;
import com.iiitb.consentmanagement2.nurse.HelperClasses.NurseEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Named;
import javax.swing.*;
import java.util.List;

@Named
public class GetPatientAssignedToNurse {

    ActorRepository actorRepository;

    @Autowired
    public GetPatientAssignedToNurse(ActorRepository actorRepository)
    {
        this.actorRepository = actorRepository;
    }

    public BasicPatientData getPatientDataForNurse(NurseEmail nurseEmail)
    {
        System.out.println("[GetPatientAssignedToNurse]: Inside getPatientDataForNurse()");
        List<Actor> nurse = null;
        String nurseID = null;
        nurse = actorRepository.findByEmailID(nurseEmail.getLoginEmail());

        if(nurse.size() == 0)
                return null;

        nurseID = nurse.get(0).getActorID();

        System.out.println("Nurse ID is:"+nurseID);

        String uri = UriComponentsBuilder.fromUriString("http://localhost:9092/api/findBasicPatientForNurse/").queryParam("nurseID",nurseID).build().toString();
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Before restcall to consent mgmt micro-service");
        ResponseEntity<BasicPatientData> response = restTemplate.postForEntity(uri,null,BasicPatientData.class);

        System.out.println("After coming back from COnsent mgmt microservice");

        if(response == null)
                return null;

        System.out.println("Patent ID is: "+response.getBody().getPatientID());
        System.out.println("Patient Name is: "+response.getBody().getPatientName());
        System.out.println("Patient AGe is: "+ response.getBody().getAge());
        System.out.println("Blood grp is: "+ response.getBody().getBloodGroup());


        return response.getBody();
    }
}
