package com.studio.pci.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.pci.R;
import com.studio.pci.activities.ProjectActivity;
import com.studio.pci.models.Project;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private List<Project> projects;
    private Context context;

    public ProjectsAdapter(List<Project> projects, Context context) {
        this.projects = projects;
        this.context = context;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_project, viewGroup, false);
        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsAdapter.ProjectViewHolder viewHolder, int i) {
        final Project project = projects.get(i);
        viewHolder.nameTextView.setText(project.getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.putExtra("PROJECT_ID",project.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.card_title)
        TextView nameTextView;

        @BindView(R.id.card_desc)
        TextView infoTextView;

        ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}