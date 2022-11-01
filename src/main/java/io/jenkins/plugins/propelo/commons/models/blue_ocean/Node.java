package io.jenkins.plugins.propelo.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Node {
    @JsonProperty("id")
    private String id;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("displayDescription")
    private String displayDescription;

    @JsonProperty("result")
    private String result;
    @JsonProperty("state")
    private String state;
    @JsonProperty("durationInMillis")
    private Long durationInMillis;
    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("type")
    private String type;

    @JsonProperty("actions")
    private List<Action> actions;

    @JsonProperty("edges")
    private List<Edge> edges;

    @JsonProperty("log")
    private UUID log;

    @JsonProperty("steps")
    private List<Step> steps = new ArrayList<>();

    @JsonProperty("child_job_runs")
    private List<JobRun> childJobRuns = new ArrayList<>();

    @JsonProperty("firstParent")
    private String firstParent;
    @JsonProperty("restartable")
    private Boolean restartable;

    @JsonProperty("_links")
    private JobRunLinks links;

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(Long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getFirstParent() {
        return firstParent;
    }

    public void setFirstParent(String firstParent) {
        this.firstParent = firstParent;
    }

    public Boolean getRestartable() {
        return restartable;
    }

    public void setRestartable(Boolean restartable) {
        this.restartable = restartable;
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

    public UUID getLog() {
        return log;
    }

    public void setLog(UUID log) {
        this.log = log;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equal(id, node.id) &&
                Objects.equal(displayName, node.displayName) &&
                Objects.equal(displayDescription, node.displayDescription) &&
                Objects.equal(result, node.result) &&
                Objects.equal(state, node.state) &&
                Objects.equal(durationInMillis, node.durationInMillis) &&
                Objects.equal(startTime, node.startTime) &&
                Objects.equal(type, node.type) &&
                Objects.equal(actions, node.actions) &&
                Objects.equal(edges, node.edges) &&
                Objects.equal(log, node.log) &&
                Objects.equal(steps, node.steps) &&
                Objects.equal(childJobRuns, node.childJobRuns) &&
                Objects.equal(firstParent, node.firstParent) &&
                Objects.equal(restartable, node.restartable) &&
                Objects.equal(links, node.links);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, displayName, displayDescription, result, state, durationInMillis, startTime, type, actions, edges, log, steps, childJobRuns, firstParent, restartable, links);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", displayDescription='" + displayDescription + '\'' +
                ", result='" + result + '\'' +
                ", state='" + state + '\'' +
                ", durationInMillis=" + durationInMillis +
                ", startTime='" + startTime + '\'' +
                ", type='" + type + '\'' +
                ", actions=" + actions +
                ", edges=" + edges +
                ", log=" + log +
                ", steps=" + steps +
                ", childJobRuns=" + childJobRuns +
                ", firstParent='" + firstParent + '\'' +
                ", restartable=" + restartable +
                ", links=" + links +
                '}';
    }
}
