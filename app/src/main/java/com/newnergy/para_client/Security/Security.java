package com.newnergy.para_client.Security;

import com.newnergy.para_client.ValueMessager;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kelvin on 2016/11/18.
 */

public class Security {

    public Date currentTime;
    public Calendar calendar;

    public Boolean tokenChecking(){

        calendar = Calendar.getInstance();
        currentTime = calendar.getTime();

        if(Long.parseLong(currentTime.toString()) > ValueMessager.tokenDueTime){

            return true;
        }else{
            return false;
        }

    }
}
