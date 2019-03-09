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
    //private final University university = new University();

    public void create(University university){
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities");
        String id = dbUniversities.push().getKey();
        dbUniversities.child(id).setValue(university);
    }

    public void update(University university){
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities").child(university.getId());
        dbUniversities.setValue(university);
    }

    /*public University findById(final String id){
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities");
        dbUniversities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    University test = ds.child(id).getValue(University.class);
                    try{
                        university.setId(test.getId());
                        university.setName(test.getName());
                        university.setCountry(test.getCountry());
                        university.setState(test.getState());
                        university.setDepartment(test.getDepartment());
                        university.setPicture(test.getPicture());
                        university.setEnable(test.isEnable());
                    }catch (Exception e){
                        Log.w("PCI",e);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return university;
    }*/

    public List<University> findAll(){
        DatabaseReference dbUniversities = FirebaseDatabase.getInstance().getReference("universities");
        dbUniversities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                universities.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    University university = ds.getValue(University.class);
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
