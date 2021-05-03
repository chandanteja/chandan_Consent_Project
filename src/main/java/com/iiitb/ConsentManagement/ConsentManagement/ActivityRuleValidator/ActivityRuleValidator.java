package com.iiitb.ConsentManagement.ConsentManagement.ActivityRuleValidator;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthServiceType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.MethodType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ROLE;
//import com.iiitb.ConsentManagement.ConsentManagement.Services.ActorService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.RulesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// This is service class

@Named
public class ActivityRuleValidator {


    RulesService rulesService;
   // ActorService actorService;

    @Autowired
    public ActivityRuleValidator(RulesService rulesService)//, ActorService actorService)
    {
        this.rulesService = rulesService;
       // this.actorService = actorService;
    }

    public String validateRegistrationActivityRule(String consent, String operation, LocalTime operationTime, String purpose, String tableName, ROLE actorRole)
    {

        MethodType operationPermitted =  rulesService.getMethodTypeFromOperation(operation);
        HealthServiceType healthServiceType ;
        LocalTime endTime,loginTime;
        List<ROLE> rolesPermitted = null;

        System.out.println("[ActivityRuleValidator]:OperationPermitted is: "+ operationPermitted);

        if(!consent.equals("true"))  // patient has selected the checkbox in frontend form and hence true
            return "INVALID_CONSENT";       // Consent is not given


        System.out.println("Before operationpermitted check");

        if(operationPermitted.toString().equals("INVALID"))
                return "INVALID_OPERATION";       // INVALID operation


        try
        {
            healthServiceType = HealthServiceType.valueOf(purpose.toUpperCase());
        }
        catch(Exception e)
        {
            System.out.println("[EXCEPTION]: Inside ActivityRuleValidator-validateRegistrationActivityRules(): Failed to get HealthServiceType from given Purpose");
            return "INVALID_PURPOSE";       // if we are unable to get healthservice from purpose then we get exception, we catch it and return from it.
        }

        // This time validation is done at respective actors' microservices before invoking any services which needs validation of time.
        //endTime = actorService.getActorEndTime(actorEmail);
       // loginTime = actorService.getActorLoginTime(actorEmail);

       // if(!(operationTime.isAfter(loginTime) && operationTime.isBefore(endTime)))
        //        return 5;

        System.out.println("HealthService Type is:"+healthServiceType);
        System.out.println("Before going to getRolesPermitted()");

        rolesPermitted = rulesService.getRolesPermitted(tableName,operationPermitted);

        System.out.println("After getRolesPermitted call is over");

        if(! rolesPermitted.contains(actorRole))
                return "PERMISSION_DENIED_FOR_ROLE";      // given role doesn't have permission to do this operation





        return "SUCCESS"; // every validation rule check is passed successfully
    }



}
