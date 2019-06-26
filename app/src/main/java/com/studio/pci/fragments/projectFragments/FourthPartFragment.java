package com.studio.pci.fragments.projectFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.pci.R;

@SuppressLint("ValidFragment")
public class FourthPartFragment extends CustomFragment {

    public FourthPartFragment(NewProjectListener listener, Context context) {
        super(listener, context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newproject_phases,container,false);
        setComponents();
        return view;
    }
    @Override
    public void savePart() {

    }
}
