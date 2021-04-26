package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Actor;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ROLE;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.ActorLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
public class ActorLoginService {

    ActorLoginRepository actorLoginRepo;

    @Autowired
    public ActorLoginService(ActorLoginRepository actorLoginRepo)
    {
        this.actorLoginRepo = actorLoginRepo;
    }

    public boolean actorAuthentication(String email,String password)
    {
        System.out.println("[CLASS-METHOD] Inside ActorLoginService-actorAuthentication");
        List<Actor>  actor = null;

        actor = actorLoginRepo.findByEmailIDAndPassword(email,password);

        if(actor.size()==0) {
            System.out.println("[IF-COND] Inside actor authentication service, Size is 0 ");
            return false;   // login failed
        }
        System.out.println("Username of actor is: "+ actor.get(0).getFullName());

        if(actor.get(0).getEmailID().equals(email) && actor.get(0).getPassword().equals(password)) {

            System.out.println("[IF-COND] Inside email and password match");
            return true; // login success
        }
    return false;   //login failed
    }

    public ROLE getActorRole(String email)
    {
        System.out.println("[CLASS-METHOD] Inside ActorLoginService-getActor");
        List<Actor>  actor = null;

        actor = actorLoginRepo.findByEmailID(email);
        if(actor.size()==0)
            return ROLE.ROLE_INVALID;
        return actor.get(0).getRole();

    }
}
