package com.iiitb.ConsentManagement.ConsentManagement.Services;


import com.iiitb.ConsentManagement.ConsentManagement.Beans.VitalParams;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.VitalParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Named
public class VitalParamsService {

    VitalParametersRepository vitalParametersRepository;

    @Autowired
    public VitalParamsService(VitalParametersRepository vitalParametersRepository)
    {
        this.vitalParametersRepository = vitalParametersRepository;
    }

    public VitalParams saveVitalParams(VitalParams vitalParams)
    {
        System.out.println("[VitalParamsService] Inside saveVitalParams()");

        try
        {
            vitalParams = vitalParametersRepository.save(vitalParams);
            System.out.println("After saving vital params");
        }
        catch(Exception e)
        {
            System.out.println("[Exception]: Exception occuerd when trying to save vital params data");
            return null;
        }
        return vitalParams;
    }
}
