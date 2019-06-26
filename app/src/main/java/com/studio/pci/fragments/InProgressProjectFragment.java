package com.studio.pci.fragments;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.ProjectsAdapter;
import com.studio.pci.models.Project;

import java.util.ArrayList;
import java.util.List;

public class InProgressProjectFragment extends Fragment{

    private View view;
    private List<Project> projects;
    private ProjectsAdapter adapter;
    private Context context;

    public InProgressProjectFragment() {
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_projects_inprogress, container, false);
        setProjects();
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_projects);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        adapter = new ProjectsAdapter(projects, context);
        recyclerView.setAdapter(adapter);
    }

    private void setProjects() {
        projects = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("projects");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                projects.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Project project = ds.getValue(Project.class);
                        projects.add(project);
                        adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("PROJECTS_FIREBASE_ERROR",databaseError.getMessage());
            }
        });
    }
}