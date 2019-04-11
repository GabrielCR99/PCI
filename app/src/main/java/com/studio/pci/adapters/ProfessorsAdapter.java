package com.studio.pci.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.pci.R;
import com.studio.pci.activities.MainActivity;
import com.studio.pci.activities.ProfessorActivity;
import com.studio.pci.models.Professor;

import java.net.ServerSocket;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfessorsAdapter extends RecyclerView.Adapter<ProfessorsAdapter.ProfessorViewHolder> {

    private List<Professor> professors;
    private Context context;

    public ProfessorsAdapter(List<Professor> professors, Context context) {
        this.professors = professors;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfessorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_user, viewGroup, false);
        return new ProfessorsAdapter.ProfessorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorViewHolder viewHolder, int i) {
        final Professor professor = professors.get(i);
        viewHolder.nameTextView.setText(professor.getName());
        viewHolder.infoTextView.setText(professor.getEmail());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfessorActivity.class);
                intent.putExtra("UID",professor.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return professors.size();
    }

    static class ProfessorViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.card_name)
        TextView nameTextView;

        @BindView(R.id.card_info)
        TextView infoTextView;

        ProfessorViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
