package com.clearviewlive.api.integrations.model;

import java.util.List;

public class Ticket {

    private int id;
    private String key;
    private List<Field> fieldList;

    public Ticket() {
    }

    public Ticket(int id, String key, List<Field> fieldList) {
        this.id = id;
        this.key = key;
        this.fieldList = fieldList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Field> getFieldsList() {
        return fieldList;
    }

    public void setFieldsList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}
