package com.studio.pci.providers;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.models.University;

import java.util.ArrayList;
import java.util.List;

public class UniversityDAO {

    private final List<University> universities = new ArrayList<>();

    public void create(University university){
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities");
        String id = dbUniversities.push().getKey();
        dbUniversities.child(id).setValue(university);
    }

    public void update(University university){
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities").child(university.getId());
        dbUniversities.setValue(university);
    }

    public University findById(String id){
        final String idUniversity = id;
        final University university = new University();
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities");
        dbUniversities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    university.setId(ds.child(idUniversity).getValue(University.class).getId());
                    university.setName(ds.child(idUniversity).getValue(University.class).getName());
                    university.setCountry(ds.child(idUniversity).getValue(University.class).getCountry());
                    university.setState(ds.child(idUniversity).getValue(University.class).getState());
                    university.setDepartment(ds.child(idUniversity).getValue(University.class).getDepartment());
                    university.setEnable(ds.child(idUniversity).getValue(University.class).isEnable());
                    university.setPicture(ds.child(idUniversity).getValue(University.class).getPicture());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return university;
    }

    public List<University> findAll(){
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities");
        dbUniversities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                universities.clear();
                for(DataSnapshot artistSnapshot: dataSnapshot.getChildren()){
                    University university = artistSnapshot.getValue(University.class);
                    universities.add(university);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return universities;
    }

    public void delete(University university){
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities").child(university.getId());
        dbUniversities.removeValue();
    }
}
