package com.teksine.queryapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.teksine.queryapplication.model.EndUser;
import com.teksine.queryapplication.model.User;

/**
 * Created by abin on 09/12/2017.
 */

public class SharedPreferencesManager {
    private static final String APP_SETTINGS = "APP_SETTINGS";
    static SharedPreferencesManager sharedPreferencesManager;


    // properties
    private static final String SOME_STRING_VALUE = "SOME_STRING_VALUE";
    // other properties...


    private SharedPreferencesManager() {}

    public  SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }
    public static SharedPreferencesManager getSharedPreferanceManager(){
        if(sharedPreferencesManager!=null){
            return sharedPreferencesManager;
        }
        else{
            return new SharedPreferencesManager();
        }
    }

    public User getUserInformation(Context context,String value) {
        Gson gson = new Gson();
        String json = getSharedPreferences(context).getString(value,"");
        User obj = gson.fromJson(json, User.class);
        return  obj;
    }
//
    public  void setUserInformation(Context context, User user) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String tempValue = gson.toJson(user);
        editor.putString("user" , tempValue);
        editor.commit();
    }
    public EndUser getEndUserInformation(Context context,String value) {
        Gson gson = new Gson();
        String json = getSharedPreferences(context).getString(value,"");
        EndUser obj = gson.fromJson(json, EndUser.class);
        return  obj;
    }
    //
    public  void setEndUserInformation(Context context, EndUser user) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String tempValue = gson.toJson(user);
        editor.putString("endUser" , tempValue);
        editor.commit();
    }
public void setGoogleId(Context context,String googleId){
    final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
    editor.putString("googleId",googleId);
    editor.commit();
}
public String getGoogleId(Context context){
    return getSharedPreferences(context).getString("googleId","");
}
public void setQueryId(Context context,String queryId){
    final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
    editor.putString("queryId",queryId);
    editor.commit();
}
    public String getQueryId(Context context){
        return getSharedPreferences(context).getString("queryId","");
    }
}
