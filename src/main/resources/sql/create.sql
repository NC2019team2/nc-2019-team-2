CREATE TABLE OBJ_TYPE
(
    OBJECT_TYPE_ID NUMBER(20) NOT NULL ENABLE,
    PARENT_ID      NUMBER(20),
    CODE           VARCHAR2(20) NOT NULL UNIQUE,
    NAME           VARCHAR2(200 BYTE),
    DESCRIPTION    VARCHAR2(1000 BYTE),
    CONSTRAINT CON_OBJECT_TYPE_ID PRIMARY KEY (OBJECT_TYPE_ID),
    CONSTRAINT CON_PARENT_ID FOREIGN KEY (PARENT_ID) REFERENCES OBJ_TYPE (OBJECT_TYPE_ID) ON DELETE CASCADE ENABLE
);

CREATE TABLE ATTR_TYPE
(
    ATTR_ID      		NUMBER(20) NOT NULL,
    OBJECT_TYPE_ID 		NUMBER(20) NOT NULL,
    OBJECT_TYPE_ID_REF 	NUMBER(20),
    CODE         		VARCHAR2(20),
    NAME         		VARCHAR2(200 BYTE),
    CONSTRAINT CON_ATTR_ID PRIMARY KEY (ATTR_ID),
    CONSTRAINT CON_ATTR_OBJECT_TYPE_ID FOREIGN KEY (OBJECT_TYPE_ID) REFERENCES OBJ_TYPE (OBJECT_TYPE_ID),
    CONSTRAINT CON_ATTR_OBJECT_TYPE_ID_REF FOREIGN KEY (OBJECT_TYPE_ID_REF) REFERENCES OBJ_TYPE (OBJECT_TYPE_ID)
);

CREATE TABLE LISTS
(
    ATTR_ID       NUMBER(10) NOT NULL,
    LIST_VALUE_ID NUMBER(10) NOT NULL,
    VALUE         VARCHAR(4000),
    CONSTRAINT CON_LIST_VALUE_ID PRIMARY KEY (LIST_VALUE_ID),
    constraint CON_L_ATTR_ID FOREIGN KEY (ATTR_ID) REFERENCES ATTR_TYPE (ATTR_ID) ON DELETE CASCADE
);

CREATE TABLE OBJECTS (
    OBJECT_ID      NUMBER(20) NOT NULL,
    PARENT_ID      NUMBER(20),
    OBJECT_TYPE_ID NUMBER(20) NOT NULL,
    NAME           VARCHAR2(2000 BYTE),
    DESCRIPTION    VARCHAR2(4000 BYTE),
    CONSTRAINT CON_OBJECTS_ID PRIMARY KEY (OBJECT_ID),
    CONSTRAINT CON_PARENTS_ID FOREIGN KEY (PARENT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE DEFERRABLE,
    CONSTRAINT CON_OBJ_TYPE_ID FOREIGN KEY (OBJECT_TYPE_ID) REFERENCES OBJ_TYPE (OBJECT_TYPE_ID)
);

CREATE TABLE ATTRIBUTES
(
    ATTR_ID       NUMBER(10) NOT NULL,
    OBJECT_ID     NUMBER(20) NOT NULL,
    VALUE         VARCHAR2(4000 BYTE),
    DATE_VALUE    DATE,
    LIST_VALUE_ID NUMBER(10),
    CONSTRAINT  CON_ATR_LIST_VALUE_ID FOREIGN KEY (LIST_VALUE_ID) REFERENCES LISTS (LIST_VALUE_ID) ON DELETE CASCADE,
    CONSTRAINT  CON_ATR_ATTR_ID FOREIGN KEY (ATTR_ID) REFERENCES ATTR_TYPE (ATTR_ID) ON DELETE CASCADE,
    CONSTRAINT CON_ATR_OBJECT_ID FOREIGN KEY (OBJECT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE
);

CREATE TABLE OBJ_REFERENCE
(
    ATTR_ID   NUMBER(20) NOT NULL,
    REFERENCE NUMBER(20) NOT NULL,
    OBJECT_ID NUMBER(20) NOT NULL,
    CONSTRAINT CON_OBJREFERENCE_PK PRIMARY KEY (ATTR_ID,REFERENCE,OBJECT_ID),
    CONSTRAINT CON_REFERENCE FOREIGN KEY (REFERENCE) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
    CONSTRAINT CON_ROBJECT_ID FOREIGN KEY (OBJECT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
    CONSTRAINT CON_RATTR_ID FOREIGN KEY (ATTR_ID) REFERENCES ATTR_TYPE (ATTR_ID) ON DELETE CASCADE
);
CREATE SEQUENCE OBJECT_ID_INCREMENT
    START WITH 1
    INCREMENT BY 1
    CACHE 20;



