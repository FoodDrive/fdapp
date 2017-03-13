package com.project.vnr.fooddrive;

/**
 * Created by DELL on 13-03-2017.
 */

public class FoodDetails {
    public String uid;
    public String Time;
    public String postTime;
    public String Food;
    public FoodDetails()
    {

    }
    public FoodDetails(String uid,String food,String Time,String postTime){
        this.uid=uid;
        this.Time=Time;
        this.postTime=postTime;
        this.Food=food;
    }
}
