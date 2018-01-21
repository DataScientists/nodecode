package net.datascientist.constants;

public enum RoleType {
    USER("USER"),
    ADMIN("ADMIN");
     
    String userProfileType;
     
    private RoleType(String userProfileType){
        this.userProfileType = userProfileType;
    }
     
    public String getUserProfileType(){
        return userProfileType;
    }
     
}