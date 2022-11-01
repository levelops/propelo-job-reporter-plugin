package io.jenkins.plugins.propelo.commons.models;

import java.util.Date;

public class JenkinsStatusInfo {

    private static final JenkinsStatusInfo instance = new JenkinsStatusInfo();

    private Date lastSuccessfulDataEvent = new Date(0);
    private Date lastFailedDataEvent = new Date(0);
    private Date lastSuccessfulHeartbeat = new Date(0);
    private Date lastFailedHeartbeat = new Date(0);

    public static JenkinsStatusInfo getInstance() {
        return instance;
    }

    public Date getLastSuccessfulDataEvent() {
        return lastSuccessfulDataEvent;
    }

    public void setLastSuccessfulDataEvent(Date lastSuccessfulDataEvent) {
        this.lastSuccessfulDataEvent = lastSuccessfulDataEvent;
    }

    public Date getLastFailedDataEvent() {
        return lastFailedDataEvent;
    }

    public void setLastFailedDataEvent(Date lastFailedDataEvent) {
        this.lastFailedDataEvent = lastFailedDataEvent;
    }

    public Date getLastSuccessfulHeartbeat() {
        return lastSuccessfulHeartbeat;
    }

    public void setLastSuccessfulHeartbeat(Date lastSuccessfulHeartbeat) {
        this.lastSuccessfulHeartbeat = lastSuccessfulHeartbeat;
    }

    public Date getLastFailedHeartbeat() {
        return lastFailedHeartbeat;
    }

    public void setLastFailedHeartbeat(Date lastFailedHeartbeat) {
        this.lastFailedHeartbeat = lastFailedHeartbeat;
    }
}
