package com.studio.pci.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.pci.R;
import com.studio.pci.models.University;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UniversitiesAdapter extends RecyclerView.Adapter<UniversitiesAdapter.UniversityViewHolder> {

    private List<University> universities;
    private Context context;
    private static  RecyclerViewClickListener itemListener;

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position);
    }

    public UniversitiesAdapter(List<University> universities, Context context, RecyclerViewClickListener itemListener) {
        this.universities = universities;
        this.context = context;
        UniversitiesAdapter.itemListener = itemListener;
    }

    @NonNull
    @Override
    public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_project, viewGroup, false);
        return new UniversityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityViewHolder viewHolder,int i) {
        final University university = universities.get(i);
            viewHolder.nameTextView.setText(university.getName());
            viewHolder.infoTextView.setText(university.getDepartment());
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    static class UniversityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_title)
        TextView nameTextView;

        @BindView(R.id.card_desc)
        TextView infoTextView;

        UniversityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getAdapterPosition());
        }
    }
}