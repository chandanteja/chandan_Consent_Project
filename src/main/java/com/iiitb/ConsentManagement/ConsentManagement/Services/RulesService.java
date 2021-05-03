package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.MethodType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ROLE;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.Rules;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;

@Named
public class RulesService {

    RulesRepository rulesRepo;
    @Autowired
    public RulesService(RulesRepository rulesRepo)
    {
        this.rulesRepo = rulesRepo;
    }

    public List<ROLE> getRolesPermitted(String tableName, MethodType methodType)
    {
            List<Rules> rules = null;
            List<ROLE> rolesPermitted = null;

            System.out.println("Inside getRolesPermitted()");
            System.out.println("Tablename: "+tableName);
        System.out.println("MethodType: "+methodType);


        if(methodType != null)
            {
                System.out.println("Inside methodType!= null IF condition");
                rules = rulesRepo.findByMethodTypeAndTableName(methodType, tableName); // Table name + methodType will be unique.
                System.out.println("Inside methodType!= null IF condition");
            }
            else
                System.out.println("[RulesService-getRolesPermitted()]: Inside method Type == null case ");

            if(rules != null)
            {
                rolesPermitted = rules.get(0).getAllowedActorTypes();

                System.out.println("Roles are [Inside rulesservice]: ");
                for (ROLE r: rolesPermitted)
                {
                    System.out.println("ROLE is: "+ r);
                }

            }
                 else
                System.out.println("[RulesService-getRolesPermitted()]: Inside rules == null case ");

            return rolesPermitted;

    }

    public MethodType getMethodTypeFromOperation(String operation)
    {
        MethodType returnValue = MethodType.valueOf("INVALID");     // invalid operation

        if(operation.equals("CREATE"))      //string value of operation
            returnValue = MethodType.valueOf("INSERT");
        if(operation.equals("UPDATE"))      //string value of operation
            returnValue = MethodType.valueOf("UPDATE");
        if(operation.equals("DELETE"))      //string value of operation
            returnValue = MethodType.valueOf("DELETE");
        if(operation.equals("VIEW") || operation.equals("SHOW") )      //string value of operation
            returnValue = MethodType.valueOf("GET");


        return returnValue;
    }

}
