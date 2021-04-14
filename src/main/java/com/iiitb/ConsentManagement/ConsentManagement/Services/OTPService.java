package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.inject.Named;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Named
public class OTPService {

    private Integer OTP;

    public Integer getOTP() {
        return OTP;
    }

    public void setOTP(Integer OTP) {
        this.OTP = OTP;
    }

    private static final Integer EXPIRE_MINS = 5;

    private LoadingCache<String, Integer> otpCache;

    public OTPService(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    //This method is used to push the opt number against Key. Rewrite the OTP if it exists
    //Using user id  as key
    public int generateOTP(String key){

        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }

    //This method is used to return the OPT number against Key->Key values is username
    public int getOtp(String key){
        try{
            return otpCache.get(key);
        }
        catch (Exception e){
            System.out.println("Exception occured inside getOtp of OTPService method");
           e.printStackTrace();
           return 0;
        }
    }

    //This method is used to clear the OTP catched already
    public void clearOTP(String key)
    {
        otpCache.invalidate(key);
    }

}
