package com.example.snapchatclone.recyclerViewFollow;

public class FollowObject {
    private String email;
    private String uId;

    public FollowObject(String email, String uId){
        this.email=email;
        this.uId=uId;
    }

    public String getUId(){
        return uId;
    }

    public void setUId(String uId){
        this.uId=uId;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }
}
