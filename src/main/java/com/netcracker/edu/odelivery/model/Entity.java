package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Id;
import com.netcracker.edu.odelivery.database.annotation.Name;

public class Entity {
    @Id
    private Long id;
    @Name
    private String name;
    private Long parentId;
    private Long objectTypeId;
    private String description;

    public Entity() {

    }

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(Long objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
