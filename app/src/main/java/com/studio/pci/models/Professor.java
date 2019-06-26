package com.studio.pci.models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Professor implements Serializable {

    private String id;
    private String name;
    private String gender;
    private String birthDate;
    private String picture;
    private String degree;
    private String email;
    private String facebookUrl;
    private String skypeUrl;
    private String bio;
    private List<String> universityList;
    private boolean enable;

    public Professor(String id, String name, String email, boolean enable) {
        this.id = id;
        this.name = name;
        gender = "";
        birthDate = "";
        picture = "";
        degree = "";
        this.email = email;
        facebookUrl = "";
        skypeUrl = "";
        bio = "";
        universityList = new ArrayList<>();
        universityList.add(" ");
        this.enable = enable;
    }

    public Professor(){
    }

    public Professor(String id, String name, String gender, String birthDate, String picture,
                     String degree, String email, String facebookUrl, String skypeUrl, String bio,
                     List<String> universityList, boolean enable) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.picture = picture;
        this.degree = degree;
        this.email = email;
        this.facebookUrl = facebookUrl;
        this.skypeUrl = skypeUrl;
        this.bio = bio;
        this.universityList = universityList;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        Professor professor = (Professor) o;
        return isEnable() == professor.isEnable() &&
                Objects.equals(getId(), professor.getId()) &&
                Objects.equals(getName(), professor.getName()) &&
                Objects.equals(getGender(), professor.getGender()) &&
                Objects.equals(getBirthDate(), professor.getBirthDate()) &&
                Objects.equals(getPicture(), professor.getPicture()) &&
                Objects.equals(getDegree(), professor.getDegree()) &&
                Objects.equals(getEmail(), professor.getEmail()) &&
                Objects.equals(getFacebookUrl(), professor.getFacebookUrl()) &&
                Objects.equals(getSkypeUrl(), professor.getSkypeUrl()) &&
                Objects.equals(getBio(), professor.getBio()) &&
                Objects.equals(getUniversityList(), professor.getUniversityList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getGender(), getBirthDate(), getPicture(),
                getDegree(), getEmail(), getFacebookUrl(), getSkypeUrl(), getBio(),
                getUniversityList(), isEnable());
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public List<String> getUniversityList() {
        return universityList;
    }

    public void setUniversityList(List<String> universityList) {
        this.universityList = universityList;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}