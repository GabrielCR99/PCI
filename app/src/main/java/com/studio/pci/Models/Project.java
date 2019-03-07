package com.studio.pci.Models;

import java.util.Date;
import java.util.Objects;

public class Project {

    private int id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return getId() == project.getId() &&
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

    public Project(int id, String title, String description, Date startDate, Date endDate, boolean enable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enable = enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getStart_Date() {
        return startDate;
    }

    public void setStart_Date(Date start_Date) {
        this.startDate = start_Date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
