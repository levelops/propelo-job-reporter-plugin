package io.levelops.plugins.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import java.util.List;
import java.util.UUID;

public class Step {
    @JsonProperty("id")
    private String id;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("displayDescription")
    private String displayDescription;

    @JsonProperty("durationInMillis")
    private Long durationInMillis;

    @JsonProperty("result")
    private String result;

    @JsonProperty("state")
    private String state;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("input")
    private String input;

    @JsonProperty("log")
    private UUID log;

    @JsonProperty("actions")
    private List<Action> actions;

    @JsonProperty("_links")
    private JobRunLinks links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public Long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(Long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public UUID getLog() {
        return log;
    }

    public void setLog(UUID log) {
        this.log = log;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public JobRunLinks getLinks() {
        return links;
    }

    public void setLinks(JobRunLinks links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return Objects.equal(id, step.id) &&
                Objects.equal(displayName, step.displayName) &&
                Objects.equal(displayDescription, step.displayDescription) &&
                Objects.equal(durationInMillis, step.durationInMillis) &&
                Objects.equal(result, step.result) &&
                Objects.equal(state, step.state) &&
                Objects.equal(startTime, step.startTime) &&
                Objects.equal(input, step.input) &&
                Objects.equal(log, step.log) &&
                Objects.equal(actions, step.actions) &&
                Objects.equal(links, step.links);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, displayName, displayDescription, durationInMillis, result, state, startTime, input, log, actions, links);
    }

    @Override
    public String toString() {
        return "Step{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", displayDescription='" + displayDescription + '\'' +
                ", durationInMillis=" + durationInMillis +
                ", result='" + result + '\'' +
                ", state='" + state + '\'' +
                ", startTime='" + startTime + '\'' +
                ", input='" + input + '\'' +
                ", log=" + log +
                ", actions=" + actions +
                ", links=" + links +
                '}';
    }
}
