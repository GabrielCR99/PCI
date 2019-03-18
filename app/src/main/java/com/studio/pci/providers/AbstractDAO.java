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

public class AbstractDAO<T extends Serializable> implements GenericDAO<T>{

    private T t;
    private List<T> tList;
    private DatabaseReference db;
    private Class<T> currentClass;

    public AbstractDAO(String tableReference){
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.currentClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        tList = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference(tableReference);
    }

    private final ValueEventListener findById = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            t = dataSnapshot.getValue(currentClass);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.v("FindById DatabaseError",databaseError.getMessage());
        }
    };

    private final ValueEventListener findAll = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            tList.clear();
            for(DataSnapshot ds: dataSnapshot.getChildren()){
                T t = ds.getValue(currentClass);
                tList.add(t);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.v("FindAll DatabaseError",databaseError.getMessage());
        }
    };

    @Override
    public T findById(final String id) {
        db.child(id).addListenerForSingleValueEvent(findById);
        return t;
    }

    @Override
    public List<T> findAll() {
        db.addValueEventListener(findAll);
        return tList;
    }

    @Override
    public void create(String id,T entity) {
        db.child(id).setValue(entity);
    }

    @Override
    public void update(String id,T entity) {
        db.child(id).setValue(entity);
    }

    @Override
    public void delete(String id) {
        db.child(id).removeValue();
    }

    @Override
    public String newKey() {
        return db.push().getKey();
    }
}
