package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Id;
import com.netcracker.edu.odelivery.database.annotation.Name;

public class Entity {
    @Id
    private Long id;
    @Name
    private String name;

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
}
