package net.datascientists.entity;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN");
     
    String userProfileType;
     
    private Roles(String userProfileType){
        this.userProfileType = userProfileType;
    }
     
    public String getUserProfileType(){
        return userProfileType;
    }
     
}