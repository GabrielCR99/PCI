package com.studio.pci.models;
import com.studio.pci.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Student implements Serializable {

    private String id;
    private String name;
    private String gender;
    private String birthDate;
    private String picture;
    private String email;
    private String facebookUrl;
    private String skypeUrl;
    private boolean enable;

    public Student(){
    }

    public Student(String id, String name, String gender, String birthDate, String picture, String email, String facebookUrl, String skypeUrl, boolean enable) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.picture = picture;
        this.email = email;
        this.facebookUrl = facebookUrl;
        this.skypeUrl = skypeUrl;
        this.enable = enable;
    }

    public Student(String id, String name, String email, boolean enable) {
        this.id = id;
        this.name = name;
        gender = "";
        birthDate = "";
        picture = "";
        this.email = email;
        facebookUrl = "";
        skypeUrl = "";
        this.enable = enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getSkypeUrl() {
        return skypeUrl;
    }

    public void setSkypeUrl(String skypeUrl) {
        this.skypeUrl = skypeUrl;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                ", facebookUrl='" + facebookUrl + '\'' +
                ", skypeUrl='" + skypeUrl + '\'' +
                ", enable=" + enable +
                '}';
    }

    public ArrayList<String> toArray(){
        ArrayList<String> studentInfo = new ArrayList<>();
        studentInfo.add(id);
        studentInfo.add(name);
        studentInfo.add(gender);
        studentInfo.add(birthDate);
        studentInfo.add(picture);
        studentInfo.add(email);
        studentInfo.add(facebookUrl);
        studentInfo.add(skypeUrl);
        studentInfo.add(enable ? "true" : "false");
        return studentInfo;
    }
}