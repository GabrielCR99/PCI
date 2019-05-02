package com.studio.pci.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pci.R;
import com.studio.pci.adapters.SelectedUniversitiesAdapter;
import com.studio.pci.adapters.UniversitiesAdapter;
import com.studio.pci.models.University;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.studio.pci.R.drawable.ic_chevron_right_black_24dp;

public class CreateActivity extends AppCompatActivity implements UniversitiesAdapter.RecyclerViewClickListener,SelectedUniversitiesAdapter.RecyclerViewClickListener{

    private List<University> universities;
    private UniversitiesAdapter adapter;

    private List<University> filteredUniversities;
    private SelectedUniversitiesAdapter filteredAdapter;


    @BindView(R.id.recycler_universities)
    RecyclerView recyclerUniversities;

    @BindView(R.id.recycler_selected_universities)
    RecyclerView recyclerSelected;

    @BindView(R.id.flipper_project)
    ViewFlipper viewFlipper;

    @BindView(R.id.linear_create_part1)
    LinearLayout linearLayout1;

    @BindView(R.id.linear_create_part5)
    LinearLayout linearLayout5;

    @BindView(R.id.fab_next)
    FloatingActionButton fabNext;

    @BindView(R.id.fab_previous)
    FloatingActionButton fabPrevious;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_project_create);
       ButterKnife.bind(this);

       checkCurrentView();

       setUniversities();
       setRecyclerView();

    }

    @OnClick(R.id.fab_previous)
    public void previousView(){
        viewFlipper.setInAnimation(this,R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this,R.anim.slide_out_left);
        viewFlipper.showPrevious();
        checkCurrentView();
    }

    @OnClick(R.id.fab_next)
    public void nextView(){
        viewFlipper.setInAnimation(this,R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this,R.anim.slide_out_left);
        checkCurrentView();
        if(checkCurrentView()){
            viewFlipper.showNext();
            checkCurrentView();
        }else{
            // TODO CREATE PROJECT
        }
    }

    public boolean checkCurrentView(){
        if(viewFlipper.getCurrentView().equals(linearLayout1)){
            fabPrevious.hide();
            fabNext.setImageDrawable(getResources().getDrawable(ic_chevron_right_black_24dp));
            return true;
        }
        if (viewFlipper.getCurrentView().equals(linearLayout5)){
            fabPrevious.show();
            fabNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_send_black_24dp));
            return false;
        }
        fabPrevious.show();
        fabNext.setImageDrawable(getResources().getDrawable(ic_chevron_right_black_24dp));
        return true;
    }

    private void setUniversities(){
        universities = new ArrayList<>();
        filteredUniversities = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("universities");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                universities.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    University university = ds.getValue(University.class);
                    universities.add(university);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DATABASE_UNIVERSITY",databaseError.getMessage());
            }
        });
    }

    private void setRecyclerView() {
        recyclerUniversities.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UniversitiesAdapter(universities,this,this);
        recyclerUniversities.setAdapter(adapter);

        recyclerSelected.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        filteredAdapter = new SelectedUniversitiesAdapter(filteredUniversities,this,this);
        recyclerSelected.setAdapter(filteredAdapter);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        filteredUniversities.add(universities.get(position));
        filteredAdapter.notifyDataSetChanged();
    }

    @Override
    public void recyclerRoundItemClicked(View v, int position) {
        filteredUniversities.remove(position);
        filteredAdapter.notifyDataSetChanged();
    }
}