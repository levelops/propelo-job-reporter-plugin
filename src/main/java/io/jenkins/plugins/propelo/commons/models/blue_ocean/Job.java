package io.jenkins.plugins.propelo.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import java.util.List;

public class Job {
    @JsonProperty("actions")
    private List<Action> actions;
    @JsonProperty("disabled")
    private boolean disabled;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("estimatedDurationInMillis")
    private Long estimatedDurationInMillis;
    @JsonProperty("fullDisplayName")
    private String fullDisplayName;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("weatherScore")
    private Long weatherScore;
    @JsonProperty("_links")
    private JobLinks links;

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getEstimatedDurationInMillis() {
        return estimatedDurationInMillis;
    }

    public void setEstimatedDurationInMillis(Long estimatedDurationInMillis) {
        this.estimatedDurationInMillis = estimatedDurationInMillis;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public void setFullDisplayName(String fullDisplayName) {
        this.fullDisplayName = fullDisplayName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Long getWeatherScore() {
        return weatherScore;
    }

    public void setWeatherScore(Long weatherScore) {
        this.weatherScore = weatherScore;
    }

    public JobLinks getLinks() {
        return links;
    }

    public void setLinks(JobLinks links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return disabled == job.disabled &&
                Objects.equal(actions, job.actions) &&
                Objects.equal(displayName, job.displayName) &&
                Objects.equal(estimatedDurationInMillis, job.estimatedDurationInMillis) &&
                Objects.equal(fullDisplayName, job.fullDisplayName) &&
                Objects.equal(fullName, job.fullName) &&
                Objects.equal(name, job.name) &&
                Objects.equal(organization, job.organization) &&
                Objects.equal(weatherScore, job.weatherScore) &&
                Objects.equal(links, job.links);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actions, disabled, displayName, estimatedDurationInMillis, fullDisplayName, fullName, name, organization, weatherScore, links);
    }

    @Override
    public String toString() {
        return "Job{" +
                "actions=" + actions +
                ", disabled=" + disabled +
                ", displayName='" + displayName + '\'' +
                ", estimatedDurationInMillis=" + estimatedDurationInMillis +
                ", fullDisplayName='" + fullDisplayName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", weatherScore=" + weatherScore +
                ", links=" + links +
                '}';
    }
}
