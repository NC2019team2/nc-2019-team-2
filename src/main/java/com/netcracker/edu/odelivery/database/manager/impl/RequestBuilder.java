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
import java.sql.*;

import java.util.*;
import java.util.Date;

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
    public StringBuilder createQuery(String where, List<List<Integer>> resultList) {
        StringBuilder query = new StringBuilder("SELECT O.OBJECT_ID, O.PARENT_ID, O.OBJECT_TYPE_ID, O.NAME, O.DESCRIPTION ");

        int aliasIndex = 1; // common index during all selections and joinings
        List<Integer> values = resultList.get(0);
        List<Integer> date_values = resultList.get(1);
        List<Integer> list_values = resultList.get(2);
        List<Integer> references = resultList.get(3);

        // select from other tables (attributes/obj_references)
        selectFromTables(query, values, date_values, list_values, references, aliasIndex);
        query.append(" FROM OBJECTS O");
        // then join all tables
        joinTables(query, values, date_values, list_values, references, aliasIndex);
        query.append(" ").append(where);
        return query;
    }

    public void joinTables(StringBuilder query, List<Integer> values, List<Integer> date_values, List<Integer> list_values, List<Integer> references, int aliasIndex) {
        aliasIndex = joinAttributes(query, values, "ATTRIBUTES", aliasIndex);
        aliasIndex = joinAttributes(query, date_values, "ATTRIBUTES", aliasIndex);
        aliasIndex = joinAttributes(query, list_values, "ATTRIBUTES", aliasIndex);
        joinAttributes(query, references, "OBJ_REFERENCE", aliasIndex);
    }

    public int joinAttributes(StringBuilder query, List<Integer> attributes, String table, int aliasIndex) {
        for (Integer attribute : attributes) {
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
        return aliasIndex;
    }

    public void selectFromTables (StringBuilder query, List<Integer> values, List<Integer> date_values,  List<Integer> list_values, List<Integer> references, int aliasIndex) {
        // select all attributes values
        aliasIndex = selectAttributes(query, values, "VALUE", aliasIndex);
        // select all attributes date_values
        aliasIndex = selectAttributes(query, date_values, "DATE_VALUE", aliasIndex);
        // select all attributes list_values
        aliasIndex = selectAttributes(query, list_values, "LIST_VALUE_ID", aliasIndex);
        // select all references
        selectAttributes(query, references, "REFERENCE", aliasIndex);
    }

    public int selectAttributes(StringBuilder query, List<Integer> attributes, String column, int aliasIndex) {
        for (Integer attribute : attributes) {
            query.append("\n,A")
                 .append(aliasIndex++)
                 .append(".")
                 .append(column)
                 .append(" ATTR_ID_")
                 .append(attribute)
                 .append(" ");
        }
        return aliasIndex;
    }

    /*
    return a list of all attributes, which contains four lists: attributes, date values, list values, references
    every nested list is sorted
    */
    public List<List<Integer>> getListOfAttributes (Class clazz) {
        Set<Field> fieldsOfClass = getFieldsRecursion(new HashSet<>(), clazz);

        List<Integer> listOfAttributes = new ArrayList<>();
        List<Integer> listOfDates = new ArrayList<>();
        List<Integer> listOfLists = new ArrayList<>();
        List<Integer> listOfReferences = new ArrayList<>();

        for (Field field: fieldsOfClass) {
            if (field.isAnnotationPresent(Attribute.class)) {
                Attribute annotationAttribute = field.getAnnotation(Attribute.class);
                int attributeId = annotationAttribute.attrId();
                if (Date.class.isAssignableFrom(field.getType())) {
                    listOfDates.add(attributeId);
                } else {
                    listOfAttributes.add(attributeId);
                }
            }
            if (field.isAnnotationPresent(AttributeList.class)) {
                AttributeList annotationAttribute = field.getAnnotation(AttributeList.class);
                int attributeId = annotationAttribute.attrId();
                listOfLists.add(attributeId);
            }
            if (field.isAnnotationPresent(Reference.class)) {
                Reference annotationAttribute = field.getAnnotation(Reference.class);
                int attributeId = annotationAttribute.attrId();
                listOfReferences.add(attributeId);
            }
        }

        Collections.sort(listOfAttributes);
        Collections.sort(listOfDates);
        Collections.sort(listOfLists);
        Collections.sort(listOfReferences);

        List<List<Integer>> resultList = new ArrayList<>();
        resultList.add(listOfAttributes);
        resultList.add(listOfDates);
        resultList.add(listOfLists);
        resultList.add(listOfReferences);

        return resultList;
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

    // returns field by ID, if it exists
    public Field getFieldById (Integer id, Class<? extends Entity> clazz, Class<? extends Annotation> annotation) {
        Set<Field> fields = getFieldsRecursion(new HashSet<>(), clazz);
        for (Field field: fields) {
            Integer annotationId = getIdFromAnnotation(field.getAnnotation(annotation));
            if (id.equals(annotationId)) {
                return field;
            }
        }
        IllegalArgumentException e = new IllegalArgumentException("There is no field with given ID");
        throw e;
    }

    // returns attribute ID
    public Integer getIdFromAnnotation(Annotation annotation) {
        Class annotationClass = annotation.annotationType();
        int idOfAttribute;

        if (annotationClass.equals(Attribute.class)) {
            idOfAttribute = ((Attribute) annotation).attrId();
        } else if (annotationClass.equals(AttributeList.class)) {
            idOfAttribute = ((AttributeList) annotation).attrId();
        } else if (annotationClass.equals(Reference.class)) {
            idOfAttribute = ((Reference) annotation).attrId();
        } else {
            return null;
        }
        return idOfAttribute;
    }

    private <T extends Entity> T createNewEntity(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
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
        entity.setId(id);
        List<List<Integer>> attributes = getListOfAttributes(entity.getClass());
        StringBuilder sql = createQuery("WHERE O.OBJECT_ID = " + id.toString(), attributes);

        return jdbcTemplate.query(sql.toString(), new ResultSetExtractor<T>() {
            @Override
            public T extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()) {
                    try {
                        setValues(entity, resultSet);
                        setDates(entity, resultSet);
//                        setLists(entity, resultSet);  // need to fix
//                        setReferences(entity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return entity;
            }
        });
    }

    public void setField(Entity entity, Field field, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(entity, value);
        field.setAccessible(false);
    }

    public <T extends Entity> void setValues(T entity, ResultSet rs) throws SQLException, IllegalAccessException {
        Class <? extends Entity> entityClass = entity.getClass();
        List<Integer> values = getListOfAttributes(entityClass).get(0);  // get list of Ids of Attributes
        for (int i = 0; i < values.size(); i++) {
            Field valueField = getFieldById(values.get(i), entityClass, Attribute.class);
            // then set field with the value from DB
            if (valueField.getType().equals(String.class)) {
                setField(entity, valueField, rs.getString(i+6));  // due to first five fields are from OBJECTS table
            } else if (valueField.getType().equals(Long.class)) {
                setField(entity, valueField, rs.getLong(i+6));
            } else if (valueField.getType().equals(Integer.class)) {
                setField(entity, valueField, rs.getInt(i+6));
            }
        }
    }

    public <T extends Entity> void setDates(T entity, ResultSet rs) throws SQLException, IllegalAccessException {
        Class <? extends Entity> entityClass = entity.getClass();
        List<Integer> dates = getListOfAttributes(entityClass).get(1); // get list of Ids of dates
        for (int i = 0; i < dates.size(); i++) {
            Field dateField = getFieldById(dates.get(i), entityClass, Attribute.class);
            setField(entity, dateField, rs.getDate(i+6));
        }

    }

    public <T extends Entity> void setLists(T entity, ResultSet rs) throws SQLException, IllegalAccessException {
        Class <? extends Entity> entityClass = entity.getClass();
        List<Integer> lists = getListOfAttributes(entityClass).get(2);  // get list of Ids of lists
        for (int i = 0; i < lists.size(); i++) {
            Field listField = getFieldById(lists.get(i), entityClass, AttributeList.class);
            if (listField.getType().equals(String.class)) {
                setField(entity, listField, rs.getString(i + 6));
            }
        }
    }

    public <T extends Entity> void setReferences(T entity, ResultSet rs) {
        Class <? extends Entity> entityClass = entity.getClass();
        List<Integer> references = getListOfAttributes(entityClass).get(3); // get list of Ids of references
        for (int i = 0; i < references.size(); i++) {
            Field referenceField = getFieldById(references.get(i), entityClass, Reference.class);
        }
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



