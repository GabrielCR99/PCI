package com.studio.pci.models;

import java.util.Objects;

public class Skill {

    private String id;
    private String name;
    private boolean enable;

    public Skill(){}

    public Skill(String id, String name, boolean enable) {
        this.id = id;
        this.name = name;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;
        Skill skill = (Skill) o;
        return id == skill.id &&
                enable == skill.enable &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, enable);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
