package com.codeinflow.timely.Model;

import com.google.firebase.firestore.PropertyName;

public class AnnouncementModel {
    @PropertyName("name")
    public String sender_name;
    @PropertyName("msg")
    public String sender_msg;


    public String getSenderName() {
        return sender_name;
    }

    public String getSenderMsg() {
        return sender_msg;
    }
}
