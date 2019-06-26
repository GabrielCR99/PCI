package com.studio.pci.fragments.projectFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.pci.R;

@SuppressLint("ValidFragment")
public class FifthPartFragment extends CustomFragment {

    public FifthPartFragment(NewProjectListener listener, Context context) {
        super(listener, context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newproject_list_selector,container,false);
        setComponents();
        return view;
    }

    @Override
    public void setComponents() {
        super.setComponents();
        TextView textView = view.findViewById(R.id.part_title);
        textView.setText(getString(R.string.students));
        TextView opcionalView = view.findViewById(R.id.opcional);
        opcionalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void savePart() {

    }
}
