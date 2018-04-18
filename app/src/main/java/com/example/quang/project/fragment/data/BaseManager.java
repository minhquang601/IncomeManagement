package com.example.quang.project.fragment.data;

import java.util.Calendar;

/**
 * Created by quang on 3/16/18.
 */

public class BaseManager {

    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day+"/"+month+"/"+year;
    }

}
