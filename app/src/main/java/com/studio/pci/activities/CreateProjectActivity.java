package com.studio.pci.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.studio.pci.R;
import com.studio.pci.adapters.ProjectPagerAdapter;
import com.studio.pci.fragments.projectFragments.CustomFragment;
import com.studio.pci.fragments.projectFragments.FifthPartFragment;
import com.studio.pci.fragments.projectFragments.FirstPartFragment;
import com.studio.pci.fragments.projectFragments.FourthPartFragment;
import com.studio.pci.fragments.projectFragments.SecondPartFragment;
import com.studio.pci.fragments.projectFragments.ThirdPartFragment;
import com.studio.pci.models.Project;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.chenupt.springindicator.SpringIndicator;

public class CreateProjectActivity extends AppCompatActivity implements CustomFragment.NewProjectListener {

    @BindView(R.id.spring_indicator)
    SpringIndicator indicator;

    @BindView(R.id.newproject_view_pager)
    ViewPager viewPager;

    @BindView(R.id.fab_next)
    ImageView buttonNext;

    @BindView(R.id.fab_previous)
    ImageView buttonPrevious;

    @BindView(R.id.toolbar_project)
    Toolbar toolbar;

    private Project project;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);
        ButterKnife.bind(this);

        setComponents();
        setClicks();
    }

    private void setClicks() {
        buttonNext.setOnClickListener(v -> checkItemPosition(true));
        buttonPrevious.setOnClickListener(v -> checkItemPosition(false));
        indicator.setOnTabClickListener(position -> {
            viewPager.setCurrentItem(position);
            setButtonVisibility();
            return false;
        });
    }

    private void setComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        project = new Project();
        ProjectPagerAdapter adapter = new ProjectPagerAdapter(getSupportFragmentManager());

        FirstPartFragment firstPartFragment = new FirstPartFragment(this, this);
        SecondPartFragment secondPartFragment = new SecondPartFragment(this, this);
        ThirdPartFragment thirdPartFragment = new ThirdPartFragment(this, this);
        FourthPartFragment fourthPartFragment = new FourthPartFragment(this, this);
        FifthPartFragment fifthPartFragment = new FifthPartFragment(this, this);

        adapter.addFragment(firstPartFragment);
        adapter.addFragment(secondPartFragment);
        adapter.addFragment(thirdPartFragment);
        adapter.addFragment(fourthPartFragment);
        adapter.addFragment(fifthPartFragment);

        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        for (int i = 0; i < 5; i++)
            if (indicator.getTabs().get(i) != null)
                indicator.getTabs().get(i).setText(String.valueOf(i + 1));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void checkItemPosition(boolean dir) {
        if (viewPager.getCurrentItem() > 0 || viewPager.getCurrentItem() < 5) {
            if (dir) viewPager.setCurrentItem(getItem(+1), true);
            else viewPager.setCurrentItem(getItem(-1), true);
            setButtonVisibility();
        }
    }

    private void setButtonVisibility() {
        if (viewPager.getCurrentItem() == 0) {
            buttonPrevious.setVisibility(View.INVISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
        } else if (viewPager.getCurrentItem() == 4) {
            buttonNext.setVisibility(View.INVISIBLE);
            buttonPrevious.setVisibility(View.VISIBLE);
        } else {
            buttonPrevious.setVisibility(View.VISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPartFilled(int part, Bundle bundle) {

    }
}