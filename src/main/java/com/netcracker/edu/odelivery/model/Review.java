package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Attribute;
import com.netcracker.edu.odelivery.database.annotation.ObjectType;
import com.netcracker.edu.odelivery.database.annotation.Reference;
import com.netcracker.edu.odelivery.model.attributes.ReviewAttributes;

@ObjectType(objType = ReviewAttributes.OBJECT_TYPE_ID)
public class Review extends Entity {
    @Reference(attrId = ReviewAttributes.USER_ID)
    private Client user;
    @Attribute(attrId = ReviewAttributes.MARK)
    private Integer mark;
    @Attribute(attrId = ReviewAttributes.REVIEW_TEXT)
    private String text;

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
