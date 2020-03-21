package com.example.demoapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpref {

    SharedPreferences sharedPreferences;
    public  Sharedpref(Context context){
        sharedPreferences = context.getSharedPreferences("filename" , Context.MODE_PRIVATE);

    }
    public void setCheckedIn(int c){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("checkedin" , c);
        editor.commit();
    }
    public int getCheckedIn (){
        int state = sharedPreferences.getInt("checkedin" , 0);
        return state;
    }

    public void setBmi(float bmi){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("Bmi" , bmi);
        editor.commit();
    }

    public double getBmi(){
        return sharedPreferences.getFloat("Bmi", -1);
    }

    public void setName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name" , name);
        editor.commit();
    }

    public String getName(){
        return sharedPreferences.getString("Name", "Name");
    }

    public void setvillage(String vname){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("village" , vname);
        editor.apply();
    }
    public  String getVillage(){
        return sharedPreferences.getString("village" , "village name");
    }

    public void setBp(float sbp, float dbp){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("dbp" , dbp);
        editor.putFloat("sbp" , sbp);
        editor.commit();
    }

    public float[] getBp(){
        float bp[] = {sharedPreferences.getFloat("sbp" , -1),sharedPreferences.getFloat("dbp" , -1) };
        return bp;
    }

    public void setSetup(Boolean bool){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("setup" , bool);
        editor.commit();
    }

    public Boolean getSetup(){
        return sharedPreferences.getBoolean("setup" , false);
    }





}
