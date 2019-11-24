package com.netcracker.edu.odelivery.database.manager.impl;

import com.netcracker.edu.odelivery.database.annotation.*;
import com.netcracker.edu.odelivery.database.manager.EntityManager;
import com.netcracker.edu.odelivery.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class RequestBuilder<T> implements EntityManager<T> {
    private static final Logger log = LoggerFactory.getLogger(RequestBuilder.class);
    @Autowired
    JdbcTemplate jdbcTemplate;


    public void save(T object) {
        if (object != null) {
            Class<?> clazz = object.getClass();
            if (clazz.isAnnotationPresent(ObjectType.class)) {
                try {
                    ObjectType annotation = clazz.getAnnotation(ObjectType.class);
                    int objectTypeId = annotation.objType();
                    Field fieldID = getIdOrNameField(clazz, Id.class); //Получаем поле с аннотацией @Id
                    Field fieldName = getIdOrNameField(clazz, Name.class); //Получаем поле с аннотацией @Name
                    fieldID.setAccessible(true);
                    Long objectId = (Long) fieldID.get(object);

                    if (objectId == null) { // Проверяем есть ли Id, то есть запрос это на обновление или создание
                        fieldName.setAccessible(true);
                        objectId = insertObject((String) fieldName.get(object), objectTypeId); // Делаем запись в базу
                        fieldName.setAccessible(false);
                    }

                    Set<Field> fieldsAttribute = getFieldsRecursion(new HashSet<>(), clazz); //Получаем все поля для нашего Object
                    final String sqlMerge = "MERGE INTO ATTRIBUTES USING DUAL ON ( ATTR_ID=? AND OBJECT_ID=?) WHEN MATCHED THEN UPDATE SET VALUE=?,DATE_VALUE=?,LIST_VALUE_ID=? WHEN NOT MATCHED THEN INSERT VALUES ( ?,?,?,?,? )";

                    for (Field field : fieldsAttribute) { //Перебираем атрибуты и в зависимости от аннотации мапим
                        if (field.isAnnotationPresent(Attribute.class)) {
                            mapAttributes(field, sqlMerge, objectId, object);
                        }
                        if (field.isAnnotationPresent(AttributeList.class)) {
                            mapAttributesList(field, sqlMerge, objectId, object);
                        }
                        if (field.isAnnotationPresent(Reference.class)) {
                            mapReference(field, fieldID, objectId, object);
                        }
                    }
                    jdbcTemplate.execute("COMMIT "); //делаем коммит
                } catch (IllegalAccessException e) {
                    log.error("Exception by  access to field",e);
                }
            }
        }

    }


    public List<T> getEntityListBySql(String sqlQuery, Class clazz) {
        return null;
    }


    public List<T> getEntityListByPage(Integer from, Integer limit, Class clazz) {
        return null;
    }

    // initial implementation of dynamic query
    public StringBuilder createQuery(String where) {
        StringBuilder query = new StringBuilder("SELECT O.OBJECT_ID, O.PARENT_ID, O.OBJECT_TYPE_ID, O.NAME, O.DESCRIPTION ");
        // select from other tables (attributes/obj_references)
        List values = new ArrayList<Long>(Arrays.asList(105L, 106L, 200L, 201L));
        List date_values = new ArrayList<Long>(Arrays.asList(108L, 109L));
        List list_values = new ArrayList<Long>(Arrays.asList(104L));
        List references = new ArrayList<Long>(Arrays.asList(120L, 122L, 140L, 150L, 174L));
        selectFromTables(query, values, date_values, list_values, references );
        query.append(" FROM OBJECTS O");
        // then join all tables
        joinTables(query, values, date_values, list_values, references);
        query.append(" ").append(where);
        return query;
    }

    public void joinTables(StringBuilder query, List<Long> values, List<Long> date_values, List<Long> list_values, List<Long> references) {
        joinAttributes(query, values, "ATTRIBUTES");
        joinAttributes(query, date_values, "ATTRIBUTES");
        joinAttributes(query, list_values, "ATTRIBUTES");
        joinAttributes(query, references, "OBJ_REFERENCE");
    }

    public StringBuilder joinAttributes(StringBuilder query, List<Long> attributes, String table) {
        int aliasIndex = 1;
        for (Long attribute : attributes) {
            String alias = "A" + aliasIndex;
            query.append("\n LEFT JOIN ")
                    .append(table)
                    .append(" ")
                    .append(alias)
                    .append(" ON ")
                    .append(alias)
                    .append(".ATTR_ID = ")
                    .append(attribute)
                    .append(" AND ")
                    .append(alias)
                    .append(".OBJECT_ID = O.OBJECT_ID");
            aliasIndex++;
        }
        return query;
    }

    // need a list of constants-codes (from classAttributes interface)
    public void selectFromTables (StringBuilder query, List<Long> values, List<Long> date_values,  List<Long> list_values, List<Long> references) {
        // select all attributes values
        selectAttributes(query, values, "VALUE");
        // select all attributes date_values
        selectAttributes(query, date_values, "DATE_VALUE");
        // select all attributes list_values
        selectAttributes(query, list_values, "LIST_VALUE_ID");
        // select all references
        selectAttributes(query, references, "REFERENCE");
    }

    public void selectAttributes(StringBuilder query, List<Long> attributes, String column) {
        int aliasIndex = 1;
        for (Long attribute : attributes) {
            query.append("\n,A")
                 .append(aliasIndex)
                 .append(".")
                 .append(column)
                 .append(" ATTR_ID_")
                 .append(attribute)
                 .append(" ");
        }
    }


    // example of select from one table
    @Autowired
    public List<Entity> getAllObjects() {
        String sql = "SELECT OBJECT_ID, NAME FROM OBJECTS ";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Entity>>() {
            @Override
            public List<Entity> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List entitiesList = new ArrayList<Entity>();
                while (resultSet.next()) {
                    Entity entity = new Entity();
                    entity.setId(resultSet.getLong("OBJECT_ID"));
                    entity.setName(resultSet.getString("NAME"));
                    entitiesList.add(entity);
                }
                return entitiesList;
            }
        });
    }

    private <T extends Entity> T createNewEntity(Class<T> clazz) {
        try {
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException  e) {
            log.error("Unable to create instance of clazz", e);
        }
        return null;
    }


    public <T extends Entity> T getEntityById(Long id, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T entity = createNewEntity(clazz);
        if (entity == null) {
            return null;
        }
        return entity;
    }

    public void delete(T object) {

    }

    //С помощью этого метода получаем все поля для объекта Class, он рекурсивен
    private Set<Field> getFieldsRecursion(Set<Field> fields, Class<?> clazz) {
        if (!clazz.equals(Object.class)) {
            Field[] fields1 = clazz.getDeclaredFields();
            Collections.addAll(fields, fields1);
            getFieldsRecursion(fields, clazz.getSuperclass());
        }
        return fields;
    }

    //Метод получает два ключевых поля @Name и @Id, тоже рекурсивно
    private Field getIdOrNameField(Class<?> clazz, Class<? extends Annotation> annotation) {
        Field fieldID = null;
        if (!clazz.equals(Entity.class)) {
            fieldID = getIdOrNameField(clazz.getSuperclass(), annotation);
        } else {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(annotation)) {
                    fieldID = field;
                    return fieldID;
                }
            }
        }
        return fieldID;
    }

    //Метод для вноса объекта в Objects и получения обратно этого id
    private Long insertObject(String name, Integer idType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO OBJECTS VALUES (OBJECT_ID_INCREMENT.nextval,NULL,?,?,NULL)", new String[]{"OBJECT_ID"});
            ps.setInt(1, idType);
            ps.setString(2, name);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    //Метод для маппинга атрибутов
    private void mapAttributes(Field field, String sqlMerge, Long objectId, T object) throws IllegalAccessException {
        field.setAccessible(true);
        Attribute annotationAttribute = field.getAnnotation(Attribute.class);
        int attributeId = annotationAttribute.attrId();
        if (Date.class.isAssignableFrom(field.getType())) {
            jdbcTemplate.update(sqlMerge, attributeId, objectId, null, field.get(object), null,
                    attributeId, objectId, null, field.get(object), null);

        } else {
            jdbcTemplate.update(sqlMerge, attributeId, objectId, field.get(object), null, null,
                    attributeId, objectId, field.get(object), null, null);
        }
        field.setAccessible(false);
    }

    //Метод для маппинга атрибутов листов
    private void mapAttributesList(Field field, String sqlMerge, Long objectId, T object) throws IllegalAccessException {
        field.setAccessible(true);
        AttributeList annotationAttribute = field.getAnnotation(AttributeList.class);
        int attributeId = annotationAttribute.attrId();
        //Получим значение для листа по атрибуту и значению
        Long listValueID = jdbcTemplate.queryForObject("SELECT LIST_VALUE_ID FROM LISTS WHERE ATTR_ID=? AND VALUE=?", new Object[]{attributeId, field.get(object)}, Long.class);
        jdbcTemplate.update(sqlMerge, attributeId, objectId, null, null, listValueID,
                attributeId, objectId, null, null, listValueID);
        field.setAccessible(false);
    }

    //Метод для маппинга референсов
    private void mapReference(Field field, Field fieldID, Long objectId, T object) throws IllegalAccessException {
        Reference annotationAttribute = field.getAnnotation(Reference.class);
        int attributeId = annotationAttribute.attrId();
        field.setAccessible(true);
        if (field.get(object) != null) { //Проверям есть ли референс
            if (Collection.class.isAssignableFrom(field.getType())) { // Если это коллекция то удаляем прошлие записи и заносим новые
                Collection collection = (Collection) field.get(object);
                jdbcTemplate.update("DELETE FROM OBJ_REFERENCE WHERE ATTR_ID=? AND OBJECT_ID=? ", attributeId, objectId);
                for (Object object1 : collection) {
                    Long reference = (Long) fieldID.get(object1);
                    jdbcTemplate.update("INSERT INTO OBJ_REFERENCE VALUES (?,?,?)", attributeId, reference, objectId);
                }
            } else { //Если не коллекция то делаем Merge
                Long reference = (Long) fieldID.get(field.get(object));
                jdbcTemplate.update("MERGE INTO OBJ_REFERENCE USING DUAL ON ( ATTR_ID=? AND OBJECT_ID=?) WHEN MATCHED THEN UPDATE SET REFERENCE=? WHEN NOT MATCHED THEN INSERT VALUES ( ?,?,?)",
                        attributeId, objectId, reference, attributeId, reference, objectId);
            }
        }
        field.setAccessible(false);
        fieldID.setAccessible(false);
    }
}



