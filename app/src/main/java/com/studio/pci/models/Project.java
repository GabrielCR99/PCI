package com.studio.pci.models;

<<<<<<< HEAD
import java.util.Date;
import java.util.Objects;

public class Project {
=======
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Project implements Serializable {
>>>>>>> Paulo

    private String id;
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

<<<<<<< HEAD
=======
    public Project(){}

>>>>>>> Paulo
    public Project(String id, String title, String description, Date startDate, Date endDate, boolean enable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enable = enable;
    }

<<<<<<< HEAD
    public Project(){}

=======
>>>>>>> Paulo
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

<<<<<<< HEAD
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

=======
>>>>>>> Paulo
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