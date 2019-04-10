package com.studio.pci.models;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Project implements Serializable {

    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return getId().equals(project.getId()) &&
                isEnable() == project.isEnable() &&
                Objects.equals(getTitle(), project.getTitle()) &&
                Objects.equals(getDescription(), project.getDescription()) &&
                Objects.equals(startDate, project.startDate) &&
                Objects.equals(getEndDate(), project.getEndDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getTitle(), getDescription(), startDate, getEndDate(), isEnable());
    }

    public Project(){}

    public Project(String id, String title, String description, String startDate, String endDate, boolean enable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enable = enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}