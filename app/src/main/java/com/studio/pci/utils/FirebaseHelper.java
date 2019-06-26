package com.studio.pci.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {

    private static DatabaseReference reference;

    public static void getUniversity(String id,ValueEventListener listener){
        reference = FirebaseDatabase.getInstance().getReference("universities");
        reference.child(id).addListenerForSingleValueEvent(listener);
    }

    public static void getUniversities(ValueEventListener listener){
        reference = FirebaseDatabase.getInstance().getReference("universities");
        reference.addListenerForSingleValueEvent(listener);
    }

    public static void getProfessor(String id,ValueEventListener listener){
        reference = FirebaseDatabase.getInstance().getReference("professors");
        reference.child(id).addListenerForSingleValueEvent(listener);
    }

    public static void getProfessors(ValueEventListener listener){
        reference = FirebaseDatabase.getInstance().getReference("professors");
        reference.addListenerForSingleValueEvent(listener);
    }

    public static void getCoordinator(String id,ValueEventListener listener){
        reference = FirebaseDatabase.getInstance().getReference("coordinators");
        reference.child(id).addListenerForSingleValueEvent(listener);
    }

    public static void getCoordinators(ValueEventListener listener){
        reference = FirebaseDatabase.getInstance().getReference("coordinators");
        reference.addListenerForSingleValueEvent(listener);
    }

    public static void getStudent(String id,ValueEventListener listener){
        reference = FirebaseDatabase.getInstance().getReference("students");
        reference.child(id).addListenerForSingleValueEvent(listener);
    }

    public static void getStudents(ValueEventListener listener){
        reference = FirebaseDatabase.getInstance().getReference("students");
        reference.addListenerForSingleValueEvent(listener);
    }

    // TODO CONTINUAR COM O HELPER
}
