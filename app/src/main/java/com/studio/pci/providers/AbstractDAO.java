package com.studio.pci.providers;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;


public abstract class AbstractDAO<T extends Serializable> implements GenericDAO<T>{

    private DatabaseReference db;

    public AbstractDAO(String tableReference){
        db = FirebaseDatabase.getInstance().getReference(tableReference);
    }

    @Override
    public void create(@NonNull String id,@NonNull T entity) {
        db.child(id).setValue(entity);
    }

    @Override
    public void update(@NonNull String id,@NonNull T entity) {
        db.child(id).setValue(entity);
    }

    @Override
    public void delete(@NonNull String id) {
        db.child(id).removeValue();
    }

    @Override
    public String newKey() {
        return db.push().getKey();
    }

}