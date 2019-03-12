package com.studio.pci.models;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Professor implements Serializable {

    private String id;
    private String name;
    private String email;
    private String gender;
    private Date birthDate;
    private String picture;
    private String degree;
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        Professor professor = (Professor) o;
        return getId() == professor.getId() &&
                isEnable() == professor.isEnable() &&
                Objects.equals(getName(), professor.getName()) &&
                Objects.equals(getEmail(), professor.getEmail()) &&
                Objects.equals(getGender(), professor.getGender()) &&
                Objects.equals(getBirthDate(), professor.getBirthDate()) &&
                Objects.equals(getPicture(), professor.getPicture()) &&
                Objects.equals(getDegree(), professor.getDegree());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getEmail(), getGender(), getBirthDate(), getPicture(), getDegree(), isEnable());
    }

    public Professor(){}

    public Professor(String id, String name, String email, String gender, Date birthDate, String picture, String degree, boolean enable) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.picture = picture;
        this.degree = degree;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
