package com.studio.pci.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
    private RecyclerViewClickListener itemListener;
    private String TAG;

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position, String TAG);
    }

    public UniversitiesAdapter(List<University> universities,
                               Context context, RecyclerViewClickListener itemListener) {
        this.universities = universities;
        this.context = context;
        this.itemListener = itemListener;
    }

    public UniversitiesAdapter(List<University> universities,
                               Context context, RecyclerViewClickListener itemListener, String TAG) {
        this.universities = universities;
        this.context = context;
        this.itemListener = itemListener;
        this.TAG = TAG;
    }

    @NonNull
    @Override
    public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
        if(!TAG.equals("FILTER"))itemView = LayoutInflater.from(context).inflate(R.layout.card_round, viewGroup, false);
        else itemView = LayoutInflater.from(context).inflate(R.layout.card_project, viewGroup, false);
        return new UniversityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityViewHolder viewHolder,int i) {
        final University university = universities.get(i);
            if(!TAG.equals("FILTER")) viewHolder.nameTextView.setText(university.getInitials());
            else {
                viewHolder.nameTextView.setText(university.getName());
                viewHolder.infoTextView.setText(university.getDepartment());
            }
            viewHolder.itemView.setOnClickListener(v -> itemListener.recyclerViewListClicked(v, i,TAG));
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    static class UniversityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_title)
        TextView nameTextView;

        @BindView(R.id.card_desc)
        TextView infoTextView;

        UniversityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}