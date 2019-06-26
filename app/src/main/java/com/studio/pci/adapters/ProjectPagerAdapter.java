package com.studio.pci.adapters;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.studio.pci.fragments.projectFragments.CustomFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectPagerAdapter extends FragmentPagerAdapter {
    private List<CustomFragment> fragmentList = new ArrayList<>();

    public ProjectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CustomFragment getItem(int position) { return fragmentList.get(position); }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(CustomFragment fragment) {
        fragmentList.add(fragment);
    }

}
