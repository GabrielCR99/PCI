package com.studio.pci.fragments.projectFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
        view = inflater.inflate(R.layout.fragment_newproject_text, container, false);
        setComponents();
        setListeners();
        return view;
    }

    private void setListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (verifyFields()) setFilled(true);
                else setFilled(false);

                savePart();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        titleEditText.addTextChangedListener(textWatcher);
        descEditText.addTextChangedListener(textWatcher);
        startEditText.addTextChangedListener(textWatcher);
        endEditText.addTextChangedListener(textWatcher);
    }

    private boolean verifyFields() {
        return titleEditText.getText().toString().trim().length() > 0 &&
                descEditText.getText().toString().trim().length() > 0 &&
                startEditText.getText().toString().trim().length() > 0 &&
                endEditText.getText().toString().trim().length() > 0;
    }

    @Override
    public void setComponents() {
        super.setComponents();
        titleEditText = view.findViewById(R.id.project_title);
        descEditText = view.findViewById(R.id.project_description);
        startEditText = view.findViewById(R.id.project_startDate);
        endEditText = view.findViewById(R.id.project_endDate);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        DatePickerDialogHelper.setDatePickerDialog(startEditText, context, sdf);
        DatePickerDialogHelper.setDatePickerDialog(endEditText, context, sdf);
    }

    @Override
    public void savePart() {
        if (verifyFields()) {
            bundle.clear();
            bundle.putString("TITLE", titleEditText.getText().toString());
            bundle.putString("DESC", descEditText.getText().toString());
            bundle.putString("START", startEditText.getText().toString());
            bundle.putString("END", endEditText.getText().toString());
            listener.onPartFilled(1, bundle);
        }
    }
}