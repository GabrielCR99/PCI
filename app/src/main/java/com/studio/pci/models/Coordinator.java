package com.studio.pci.models;

import java.util.List;

public class Coordinator extends Professor{

    public Coordinator() {
    }

    public Coordinator(String id, String name, String email, boolean enable) {
        super(id, name, email, enable);
    }

    public Coordinator(String id, String name, String gender, String birthDate, String picture,
                       String degree, String email, String facebookUrl, String skypeUrl, String bio,
                       List<University> universityList, boolean enable) {
        super(id, name, gender, birthDate, picture, degree, email, facebookUrl, skypeUrl, bio, universityList, enable);
    }
}