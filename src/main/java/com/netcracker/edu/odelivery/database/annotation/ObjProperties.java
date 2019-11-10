package com.netcracker.edu.odelivery.database.annotation;

public @interface ObjProperties {
    String name();
    int objType();
    int parentId() default 0;
}

