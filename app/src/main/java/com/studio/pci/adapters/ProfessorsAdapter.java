package com.studio.pci.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.activities.MainActivity;
import com.studio.pci.activities.ProfessorActivity;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Upload;

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
    public void onBindViewHolder(@NonNull final ProfessorViewHolder viewHolder, int i) {
        final Professor professor = professors.get(i);
        viewHolder.nameTextView.setText(professor.getName());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile_photo").child(professor.getId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    Picasso.get().load(upload.getPhoto()).placeholder(R.drawable.ic_launcher_background).into(viewHolder.imageView);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("BIND INFO ERROR",databaseError.getMessage());
            }
        });

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
