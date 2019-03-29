package com.studio.pci.models;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String id;
    private String type;

    public User() {
    }

    public User(String uid, String type) {
        this.id = uid;
        this.type = type;
    }

    public String getUid() {
        return id;
    }

    public void setUid(String uid) {
        this.id = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUid(), user.getUid()) &&
                Objects.equals(getType(), user.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getType());
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
