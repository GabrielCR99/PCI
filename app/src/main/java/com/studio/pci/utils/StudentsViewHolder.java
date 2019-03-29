package com.studio.pci.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.studio.pci.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.generic_name)
    TextView nameTextView;

    @BindView(R.id.generic_type)
    TextView typeTextView;

    @BindView(R.id.generic_info)
    TextView infoTextView;

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getTypeTextView() {
        return typeTextView;
    }

    public TextView getInfoTextView() {
        return infoTextView;
    }

    public StudentsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
