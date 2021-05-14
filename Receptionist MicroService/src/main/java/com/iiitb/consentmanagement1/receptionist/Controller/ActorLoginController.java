package com.iiitb.consentmanagement1.receptionist.Controller;



import com.iiitb.consentmanagement1.receptionist.Beans.ROLE;
import com.iiitb.consentmanagement1.receptionist.HelperClasses.ActorLogin;
import com.iiitb.consentmanagement1.receptionist.Services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.time.LocalTime;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActorLoginController {

    ActorService actorService;
    private final ROLE ACTOR_ROLE = ROLE.ROLE_RECEPTIONIST;    // In this microservice we verify the actors which belong to this microservice role only.

    @Autowired
    public ActorLoginController(ActorService actorService)
    {
        this.actorService = actorService;
    }

    @PostMapping(path="/actorlogin", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Response authenticateActor(@RequestBody ActorLogin actorLogin)
    {
        System.out.println("[CLASS] Inside ActorLoginController: authenticateActor() ");
        System.out.println("Email: "+actorLogin.getEmail());
        System.out.println("Password: "+actorLogin.getPassword());

        boolean loginResult =  actorService.actorAuthentication(actorLogin.getEmail(),actorLogin.getPassword(),ACTOR_ROLE);
        String setLoginTime = "LOGIN_TIME_NOT_SET"; // time is not yet set

        if(loginResult == false)
                return Response.status(401).build();

        if(loginResult != false)
        {
            System.out.println("Going to set login time of logged in actor");
            setLoginTime = actorService.setActorLoginTime(actorLogin.getEmail(), LocalTime.now());


        }
        if(!setLoginTime.equals("SUCCESSFULLY_SET_LOGIN_TIME"))
                return Response.status(401).build();

        System.out.println("Actor authenticated successfully and login time is set");

        return Response.ok().entity(ACTOR_ROLE).build();
    }

}
