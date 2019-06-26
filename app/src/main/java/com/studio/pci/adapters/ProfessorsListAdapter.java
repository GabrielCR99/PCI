package com.studio.pci.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.studio.pci.R;
import com.studio.pci.listeners.RecyclerViewClickListener;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Upload;
import com.studio.pci.utils.NameCutterHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfessorsListAdapter extends RecyclerView.Adapter<ProfessorsListAdapter.ProfessorViewHolder> {

    private List<Professor> professors;
    private List<Upload> uploads;
    private Context context;
    private RecyclerViewClickListener itemListener;
    private String TAG;

    public ProfessorsListAdapter(List<Professor> professors, List<Upload> uploads,
                                 Context context, RecyclerViewClickListener itemListener, String TAG) {
        this.professors = professors;
        this.uploads = uploads;
        this.context = context;
        this.itemListener = itemListener;
        this.TAG = TAG;
    }

    @NonNull
    @Override
    public ProfessorsListAdapter.ProfessorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (!TAG.equals("FILTER"))
            itemView = LayoutInflater.from(context).inflate(R.layout.card_round, parent, false);
        else itemView = LayoutInflater.from(context).inflate(R.layout.card_project, parent, false);
        return new ProfessorsListAdapter.ProfessorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorViewHolder viewHolder, int i) {
        Upload upload = uploads.get(i);
        final Professor professor = professors.get(i);

        if (TAG.equals("FILTER")) viewHolder.infoTextView.setText(professor.getEmail());

        viewHolder.nameTextView.setText(NameCutterHelper.cutName(professor.getName(), 0));

        if (!upload.getPhoto().equals("null")) Picasso.get().load(upload.getPhoto())
                .placeholder(R.drawable.ic_launcher_background)
                .into(viewHolder.photoView);
        viewHolder.itemView.setOnClickListener(v -> itemListener.recyclerViewListClicked(v, i, TAG));
    }

    @Override
    public int getItemCount() {
        return professors.size();
    }

    static class ProfessorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_photo)
        ImageView photoView;

        @BindView(R.id.card_title)
        TextView nameTextView;

        @BindView(R.id.card_desc)
        TextView infoTextView;

        ProfessorViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
