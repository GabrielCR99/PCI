package com.studio.pci.models;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Phase implements Serializable {

    private String id;
    private String idProject;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean enable;

    public Phase(){}

    public Phase(String id, String idProject, String title, String description, Date startDate, Date endDate, boolean enable) {
        this.id = id;
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phase)) return false;
        Phase phase = (Phase) o;
        return id == phase.id &&
                idProject == phase.idProject &&
                enable == phase.enable &&
                Objects.equals(title, phase.title) &&
                Objects.equals(description, phase.description) &&
                Objects.equals(startDate, phase.startDate) &&
                Objects.equals(endDate, phase.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idProject, title, description, startDate, endDate, enable);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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