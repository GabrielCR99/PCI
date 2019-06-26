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
import com.studio.pci.adapters.ProfessorsListAdapter;
import com.studio.pci.listeners.RecyclerViewClickListener;
import com.studio.pci.models.Professor;
import com.studio.pci.models.Upload;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class ThirdPartFragment extends CustomFragment implements RecyclerViewClickListener {

    private ProfessorsListAdapter filteredAdapter;

    private List<Professor> professorList;
    private List<Upload> uploadList;

    private List<Professor> filteredProfessors;
    private List<Upload> filteredUploads;

    public ThirdPartFragment(NewProjectListener listener, Context context) {
        super(listener, context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newproject_list_selector, container, false);
        setProfessors();
        setComponents();
        return view;
    }

    private void setProfessors() {
        filteredProfessors = new ArrayList<>();
        professorList = new ArrayList<>();
        uploadList = new ArrayList<>();
        filteredUploads = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("professors").exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Professor professor = ds.getValue(Professor.class);
                        // TODO VERIFICAR SE PROFESSOR PERTENCE A UNIVERSIDADE SELECIONADA
                        Upload upload;
                        if (professor.getPicture() != null)
                            upload = dataSnapshot.child("profile_photo")
                                    .child(professor.getId()).getValue(Upload.class);
                        else upload = new Upload("null");

                        professorList.add(professor);
                        uploadList.add(upload);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DATABASE_PROFESSOR_ERRO", databaseError.getMessage());
            }
        });
    }

    @Override
    public void setComponents() {
        super.setComponents();
        TextView textView = view.findViewById(R.id.part_title);
        textView.setText(getString(R.string.professors));

        RecyclerView universityRecyclerView = view.findViewById(R.id.recycler_universities);
        RecyclerView filteredRecyclerView = view.findViewById(R.id.recycler_selected_universities);

        universityRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        filteredRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));

        ProfessorsListAdapter adapter = new ProfessorsListAdapter(professorList, uploadList,
                context, this, "FILTER");
        filteredAdapter = new ProfessorsListAdapter(filteredProfessors, filteredUploads,
                context, this, "REMOVE");

        universityRecyclerView.setAdapter(adapter);
        filteredRecyclerView.setAdapter(filteredAdapter);
    }

    @Override
    public void recyclerViewListClicked(View v, int position, String TAG) {
        if (TAG.equals("FILTER")) {
            if (filteredProfessors.contains(professorList.get(position))) {
                filteredProfessors.remove(professorList.get(position));
                filteredUploads.remove(uploadList.get(position));
            } else {
                filteredProfessors.add(professorList.get(position));
                filteredUploads.add(uploadList.get(position));
            }

        } else if (TAG.equals("REMOVE")) {
            filteredProfessors.remove(position);
            filteredUploads.remove(position);
        }

        filteredAdapter.notifyDataSetChanged();

        if (filteredProfessors.size() > 0)
            setFilled(true);
        else
            setFilled(false);

        savePart();
    }

    @Override
    public void savePart() {
        if (filteredProfessors.size() > 0) {

            bundle.clear();
            ArrayList<String> professors = new ArrayList<>();
            for (Professor professor : filteredProfessors)
                professors.add(professor.getId());

            bundle.putStringArrayList("PROFESSORS", professors);
            listener.onPartFilled(3, bundle);
        }
    }
}