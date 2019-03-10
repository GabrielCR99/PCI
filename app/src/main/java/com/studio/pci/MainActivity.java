package com.studio.pci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.pci.models.Student;
import com.studio.pci.models.University;
import com.studio.pci.providers.StudentDAO;
import com.studio.pci.providers.UniversityDAO;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CREATE UNIVERSITY
        /*University university = new University();
        UniversityDAO universityDAO = new UniversityDAO();
        university.setName("University of São Paulo");
        university.setState("São Paulo");
        university.setCountry("Brazil");
        university.setDepartment("Searches");
        university.setPicture("teste.png");
        university.setEnable(true);
        universityDAO.create(university);*/

        //DELETE UNIVERSITY
        /*UniversityDAO universityDAO = new UniversityDAO();
        universityDAO.delete(ID DA UNIVERSIDADE);*/
    }
}
