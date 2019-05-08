package com.studio.pci.activities;

import android.app.SearchManager;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectUniversityActivity extends AppCompatActivity implements
        UniversitiesAdapter.RecyclerViewClickListener{

    private List<University> universities;
    private UniversitiesAdapter adapter;
    private University selectedUniversity;
    private EditText view;

    @BindView(R.id.recycler_universities)
    RecyclerView recyclerView;

    @OnClick(R.id.fab_confirm)
    public void onConfirmed(){
        view.setText(selectedUniversity.getName());
        finish();
    }

    public SelectUniversityActivity(EditText view) {
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_university);
        ButterKnife.bind(this);

        setUniversities();
        setRecyclerView();
        selectedUniversity = new University();
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UniversitiesAdapter(universities,this,null);
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
            if(university.getName().toLowerCase().equals(s.toLowerCase()) || university.getInitials().toLowerCase().equals(s.toLowerCase())){
                filteredUniversities.add(university);
            }
        }
        UniversitiesAdapter filteredAdapter = new UniversitiesAdapter(filteredUniversities,this,null);
        recyclerView.setAdapter(filteredAdapter);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        selectedUniversity = universities.get(position);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

}