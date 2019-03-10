package com.studio.pci.providers;

import com.studio.pci.models.Task;

public class TaskDAO extends AbstractDAO<Task> {

    public TaskDAO() {
        super("tasks");
    }
}
