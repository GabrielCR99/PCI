package com.studio.pci.models;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Student implements Serializable {

    private String uid;
    private String name;
    private String gender;
    private String birthDate;
    private String picture;
    private String email;
    private String facebookUrl;
    private String skypeUrl;
    private boolean enable;

    public Student(){
        this.uid = "";
        this.name = "";
        this.email = "";
        this.gender = "";
        this.birthDate = "";
        this.picture = "";
        this.enable = true;
    }

    public Student(String id, String name, String email, String gender, String birthDate, String picture, boolean enable) {
        this.uid = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.picture = picture;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return uid.equals(student.uid) &&
                enable == student.enable &&
                Objects.equals(name, student.name) &&
                Objects.equals(email, student.email) &&
                Objects.equals(gender, student.gender) &&
                Objects.equals(birthDate, student.birthDate) &&
                Objects.equals(picture, student.picture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, name, email, gender, birthDate, picture, enable);
    }

    public String getId() {
        return uid;
    }

    public void setId(String id) {
        this.uid = id;
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
}