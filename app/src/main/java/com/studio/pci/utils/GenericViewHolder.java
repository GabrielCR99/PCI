package com.studio.pci.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.pci.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenericViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.card_name)
    TextView nameTextView;

    @BindView(R.id.card_info)
    TextView infoTextView;

    public TextView getNameTextView() {
        return nameTextView;
    }


    public TextView getInfoTextView() {
        return infoTextView;
    }

    public GenericViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
