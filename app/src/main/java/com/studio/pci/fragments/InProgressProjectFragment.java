package com.studio.pci.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import butterknife.ButterKnife;

public class InProgressProjectFragment extends Fragment {

    private View view;
    private List<Project> projects;
    private ProjectsAdapter adapter;
    private DatabaseReference db;
    private Context context;

    public InProgressProjectFragment() {
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_projetos_finalizados, container, false);
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
        db = FirebaseDatabase.getInstance().getReference("projects");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
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
                Log.v("PROJECTS_FIREBASE",databaseError.getMessage());
            }
        });
    }
}