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
import android.widget.Toast;

import com.studio.pci.R;
import com.studio.pci.adapters.ViewPagerAdapter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectListFragment extends Fragment {

    @BindView(R.id.tab_layout_main)
    TabLayout tabLayout;

    @BindView(R.id.projects_view_pager)
    ViewPager viewPager;

    @BindView(R.id.fab_Add)
    FloatingActionButton fab;

    private Context context;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_projects_list,container,false);
        ButterKnife.bind(this,view);

        Bundle b = getArguments();
        int type = Objects.requireNonNull(b).getInt("TYPE");

        if(type==1) fab.hide();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addFragment(new InProgressProjectFragment(), getString(R.string.projects_in_progress));
        viewPagerAdapter.addFragment(new FinishedProjectFragment(), getString(R.string.finished));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Adicionar", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

