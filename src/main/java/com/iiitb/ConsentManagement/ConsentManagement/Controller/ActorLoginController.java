package com.iiitb.ConsentManagement.ConsentManagement.Controller;


import com.iiitb.ConsentManagement.ConsentManagement.Beans.DemographicDetails;
import com.iiitb.ConsentManagement.ConsentManagement.HelperClasses.ActorLogin;
import com.iiitb.ConsentManagement.ConsentManagement.Services.ActorLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping(path="/api")

public class ActorLoginController {

    ActorLoginService actorLoginService;

    @Autowired
    public ActorLoginController(ActorLoginService actorLoginService)
    {
        this.actorLoginService = actorLoginService;
    }

    @PostMapping(path="/actorlogin", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Response authenticateActor(@RequestBody ActorLogin actorLogin)
    {
        System.out.println("[CLASS] Inside ActorLoginController: authenticateActor() ");
        System.out.println("Email: "+actorLogin.getEmail());
        System.out.println("Password: "+actorLogin.getPassword());

        boolean loginResult =  actorLoginService.actorAuthentication(actorLogin.getEmail(),actorLogin.getPassword());

        if(loginResult == false)
                return Response.status(401).build();

        return Response.ok().build();
    }

}
