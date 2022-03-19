package com.clearviewlive.api.integrations.model;

import java.util.Date;
import java.util.List;

public class Tickets {

    private int id;
    private String key;
    private List<Fields> fieldsList;

    public Tickets() {
    }

    public Tickets(int id, String key, List<Fields> fieldsList) {
        this.id = id;
        this.key = key;
        this.fieldsList = fieldsList;
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

    public List<Fields> getFieldsList() {
        return fieldsList;
    }

    public void setFieldsList(List<Fields> fieldsList) {
        this.fieldsList = fieldsList;
    }
}
