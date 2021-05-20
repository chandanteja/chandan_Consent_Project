package com.iiitb.consentmanagement.doctormicroservice.Services;

import com.iiitb.consentmanagement.doctormicroservice.Beans.Actor;
import com.iiitb.consentmanagement.doctormicroservice.Beans.ROLE;
import com.iiitb.consentmanagement.doctormicroservice.Beans.Status;
import com.iiitb.consentmanagement.doctormicroservice.DAO.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;
import java.util.Random;

@Named
public class DoctorAvailabilityService {

    ActorRepository actorRepository;

    @Autowired
    public DoctorAvailabilityService(ActorRepository actorRepository)
    {
        this.actorRepository = actorRepository;
    }

    public String getAvailableDoctor(Status actorStatus, ROLE actorRole)
    {
        List<Actor> actor = null;
        Actor availableActor = null;
        Random randomNumber = new Random();

        System.out.println("[DoctorAvailabilityService]: Inside getAvailableDoctor");
        actor = actorRepository.findByStatusAndRole(actorStatus,actorRole);

        if(actor.size()==0)
            return "FAILED_TO_FIND_DOCTOR";

        int result = randomNumber.nextInt((actor.size()) ) + 0 ;

        availableActor = actor.get(result);
        System.out.println("[DoctorAvailabilityService]: After finding an actor randomly by choosing random number. Returning now from doctorservice");

        availableActor.setStatus(Status.BUSY);

        try
        {
            availableActor = actorRepository.save(availableActor);
        }
        catch (Exception e)
        {
            System.out.println("[Exception]: Exception occurred when saving doctor data");
            return "FAILED_TO_UPDATE_DOCTOR_STATUS";
        }

        System.out.println("Changed the status of Nurse to Busy from IDLE");

        // Need to change the status of the actor selected to BUSY.

        return availableActor.getActorID();
    }

    public String changeDoctorAvailableStatus(String doctorID)
    {

        System.out.println("Inside DoctorAvailabilityService-changeDoctorAvailableStatus ");
        List<Actor> actor = null;
        if(doctorID!= null) {
            actor = actorRepository.findByActorID(doctorID);
        }
        else{
            return "SUCCESS";   // if there is no actor thern no need to change the status for actor
        }
        if(actor == null || actor.size()==0)
        {
            System.out.println("Inside actor!=null and actor size ==0");
            return "FAILED_TO_CHANGE_STATUS";

        }
        return "SUCCESS";
    }
}
