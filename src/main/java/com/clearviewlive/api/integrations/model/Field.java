package com.clearviewlive.api.integrations.model;

import java.util.Date;
import java.util.List;

public class Field {
    private int agentId;
    private String agentName;
    private Date startDate;
    private String type;
    private String priority;
    private String company;
    private boolean completed;
    private long totalDuration;
    private List<SummaryState> summaryStateList;

    public Field() {
    }

    public Field(int agentId, String agentName, Date startDate, String type, String priority, String company, boolean completed, long totalDuration, List<SummaryState> summaryStateList) {
        this.agentId = agentId;
        this.agentName = agentName;
        this.startDate = startDate;
        this.type = type;
        this.priority = priority;
        this.company = company;
        this.completed = completed;
        this.totalDuration = totalDuration;
        this.summaryStateList = summaryStateList;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public List<SummaryState> getSummaryStatesList() {
        return summaryStateList;
    }

    public void setSummaryStatesList(List<SummaryState> summaryStateList) {
        this.summaryStateList = summaryStateList;
    }
}
