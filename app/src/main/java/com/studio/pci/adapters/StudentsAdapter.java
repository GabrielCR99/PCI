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
import com.studio.pci.activities.StudentActivity;
import com.studio.pci.models.Student;

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
        viewHolder.infoTextView.setText(student.getEmail());
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

        @BindView(R.id.card_name)
        TextView nameTextView;

        @BindView(R.id.card_info)
        TextView infoTextView;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}