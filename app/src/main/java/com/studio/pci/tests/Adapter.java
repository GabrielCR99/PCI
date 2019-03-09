package com.studio.pci.tests;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.paulo.vem.R;
import com.example.paulo.vem.models.University;

import java.util.List;

public class Adapter extends ArrayAdapter<University> {

    private Activity context;
    private List<University> universities;

    public Adapter(Activity context, List<University> universities) {
        super(context, R.layout.list_layout,universities);
        this.context = context;
        this.universities = universities;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.list_layout,null,true);
        TextView nameTextView = view.findViewById(R.id.artist_name);
        TextView genreTextView = view.findViewById(R.id.artist_genre);
        University university = universities.get(position);
        nameTextView.setText(university.getName());
        genreTextView.setText(university.getDepartment());
        return view;
    }
}
