package com.studio.pci.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.StudentsAdapter;
import com.studio.pci.adapters.UniversitiesAdapter;
import com.studio.pci.models.Student;
import com.studio.pci.models.University;
import com.studio.pci.models.Upload;
import com.studio.pci.providers.StudentDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectUniversityActivity extends AppCompatActivity implements
        UniversitiesAdapter.RecyclerViewClickListener {

    private List<University> universities;
    private UniversitiesAdapter adapter;
    private University selectedUniversity;
    private String uid;

    @BindView(R.id.recycler_universities)
    RecyclerView recyclerView;

    @BindView(R.id.selected_university_name)
    TextView unNameTextView;

    @BindView(R.id.selected_university_department)
    TextView unDeptTextView;

    @OnClick(R.id.fab_confirm)
    public void onConfirmed(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("UNIVERSITY",selectedUniversity);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_university);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        uid = intent.getStringExtra("UID");

        setUniversities();
        setRecyclerView();
        selectedUniversity = new University();
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UniversitiesAdapter(universities,this,this);
        recyclerView.setAdapter(adapter);
    }

    private void setUniversities() {
        universities = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("universities");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                universities.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    University university = ds.getValue(University.class);
                    if(university.isEnable()) universities.add(university);
                    if(university.getId().equals(uid)){
                        unNameTextView.setText(selectedUniversity.getName());
                        unDeptTextView.setText(selectedUniversity.getDepartment());
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("ERROR_UNIVERSITY",databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.user_search).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                search(query);
                return true;
            }
        });
        return true;
    }

    private void search(String s) {
        List<University> filteredUniversities = new ArrayList<>();
        for(University university : universities){
            // SEARCH BY INITIALS OR NAME
            if(university.getName().toLowerCase().contains(s.toLowerCase()) || university.getInitials().toLowerCase().contains(s.toLowerCase())){
                filteredUniversities.add(university);
            }
        }
        UniversitiesAdapter filteredAdapter = new UniversitiesAdapter(filteredUniversities,this,this);
        recyclerView.setAdapter(filteredAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        selectedUniversity = universities.get(position);
        unNameTextView.setText(selectedUniversity.getName());
        unDeptTextView.setText(selectedUniversity.getDepartment());
    }
}