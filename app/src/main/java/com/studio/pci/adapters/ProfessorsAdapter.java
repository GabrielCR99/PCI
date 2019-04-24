package com.studio.pci.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.activities.ProfessorActivity;
import com.studio.pci.activities.StudentActivity;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Upload;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfessorsAdapter extends RecyclerView.Adapter<ProfessorsAdapter.ProfessorViewHolder> {

    private List<Professor> professors;
    private List<Upload> uploads;
    private Context context;

    public ProfessorsAdapter(List<Professor> professors,List<Upload> uploads, Context context) {
        this.professors = professors;
        this.uploads = uploads;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfessorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_user, viewGroup, false);
        return new ProfessorsAdapter.ProfessorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfessorViewHolder viewHolder, int i) {
        Upload upload = uploads.get(i);
        final Professor professor = professors.get(i);
        String[] name = professor.getName().split(" ");
        String first = name[0];
        viewHolder.nameTextView.setText(first);
        if(!upload.getPhoto().equals("null")) Picasso.get().load(upload.getPhoto()).placeholder(R.drawable.ic_launcher_background).into(viewHolder.imageView);
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

        @BindView(R.id.card_photo)
        ImageView imageView;

        @BindView(R.id.card_name)
        TextView nameTextView;

        ProfessorViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
