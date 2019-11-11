package com.netcracker.edu.odelivery.database.manager.impl;

import com.netcracker.edu.odelivery.database.annotation.*;
import com.netcracker.edu.odelivery.database.manager.EntityManager;
import com.netcracker.edu.odelivery.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;


import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.*;

@Repository
public class RequestBuilder<T> implements EntityManager<T> {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public void save(T object) {

        if(object!=null){
            Class<?> clazz =object.getClass();
            if(clazz.isAnnotationPresent(ObjProperties.class)){
                ObjProperties annotation=clazz.getAnnotation(ObjProperties.class);
                int objectTypeId=annotation.objType();
                Field fieldID=getIdField(clazz);
                fieldID.setAccessible(true);
                Long objectId = null;
                try {
                    objectId=(Long) fieldID.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(objectId==null){
                    System.out.println("Тут insert в БД в objects и получаем по sequence ID "+objectTypeId);
                    objectId=1L;
                }
                Set<Field> fieldsAttribute=getFieldsRecursion(new HashSet<>(),clazz);
                for (Field field : fieldsAttribute){
                    try {
                        if(field.isAnnotationPresent(Attribute.class)) {
                            field.setAccessible(true);
                            Attribute annotationAttribute=field.getAnnotation(Attribute.class);
                            int attributeId=annotationAttribute.attrId();
                            if(Date.class.isAssignableFrom(field.getType())){
                                System.out.println("Merge и данные "+attributeId+" "+objectId+" date="+  field.get(object));
                            }
                            else {
                                System.out.println("Merge и данные "+attributeId+" "+objectId+" value="+  field.get(object));
                            }

                            System.out.println();
                        }
                        if(field.isAnnotationPresent(AttList.class)){
                            System.out.println("Записываем по листу предварительно взяв номер");
                        }
                        if(field.isAnnotationPresent(AttReference.class)){
                            AttReference annotationAttribute=field.getAnnotation(AttReference.class);
                            int attributeId=annotationAttribute.attrId();
                            field.setAccessible(true);
                            if(field.get(object)!=null) {
                                if (Collection.class.isAssignableFrom(field.getType())) {
                                    Collection collection = (Collection) field.get(object);

                                    for (Object object1 : collection) {
                                        Long objectId1 = (Long) fieldID.get(object1);
                                        System.out.println("Заносим в референсы коллекцию " + attributeId + " " + objectId + " " + objectId1);
                                    }
                                } else {
                                    Long objectId1 = (Long) fieldID.get(field.get(object));
                                    System.out.println("Заносим в референсы " + attributeId + " " + objectId + " " + objectId1);
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Коммит");
            }
        }

    }


    public List<T> getEntityListBySql(String sqlQuery) {
        return null;
    }


    public List<T> getEntityListByPage(Integer from, Integer limit, Class clazz) {
        return null;
    }


    public T gerEntityById(Integer id, Class clazz) {
        return null;
    }


    public void delete(T object) {

    }
    private Set<Field> getFieldsRecursion(Set<Field> fields,Class<?> clazz){
        if(!clazz.equals(Object.class)){
            Field[] fields1=clazz.getDeclaredFields();
            Collections.addAll(fields, fields1);
            getFieldsRecursion(fields,clazz.getSuperclass());
        }
        return fields;
    }
    private Field getIdField(Class<?> clazz){
        Field fieldID=null;
        if(!clazz.equals(Entity.class)){
            fieldID= getIdField(clazz.getSuperclass());
        }
        else {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    fieldID = field;

                    return fieldID;
                }
            }
        }
        return fieldID;
    }
}
