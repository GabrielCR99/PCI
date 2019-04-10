package com.studio.pci.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.pci.R;
import com.studio.pci.activities.StudentActivity;
import com.studio.pci.models.Student;
import com.studio.pci.utils.GenericViewHolder;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    private List<Student> students;
    private Context context;

    public StudentsAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_generic, viewGroup, false);
        return new GenericViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final GenericViewHolder genericViewHolder, final int i) {
        final Student student = students.get(i);
        genericViewHolder.getNameTextView().setText(student.getName());
        genericViewHolder.getInfoTextView().setText(student.getEmail());
        genericViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
}