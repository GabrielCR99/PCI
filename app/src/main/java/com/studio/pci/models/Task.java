package com.studio.pci.models;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Task implements Serializable {

    private String id;
    private String title;
    private String description;
    private String endDate;
    private boolean done;
    private boolean enable;

    public Task(){}

    public Task(String id, String title, String description, String endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.done = false;
        this.enable = true;
    }

    public Task(String id, String title, String description, String endDate, boolean done, boolean enable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.done = done;
        this.enable = enable;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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