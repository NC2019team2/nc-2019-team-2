package com.netcracker.edu.odelivery.database.manager.impl;

import com.netcracker.edu.odelivery.database.manager.EntityManager;

import java.util.List;

public class RequestBuilder<T> implements EntityManager<T> {
    @Override
    public void save(T object) {
        /*
        Создание нового объекта если isNewObject=true

        Передаём в этот метод объект, проверяем есть ли у него аннотация @ObjProperties
         если да то получаем все методы get и вызываем их и получаем уже доступ к аннотации AttrProperties.
          Далее имея уже эту нужную инфу создаём два запроса в БД а именно objects и attributes


         */


    }

    @Override
    public List<T> getEntityListBySql(String sqlQuery) {
        return null;
    }

    @Override
    public List<T> getEntityListByPage(Integer from, Integer limit, Class clazz) {
        return null;
    }

    @Override
    public T gerEntityById(Integer id, Class clazz) {
        return null;
    }

    @Override
    public void delete(T object) {

    }
}
