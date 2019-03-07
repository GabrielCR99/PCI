package com.studio.pci.Models;

import java.util.Date;
import java.util.Objects;

public class Meeting {

    private int id;
    private int idProject;
    private String name;
    private Date startDate;
    private Date time;
    private Date duration;
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return getId() == meeting.getId() &&
                getIdProject() == meeting.getIdProject() &&
                isEnable() == meeting.isEnable() &&
                Objects.equals(getName(), meeting.getName()) &&
                Objects.equals(getStartDate(), meeting.getStartDate()) &&
                Objects.equals(getTime(), meeting.getTime()) &&
                Objects.equals(getDuration(), meeting.getDuration());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getIdProject(), getName(), getStartDate(), getTime(), getDuration(), isEnable());
    }

    public Meeting(int idProject, String name, Date startDate, Date time, Date duration, boolean enable) {
        this.idProject = idProject;
        this.name = name;
        this.startDate = startDate;
        this.time = time;
        this.duration = duration;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
