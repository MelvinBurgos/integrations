package com.clearviewlive.api.integrations.model;

import java.util.Date;

public class Ticket {

    private int ticketID;
    private String key;
    private String agentName;
    private Date startDate;
    private String type;
    private String priority;
    private String company;
    private boolean completed;
    private long totalDuration;
    private long open;
    private long inProgress;
    private long waiting;
    private long internalValidation;

    public Ticket() {
    }

    public Ticket(int ticketID, String key, String agentName, Date startDate, String type, String priority, String company, boolean completed, long totalDuration, long open, long inProgress, long waiting, long internalValidation) {
        this.ticketID = ticketID;
        this.key = key;
        this.agentName = agentName;
        this.startDate = startDate;
        this.type = type;
        this.priority = priority;
        this.company = company;
        this.completed = completed;
        this.totalDuration = totalDuration;
        this.open = open;
        this.inProgress = inProgress;
        this.waiting = waiting;
        this.internalValidation = internalValidation;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public long getOpen() {
        return open;
    }

    public void setOpen(long open) {
        this.open = open;
    }

    public long getInProgress() {
        return inProgress;
    }

    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }

    public long getWaiting() {
        return waiting;
    }

    public void setWaiting(long waiting) {
        this.waiting = waiting;
    }

    public long getInternalValidation() {
        return internalValidation;
    }

    public void setInternalValidation(long internalValidation) {
        this.internalValidation = internalValidation;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", key='" + key + '\'' +
                ", agentName='" + agentName + '\'' +
                ", startDate=" + startDate +
                ", type='" + type + '\'' +
                ", priority='" + priority + '\'' +
                ", company='" + company + '\'' +
                ", completed=" + completed +
                ", totalDuration=" + totalDuration +
                ", open=" + open +
                ", inProgress=" + inProgress +
                ", waiting=" + waiting +
                ", internalValidation=" + internalValidation +
                '}';
    }
}
