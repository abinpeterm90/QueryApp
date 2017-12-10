package com.teksine.queryapplication.utils;

/**
 * Created by abin on 09/12/2017.
 */

public class GeneralFunctions {
    public String splitMail(String mail){
        String [] arrOfStr = mail.split("@", 2);
        System.out.println(arrOfStr[0]);
        return  arrOfStr[0];
    }
}
