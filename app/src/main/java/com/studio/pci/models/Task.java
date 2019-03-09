package com.studio.pci.models;

import java.util.Date;
import java.util.Objects;

public class Task {

    private String id;
    private int idPhase;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean done;
    private boolean enable;


    public Task(){}


    public Task(String id, int idPhase, String title, String description, Date startDate, Date endDate, boolean done, boolean enable) {
        this.id = id;
        this.idPhase = idPhase;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.done = done;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id &&
                idPhase == task.idPhase &&
                done == task.done &&
                enable == task.enable &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(startDate, task.startDate) &&
                Objects.equals(endDate, task.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idPhase, title, description, startDate, endDate, done, enable);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdPhase() {
        return idPhase;
    }

    public void setIdPhase(int idPhase) {
        this.idPhase = idPhase;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
