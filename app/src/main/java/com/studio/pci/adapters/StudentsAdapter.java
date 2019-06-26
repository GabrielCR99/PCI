package com.studio.pci.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.activities.StudentActivity;
import com.studio.pci.models.Student;
import com.studio.pci.models.Upload;
import com.studio.pci.utils.NameCutterHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private List<Student> students;
    private List<Upload> uploads;
    private Context context;

    public StudentsAdapter(List<Student> students, List<Upload> uploads,Context context) {
        this.students = students;
        this.uploads = uploads;
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
        Upload upload = uploads.get(i);
        final Student student = students.get(i);
        viewHolder.nameTextView.setText(NameCutterHelper.cutName(student.getName(),0));
        if(!upload.getPhoto().equals("null")) Picasso.get().load(upload.getPhoto()).placeholder(R.drawable.ic_launcher_background).into(viewHolder.imageView);
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