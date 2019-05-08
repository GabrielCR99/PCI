package com.studio.pci.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.pci.R;
import com.studio.pci.activities.CreateProjectActivity;
import com.studio.pci.adapters.ViewPagerAdapter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectListFragment extends Fragment {

    @BindView(R.id.tab_layout_main)
    TabLayout tabLayout;

    @BindView(R.id.projects_view_pager)
    ViewPager viewPager;

    @BindView(R.id.fab_Add)
    FloatingActionButton fab;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Context context1 = context;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects_list, container, false);
        ButterKnife.bind(this, view);

        Bundle b = getArguments();
        int type = Objects.requireNonNull(b).getInt("TYPE");

        if(type==1) fab.hide();

        FinishedProjectFragment finishedProjects = new FinishedProjectFragment();
        InProgressProjectFragment inProgressProjects = new InProgressProjectFragment();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addFragment(inProgressProjects, getString(R.string.projects_in_progress));
        viewPagerAdapter.addFragment(finishedProjects, getString(R.string.finished));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CreateProjectActivity.class));
            }
        });

        return view;
    }
}

