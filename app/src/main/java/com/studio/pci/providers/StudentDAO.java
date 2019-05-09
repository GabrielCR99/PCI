package com.studio.pci.providers;

import com.studio.pci.models.Student;

public class StudentDAO extends AbstractDAO<Student> {

    public StudentDAO() {
        super("students");
    }
}
