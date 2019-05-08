package com.studio.pci.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.pci.R;
import com.studio.pci.models.University;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedUniversitiesAdapter extends RecyclerView.Adapter<SelectedUniversitiesAdapter.UniversityViewHolder>{

    private List<University> universities;
    private Context context;
    private static RecyclerViewClickListener itemListener;

    public interface RecyclerViewClickListener {
        void recyclerRoundItemClicked(View v, int position);
    }

    public SelectedUniversitiesAdapter(List<University> universities, Context context, RecyclerViewClickListener itemListener) {
        this.universities = universities;
        this.context = context;
        SelectedUniversitiesAdapter.itemListener = itemListener;
    }

    @NonNull
    @Override
    public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_round, viewGroup, false);
        return new SelectedUniversitiesAdapter.UniversityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityViewHolder viewHolder, int i) {
        final University university = universities.get(i);
        viewHolder.nameTextView.setText(university.getInitials());
        // TODO SET UNIVERSITY PHOTO
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    static class UniversityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_title)
        TextView nameTextView;

        @BindView(R.id.card_photo)
        ImageView photoImageView;

        UniversityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerRoundItemClicked(v, this.getAdapterPosition());
        }
    }
}
