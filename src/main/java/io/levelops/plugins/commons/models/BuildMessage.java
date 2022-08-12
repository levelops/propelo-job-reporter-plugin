package io.levelops.plugins.commons.models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BuildMessage implements Comparable<BuildMessage> {
    private String jobName;
    private long startTime;
    private String result;
    private long duration;
    private long buildNumber;
    private String userId;

    public BuildMessage(String jobName, long buildNumber, long startTime, long duration, String result, String userId) {
        this.jobName = jobName;
        this.startTime = startTime;
        this.result = result;
        this.duration = duration;
        this.buildNumber = buildNumber;
        this.userId = userId;
    }

    public String getJobName() {
        return jobName;
    }

    public long getBuildNumber()  { return buildNumber; }

    public long getStartTime() {
        return startTime;
    }

    public String getResult() {
        return result;
    }

    public long getDuration() { return duration; }

    public String getUserId() {
        return userId;
    }

    public int compare(BuildMessage message1, BuildMessage message2) {
        return (int) (message1.getBuildNumber() - message2.getBuildNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BuildMessage that = (BuildMessage) o;

        return new EqualsBuilder()
                .append(startTime, that.startTime)
                .append(duration, that.duration)
                .append(buildNumber, that.buildNumber)
                .append(jobName, that.jobName)
                .append(result, that.result)
                .append(userId, that.userId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(jobName)
                .append(startTime)
                .append(result)
                .append(duration)
                .append(buildNumber)
                .append(userId)
                .toHashCode();
    }

    @Override
    public int compareTo(BuildMessage message) {
        return compare(this, message);
    }

    @Override
    public String toString() {
        return buildNumber + "\t" + jobName + "\t" + result;
    }
}