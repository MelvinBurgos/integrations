package com.clearviewlive.api.integrations.model;

public class SummaryState {
    private int id;
    private String name;
    private long duration;

    public SummaryState() {
    }

    public SummaryState(int id, String name, long duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
