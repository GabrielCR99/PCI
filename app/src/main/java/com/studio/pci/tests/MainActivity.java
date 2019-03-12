package com.studio.pci.tests;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.studio.pci.R;
import com.studio.pci.dao.UniversityDAO;
import com.studio.pci.models.University;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Spinner spinner;
    Button button;
    ListView listView;
    List<University> universities;
    UniversityDAO universityDAO;

    DatabaseReference dbTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        universities = new ArrayList<>();
        universityDAO = new UniversityDAO();
        editText = findViewById(R.id.edit_test);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button_test);
        listView = findViewById(R.id.artist_listview);
        dbTest = FirebaseDatabase.getInstance().getReference("universities");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            University university = new University();
            university.setName(editText.getText().toString().trim());
            university.setDepartment(spinner.getSelectedItem().toString().trim());
            universityDAO.create(university);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                universities.clear();
                for(DataSnapshot artistSnapshot: dataSnapshot.getChildren()){
                    University university = artistSnapshot.getValue(University.class);
                    universities.add(university);
                }
                Adapter adapter = new Adapter(MainActivity.this,universities);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
