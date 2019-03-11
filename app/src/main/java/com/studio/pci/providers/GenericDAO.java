package com.studio.pci.providers;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T extends Serializable> {

    T findById(String id);

    List<T> findAll();

    void create(T entity);

    void update(String id,T entity);

    void delete(String id);

}