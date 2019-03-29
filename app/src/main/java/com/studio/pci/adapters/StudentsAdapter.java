package com.studio.pci.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.pci.R;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_generic, viewGroup, false);
        return new StudentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder studentsViewHolder, int i) {
        Student student = students.get(i);
        studentsViewHolder.getNameTextView().setText(student.getName());
        studentsViewHolder.getTypeTextView().setText(student.getEmail());
        studentsViewHolder.getInfoTextView().setText(student.getId());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
