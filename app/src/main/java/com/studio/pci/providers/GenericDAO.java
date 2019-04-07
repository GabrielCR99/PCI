package com.studio.pci.providers;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T extends Serializable> {

    void create(String id,T entity);

    void update(String id,T entity);

    void delete(String id);

    String newKey();
}