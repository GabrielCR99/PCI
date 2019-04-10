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
    private boolean finished;
    private boolean enable;

    public Project(String id, String title, String description, String startDate, String endDate, boolean finished, boolean enable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finished = finished;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return isFinished() == project.isFinished() &&
                isEnable() == project.isEnable() &&
                Objects.equals(getId(), project.getId()) &&
                Objects.equals(getTitle(), project.getTitle()) &&
                Objects.equals(getDescription(), project.getDescription()) &&
                Objects.equals(getStartDate(), project.getStartDate()) &&
                Objects.equals(getEndDate(), project.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getStartDate(), getEndDate(), isFinished(), isEnable());
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}