package com.street35.booked.Profile;

/**
 * Created by Weirdo on 03-10-2016.
 */

public class ProfileModel {

    private String first_name = null , last_name = null , contact = null , university = null
            , address = null , email = null;
    private int sex ;    // 1->male , 2->female , 3->others


    ProfileModel(String first_name , String last_name , int sex , String contact , String address ,String university){

//        this.firsfirst_name

    }




    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
