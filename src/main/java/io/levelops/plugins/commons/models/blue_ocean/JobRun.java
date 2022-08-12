package io.levelops.plugins.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import io.levelops.plugins.commons.models.JobRunDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JobRun {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("pipeline")
    private String pipeline;
    @JsonProperty("result")
    private String result;
    @JsonProperty("state")
    private String state;
    @JsonProperty("type")
    private String type;
    @JsonProperty("actions")
    private List<Action> actions;
    @JsonProperty("causes")
    private List<Cause> causes;
    @JsonProperty("description")
    private String description;
    @JsonProperty("durationInMillis")
    private Long durationInMillis;
    @JsonProperty("enQueueTime")
    private String enQueueTime;
    @JsonProperty("endTime")
    private String endTime;
    @JsonProperty("estimatedDurationInMillis")
    private Long estimatedDurationInMillis;

    @JsonProperty("replayable")
    private boolean replayable;

    @JsonProperty("runSummary")
    private String runSummary;
    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("_links")
    private JobRunLinks links;

    @JsonProperty("child_job_runs")
    private List<JobRun> childJobRuns = new ArrayList<>();

    @JsonProperty("stages")
    private List<Node> stages = new ArrayList<>();

    @JsonProperty("log")
    private UUID log;

    @JsonProperty("job_normalized_full_name")
    private String jobNormalizedFullName;

    public JobRun() {}

    public JobRun(JobRunDetail jobRunDetail, UUID logFileUUID) {
        this.name =  jobRunDetail.getJobName();
        this.result =  jobRunDetail.getResult();
        this.durationInMillis = jobRunDetail.getDuration();
        this.startTime = String.valueOf(jobRunDetail.getStartTime());
        this.log = logFileUUID;
        this.jobNormalizedFullName = jobRunDetail.getJobNormalizedFullName();
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Cause> getCauses() {
        return causes;
    }

    public void setCauses(List<Cause> causes) {
        this.causes = causes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(Long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getEnQueueTime() {
        return enQueueTime;
    }

    public void setEnQueueTime(String enQueueTime) {
        this.enQueueTime = enQueueTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getEstimatedDurationInMillis() {
        return estimatedDurationInMillis;
    }

    public void setEstimatedDurationInMillis(Long estimatedDurationInMillis) {
        this.estimatedDurationInMillis = estimatedDurationInMillis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public boolean isReplayable() {
        return replayable;
    }

    public void setReplayable(boolean replayable) {
        this.replayable = replayable;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRunSummary() {
        return runSummary;
    }

    public void setRunSummary(String runSummary) {
        this.runSummary = runSummary;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JobRunLinks getLinks() {
        return links;
    }

    public void setLinks(JobRunLinks links) {
        this.links = links;
    }

    public List<JobRun> getChildJobRuns() {
        return childJobRuns;
    }

    public void setChildJobRuns(List<JobRun> childJobRuns) {
        this.childJobRuns = childJobRuns;
    }

    public List<Node> getStages() {
        return stages;
    }

    public void setStages(List<Node> stages) {
        this.stages = stages;
    }

    public UUID getLog() {
        return log;
    }

    public void setLog(UUID log) {
        this.log = log;
    }

    public String getJobNormalizedFullName() {
        return jobNormalizedFullName;
    }

    public void setJobNormalizedFullName(String jobNormalizedFullName) {
        this.jobNormalizedFullName = jobNormalizedFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRun jobRun = (JobRun) o;
        return replayable == jobRun.replayable &&
                Objects.equal(id, jobRun.id) &&
                Objects.equal(name, jobRun.name) &&
                Objects.equal(organization, jobRun.organization) &&
                Objects.equal(pipeline, jobRun.pipeline) &&
                Objects.equal(result, jobRun.result) &&
                Objects.equal(state, jobRun.state) &&
                Objects.equal(type, jobRun.type) &&
                Objects.equal(actions, jobRun.actions) &&
                Objects.equal(causes, jobRun.causes) &&
                Objects.equal(description, jobRun.description) &&
                Objects.equal(durationInMillis, jobRun.durationInMillis) &&
                Objects.equal(enQueueTime, jobRun.enQueueTime) &&
                Objects.equal(endTime, jobRun.endTime) &&
                Objects.equal(estimatedDurationInMillis, jobRun.estimatedDurationInMillis) &&
                Objects.equal(runSummary, jobRun.runSummary) &&
                Objects.equal(startTime, jobRun.startTime) &&
                Objects.equal(links, jobRun.links) &&
                Objects.equal(childJobRuns, jobRun.childJobRuns) &&
                Objects.equal(stages, jobRun.stages) &&
                Objects.equal(log, jobRun.log) &&
                Objects.equal(jobNormalizedFullName, jobRun.jobNormalizedFullName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, organization, pipeline, result, state, type, actions, causes, description, durationInMillis, enQueueTime, endTime, estimatedDurationInMillis, replayable, runSummary, startTime, links, childJobRuns, stages, log, jobNormalizedFullName);
    }

    @Override
    public String toString() {
        return "JobRun{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", pipeline='" + pipeline + '\'' +
                ", result='" + result + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", actions=" + actions +
                ", causes=" + causes +
                ", description='" + description + '\'' +
                ", durationInMillis=" + durationInMillis +
                ", enQueueTime='" + enQueueTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", estimatedDurationInMillis=" + estimatedDurationInMillis +
                ", replayable=" + replayable +
                ", runSummary='" + runSummary + '\'' +
                ", startTime='" + startTime + '\'' +
                ", links=" + links +
                ", childJobRuns=" + childJobRuns +
                ", stages=" + stages +
                ", log='" + log + '\'' +
                ", jobNormalizedFullName='" + jobNormalizedFullName + '\'' +
                '}';
    }
}
