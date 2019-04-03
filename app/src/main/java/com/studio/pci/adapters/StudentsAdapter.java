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
import com.studio.pci.activities.StudentActivity;
import com.studio.pci.models.Student;
import com.studio.pci.utils.StudentsViewHolder;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsViewHolder> {

    private List<Student> students;
    private Context context;

    public StudentsAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_item, viewGroup, false);
        return new StudentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentsViewHolder studentsViewHolder, final int i) {
        final Student student = students.get(i);
        studentsViewHolder.getNameTextView().setText(student.getName());
        studentsViewHolder.getInfoTextView().setText(student.getEmail());
        studentsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StudentActivity.class);
                intent.putExtra("UID",student.getId());
                context.startActivity(intent);
                Toast.makeText(context,"TESTANDO : "+studentsViewHolder.getAdapterPosition(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}