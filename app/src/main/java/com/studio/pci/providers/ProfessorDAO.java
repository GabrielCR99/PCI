package com.studio.pci.providers;

import com.studio.pci.models.Professor;

public class ProfessorDAO extends AbstractDAO<Professor> {
    public ProfessorDAO() {
        super("professors");
    }
}
