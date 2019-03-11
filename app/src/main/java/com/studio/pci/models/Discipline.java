package com.studio.pci.models;

<<<<<<< HEAD
import java.util.Objects;

public class Discipline {
=======
import java.io.Serializable;
import java.util.Objects;

public class Discipline implements Serializable {
>>>>>>> Paulo

    private String id;
    private String name;
    private String description;
    private boolean enable;

<<<<<<< HEAD
    public  Discipline(){}
=======
    public Discipline(){}
>>>>>>> Paulo

    public Discipline(String id, String name, String description, boolean enable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discipline)) return false;
        Discipline that = (Discipline) o;
        return id == that.id &&
                enable == that.enable &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, enable);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
