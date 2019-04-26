package com.studio.pci.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.StudentsAdapter;
import com.studio.pci.models.Project;
import com.studio.pci.models.Student;
import com.studio.pci.models.Upload;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewStudentsActivity extends AppCompatActivity {

    private String projectID;
    private List<Student> students;
    private List<Upload> uploads;
    private StudentsAdapter adapter;
    private Project project;

    @BindView(R.id.recycler_users_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        projectID = intent.getStringExtra("PROJECT_ID");
        setStudents();
        setRecyclerView();
    }

    private void search(String s) {
        List<Student> studentFilter = new ArrayList<>();
        List<Upload> uploadFilter = new ArrayList<>();

        for(int i=0 ; i < students.size() ; i++ ){
            if(students.get(i).getName().toLowerCase().contains(s.toLowerCase())){
                studentFilter.add(students.get(i));
                uploadFilter.add(uploads.get(i));
            }
        }

        StudentsAdapter studentsFiltered = new StudentsAdapter(studentFilter,uploadFilter,this);
        recyclerView.setAdapter(studentsFiltered);
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new StudentsAdapter(students,uploads,this);
        recyclerView.setAdapter(adapter);
    }

    private void setStudents() {
        students = new ArrayList<>();
        uploads = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                project = dataSnapshot.child("projects").child(projectID).getValue(Project.class);
                students.clear();
                uploads.clear();
                for(String id : project.getStudents()){
                    Student student = dataSnapshot.child("students").child(id).getValue(Student.class);
                    Upload upload;
                    if(dataSnapshot.child("profile_photo").child(id).exists()) upload = dataSnapshot.child("profile_photo").child(id).getValue(Upload.class);
                    else upload = new Upload("null");
                    students.add(student);
                    uploads.add(upload);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DATABASE_PROJECT_ERROR",databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

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
}