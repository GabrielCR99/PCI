package com.studio.pci.Models;

import java.util.Objects;

public class Program {

    private int id;
    private String name;
    private String description;
    private String type;
    private boolean enable;

    public Program(int id, String name, String description, String type, boolean enable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Program)) return false;
        Program program = (Program) o;
        return id == program.id &&
                enable == program.enable &&
                Objects.equals(name, program.name) &&
                Objects.equals(description, program.description) &&
                Objects.equals(type, program.type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, type, enable);
    }
}
