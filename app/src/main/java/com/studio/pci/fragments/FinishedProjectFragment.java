package com.studio.pci.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.studio.pci.R;
import com.studio.pci.adapters.ProjectsAdapter;
import com.studio.pci.models.Project;

import java.util.List;

public class FinishedProjectFragment extends Fragment {

    private List<Project> projects;
    private ProjectsAdapter adapter;
    private DatabaseReference db;

    public FinishedProjectFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projects_finished, container, false);
    }

}