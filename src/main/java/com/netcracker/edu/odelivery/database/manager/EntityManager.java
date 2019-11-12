package com.netcracker.edu.odelivery.database.manager;

import java.util.List;

public interface EntityManager<T> {
    void save(T object);
    List<T> getEntityListBySql(String sqlQuery,Class clazz);
    List<T> getEntityListByPage(Integer from, Integer limit, Class clazz);
    T gerEntityById(Long id, Class clazz);
    void delete(T object);
}
