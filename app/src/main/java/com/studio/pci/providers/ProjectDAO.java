package com.studio.pci.providers;

import com.studio.pci.models.Project;

public class ProjectDAO extends AbstractDAO<Project> {
    public ProjectDAO() {
        super("projects");
    }
}
