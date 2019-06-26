package com.studio.pci.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.studio.pci.R;
import com.studio.pci.adapters.ProjectPagerAdapter;
import com.studio.pci.fragments.projectFragments.CustomFragment;
import com.studio.pci.fragments.projectFragments.FifthPartFragment;
import com.studio.pci.fragments.projectFragments.FirstPartFragment;
import com.studio.pci.fragments.projectFragments.FourthPartFragment;
import com.studio.pci.fragments.projectFragments.SecondPartFragment;
import com.studio.pci.fragments.projectFragments.ThirdPartFragment;
import com.studio.pci.models.Project;
import com.studio.pci.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.chenupt.springindicator.SpringIndicator;

public class CreateProjectActivity extends AppCompatActivity implements CustomFragment.NewProjectListener {

    @BindView(R.id.spring_indicator)
    SpringIndicator indicator;

    @BindView(R.id.newproject_view_pager)
    CustomViewPager viewPager;

    @BindView(R.id.toolbar_project)
    Toolbar toolbar;

    private Project project;
    private ProjectPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);
        ButterKnife.bind(this);

        setComponents();
        setClick();
    }

    private void setClick() {
        indicator.setOnTabClickListener(position -> {
            checkItemPosition(position > viewPager.getCurrentItem());
            return false;
        });
    }

    private void setComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        project = new Project();
        adapter = new ProjectPagerAdapter(getSupportFragmentManager());

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

        viewPager.setPagingEnabled(false);
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
            if (dir) {
                if (viewPager.getCurrentItem() == 3) {
                    viewPager.setCurrentItem(getItem(+1));
                    return;
                }

                if (adapter.getItem(getItem(0)).isFilled()) {
                    viewPager.setCurrentItem(getItem(+1));
                } else {
                    Toast.makeText(this,
                            getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
                }
            } else viewPager.setCurrentItem(getItem(-1), true);
        }
    }

    @Override
    public void onPartFilled(int part, Bundle bundle) {
        switch (part) {
            case 1:
                project.setTitle(bundle.getString("TITLE"));
                project.setDescription(bundle.getString("DESC"));
                project.setStartDate(bundle.getString("START"));
                project.setEndDate(bundle.getString("END"));
                break;
            case 2:
                project.setUniversities(bundle.getStringArrayList("UNIVERSITIES"));
                break;
            case 3:
                project.setProfessors(bundle.getStringArrayList("PROFESSORS"));
                break;
            case 4:

                break;
            case 5:

                break;
            default:
                Log.e("ERROR_PART_FILLED", "PART INT DOESNT EXIST");
                break;
        }
    }

}