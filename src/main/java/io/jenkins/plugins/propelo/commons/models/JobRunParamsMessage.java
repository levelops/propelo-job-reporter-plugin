package io.jenkins.plugins.propelo.commons.models;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JobRunParamsMessage implements Comparable<JobRunParamsMessage>{
    private Long buildNumber;
    private List<JobRunParam> jobRunParams;

    public JobRunParamsMessage(Long buildNumber, List<JobRunParam> jobRunParams) {
        this.buildNumber = buildNumber;
        this.jobRunParams = jobRunParams;
    }

    public Long getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Long buildNumber) {
        this.buildNumber = buildNumber;
    }

    public List<JobRunParam> getJobRunParams() {
        return jobRunParams;
    }

    public void setJobRunParams(List<JobRunParam> jobRunParams) {
        this.jobRunParams = jobRunParams;
    }

    @Override
    public int compareTo(@NotNull JobRunParamsMessage o) {
        return (int) (this.buildNumber - o.getBuildNumber());
    }
}
