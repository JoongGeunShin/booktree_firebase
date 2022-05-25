package org.techtown.booktree_firebase;

public class MemberInfo {
    private String name;
    private String phoneNumber;
    private String gender;
    private String birthday;
    private String userEmail;

    MemberInfo(String name, String phoneNumber, String gender, String birthday, String userEmail){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
        this.userEmail = userEmail;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getGender(){
        return this.gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }

    public String getBirthday(){
        return this.birthday;
    }
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    public String getUserEmail() { return this.userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

}
