package com.studio.pci.providers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T extends Serializable> implements GenericDAO<T>{

    private DatabaseReference db;
    private String reference;

    public AbstractDAO(String tableReference){
        db = FirebaseDatabase.getInstance().getReference();
        reference = tableReference;
    }

    @Override
    public void create(String id,T entity) {
        db.child(reference).child(id).setValue(entity);
    }

    @Override
    public void update(String id,T entity) {
        db.child(reference).child(id).setValue(entity);
    }

    @Override
    public void delete(String id) {
        db.child(reference).child(id).removeValue();
    }

    @Override
    public String newKey() {
        return db.push().getKey();
    }
}