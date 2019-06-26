package com.studio.pci.fragments.projectFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.UniversitiesAdapter;
import com.studio.pci.listeners.RecyclerViewClickListener;
import com.studio.pci.models.University;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SecondPartFragment extends CustomFragment implements RecyclerViewClickListener {

    public SecondPartFragment(NewProjectListener listener, Context context) {
        super(listener, context);
    }

    private UniversitiesAdapter filteredAdapter;

    private List<University> universities;
    private List<University> filteredUniversities;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newproject_list_selector, container, false);
        setUniversities();
        setComponents();
        return view;
    }

    private void setUniversities() {
        universities = new ArrayList<>();
        filteredUniversities = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("universities");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                universities.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    University university = ds.getValue(University.class);
                    if (university.isEnable()) universities.add(university);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("ERROR_UNIVERSITY", databaseError.getMessage());
            }
        });
    }

    @Override
    public void setComponents() {
        super.setComponents();
        TextView textView = view.findViewById(R.id.part_title);
        textView.setText(getString(R.string.universities));

        RecyclerView universityRecyclerView = view.findViewById(R.id.recycler_universities);
        RecyclerView filteredRecyclerView = view.findViewById(R.id.recycler_selected_universities);

        universityRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        filteredRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));

        UniversitiesAdapter adapter = new UniversitiesAdapter(universities, context, this, "FILTER");
        filteredAdapter = new UniversitiesAdapter(filteredUniversities, context, this, "REMOVE");

        universityRecyclerView.setAdapter(adapter);
        filteredRecyclerView.setAdapter(filteredAdapter);
    }

    @Override
    public void recyclerViewListClicked(View v, int position, String TAG) {
        if (TAG.equals("FILTER")) {
            if (filteredUniversities.contains(universities.get(position)))
                filteredUniversities.remove(universities.get(position));
            else
                filteredUniversities.add(universities.get(position));

        } else if (TAG.equals("REMOVE"))
            filteredUniversities.remove(position);
        filteredAdapter.notifyDataSetChanged();

        if(filteredUniversities.size() > 0)
            setFilled(true);
        else
            setFilled(false);

        savePart();
    }

    @Override
    public void savePart() {
        if (filteredUniversities.size() > 0) {

            bundle.clear();
            ArrayList<String> universities = new ArrayList<>();
            for (University university: filteredUniversities)
                universities.add(university.getId());

            bundle.putStringArrayList("UNIVERSITIES",universities);
            listener.onPartFilled(2,bundle);
        }
    }
}