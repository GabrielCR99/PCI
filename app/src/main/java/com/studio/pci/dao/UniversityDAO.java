package com.studio.pci.dao;

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

    public void findById(String id){
        //Pesquisar mais sobre
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
