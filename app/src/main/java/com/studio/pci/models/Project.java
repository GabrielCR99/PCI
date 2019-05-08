package com.studio.pci.models;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Project implements Serializable {

    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private ArrayList<String> universities;
    private ArrayList<String> students;
    private ArrayList<String> professors;
    private ArrayList<String> phases;
    private boolean finished;
    private boolean enable;

    public Project(){}

    public Project(String id, String title, String description, String startDate, String endDate, ArrayList<String> universities, ArrayList<String> students, ArrayList<String> professors, ArrayList<String> phases, boolean finished) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.universities = universities;
        this.students = students;
        this.professors = professors;
        this.phases = phases;
        this.finished = finished;
        this.enable = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<String> getUniversities() {
        return universities;
    }

    public void setUniversities(ArrayList<String> universities) {
        this.universities = universities;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<String> getProfessors() {
        return professors;
    }

    public void setProfessors(ArrayList<String> professors) {
        this.professors = professors;
    }

    public ArrayList<String> getPhases() {
        return phases;
    }

    public void setPhases(ArrayList<String> phases) {
        this.phases = phases;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}