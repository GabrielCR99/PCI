package com.studio.pci.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.studio.pci.R;
import com.studio.pci.models.University;
import com.studio.pci.providers.UniversityDAO;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        /*University university = new University();
        UniversityDAO universityDAO = new UniversityDAO();
        String id = universityDAO.newKey();
        university.setId(id);
        university.setName("Universidade de Piracicaba");
        university.setState("São Paulo");
        university.setDepartment("Desenvolvimento");
        university.setEnable(true);
        university.setCountry("Brazil");
        university.setPicture("teste1.png");
        universityDAO.create(id,university);*/

    }
}
