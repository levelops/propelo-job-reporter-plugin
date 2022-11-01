package io.jenkins.plugins.propelo.commons.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobConfigChange {
    @JsonUnwrapped
    private JobNameDetails jobNameDetails;

    @JsonProperty("change_type")
    private JobConfigChangeType changeType;

    @JsonProperty("change_time")
    private long changeTime;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("users_name")
    private String usersName;

    public JobConfigChange(JobNameDetails jobNameDetails, JobConfigChangeType changeType, long changeTime, String userId, String usersName) {
        this.jobNameDetails = jobNameDetails;
        this.changeType = changeType;
        this.changeTime = changeTime;
        this.userId = userId;
        this.usersName = usersName;
    }

    public JobNameDetails getJobNameDetails() {
        return jobNameDetails;
    }

    public void setJobNameDetails(JobNameDetails jobNameDetails) {
        this.jobNameDetails = jobNameDetails;
    }

    public JobConfigChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(JobConfigChangeType changeType) {
        this.changeType = changeType;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobConfigChange that = (JobConfigChange) o;
        return changeTime == that.changeTime &&
                Objects.equal(jobNameDetails, that.jobNameDetails) &&
                changeType == that.changeType &&
                Objects.equal(userId, that.userId) &&
                Objects.equal(usersName, that.usersName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jobNameDetails, changeType, changeTime, userId, usersName);
    }

    @Override
    public String toString() {
        return "JobConfigChange{" +
                "jobNameDetails=" + jobNameDetails +
                ", changeType=" + changeType +
                ", changeTime=" + changeTime +
                ", userId='" + userId + '\'' +
                ", usersName='" + usersName + '\'' +
                '}';
    }
}
