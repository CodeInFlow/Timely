package com.codeinflow.timely.Model;

import com.google.firebase.firestore.PropertyName;

public class ConstableModel {
    @PropertyName("name")
    public String const_name;
    @PropertyName("img")
    public String const_img;
    @PropertyName("date")
    public String const_date;
    @PropertyName("place")
    public String const_place;

    public String getConstName() {
        return const_name;
    }

    public String getConstImage() {
        return const_img;
    }

    public String getConstDate() {
        return const_date;
    }

    public String getConstPlace() {
        return const_place;
    }

}
