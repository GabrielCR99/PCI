package com.studio.pci.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.pci.R;
import com.studio.pci.adapters.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectListFragment extends Fragment {

    @BindView(R.id.tab_layout_main)
    TabLayout tabLayout;

    @BindView(R.id.projects_view_pager)
    ViewPager viewPager;

    @BindView(R.id.add_project_button)
    FloatingActionButton fab;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects_list, container, false);
        ButterKnife.bind(this, view);

        Bundle b = getArguments();
        int type = b.getInt("TYPE");

        if(type==1) fab.hide();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addFragment(new InProgressProjectFragment(), getString(R.string.projects_in_progress));
        viewPagerAdapter.addFragment(new FinishedProjectFragment(), getString(R.string.finished));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
