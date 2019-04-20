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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.activities.StudentActivity;
import com.studio.pci.activities.ViewStudentsActivity;
import com.studio.pci.models.Student;
import com.studio.pci.models.Upload;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private List<Student> students;
    private Context context;

    public StudentsAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_user, viewGroup, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentViewHolder viewHolder, final int i) {
        final Student student = students.get(i);
        viewHolder.nameTextView.setText(student.getName());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile_photo").child(student.getId());
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
                Log.e("BIND_INFO_ERROR",databaseError.getMessage());
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StudentActivity.class);
                intent.putExtra("UID",student.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.card_photo)
        ImageView imageView;

        @BindView(R.id.card_name)
        TextView nameTextView;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}