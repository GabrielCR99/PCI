package com.studio.pci.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studio.pci.R;
import com.studio.pci.activities.ProjectActivity;
import com.studio.pci.models.Project;
import com.studio.pci.utils.GenericViewHolder;

import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    private List<Project> projects;
    private Context context;

    public ProjectsAdapter(List<Project> projects, Context context) {
        this.projects = projects;
        this.context = context;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_generic, viewGroup, false);
        return new GenericViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder genericViewHolder, int i) {
        final Project project = projects.get(i);
        genericViewHolder.getNameTextView().setText(project.getTitle());
        genericViewHolder.getInfoTextView().setText(project.getEndDate());
        genericViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.putExtra("PROJECT_ID",project.getId());
                context.startActivity(intent);
            }
        });
        Toast.makeText(context,"PROJETO ADICIONADO ADAPTER",Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}