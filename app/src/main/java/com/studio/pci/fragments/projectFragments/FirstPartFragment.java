package com.studio.pci.fragments.projectFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.studio.pci.R;
import com.studio.pci.utils.DatePickerDialogHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

@SuppressLint("ValidFragment")
public class FirstPartFragment extends CustomFragment {

    private EditText titleEditText;
    private EditText descEditText;
    private EditText startEditText;
    private EditText endEditText;

    public FirstPartFragment(NewProjectListener listener, Context context) {
        super(listener, context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newproject_part1,container,false);
        setComponents();
        return view;
    }

    @Override
    public void setComponents() {
        super.setComponents();
        titleEditText = view.findViewById(R.id.project_title);
        descEditText = view.findViewById(R.id.project_description);
        startEditText = view.findViewById(R.id.project_startDate);
        endEditText = view.findViewById(R.id.project_endDate);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        DatePickerDialogHelper.setDatePickerDialog(startEditText,context,sdf);
        DatePickerDialogHelper.setDatePickerDialog(endEditText,context,sdf);
    }

    @Override
    public boolean savePart() {
        return false;
    }
}
