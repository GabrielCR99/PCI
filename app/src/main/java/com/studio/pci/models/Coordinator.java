package com.studio.pci.models;

public class Coordinator extends Professor{

    public Coordinator() {
    }

    public Coordinator(String id, String name, String email, String bio, boolean enable) {
        super(id, name, email, bio, enable);
    }

    public Coordinator(String id, String name, String gender, String birthDate, String picture, String degree, String email, String facebookUrl, String skypeUrl, String bio, boolean enable) {
        super(id, name, gender, birthDate, picture, degree, email, facebookUrl, skypeUrl, bio, enable);
    }
}