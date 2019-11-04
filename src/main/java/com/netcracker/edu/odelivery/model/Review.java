package com.netcracker.edu.odelivery.model;

public class Review {
    private Long id;
    private Client user;
    private Integer mark;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
