package com.netcracker.edu.odelivery.database.manager;

import com.netcracker.edu.odelivery.model.Client;
import com.netcracker.edu.odelivery.model.Entity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface EntityManager<T> {
    void save(T object);
    List<T> getEntityListBySql(String sqlQuery,Class clazz);
    List<T> getEntityListByPage(Integer from, Integer limit, Class clazz);
    <T extends Entity>T getEntityById(Long id, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;
    List<Entity> getAllObjects();
    void delete(T object);
}
