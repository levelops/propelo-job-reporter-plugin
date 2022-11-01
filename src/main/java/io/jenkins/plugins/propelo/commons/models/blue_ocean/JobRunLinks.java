package io.jenkins.plugins.propelo.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class JobRunLinks {
    @JsonProperty("prevRun")
    private Link prevRun;
    @JsonProperty("parent")
    private Link parent;
    @JsonProperty("tests")
    private Link tests;
    @JsonProperty("nodes")
    private Link nodes;
    @JsonProperty("log")
    private Link log;
    @JsonProperty("self")
    private Link self;
    @JsonProperty("blueTestSummary")
    private Link blueTestSummary;
    @JsonProperty("actions")
    private Link actions;
    @JsonProperty("steps")
    private Link steps;
    @JsonProperty("artifacts")
    private Link artifacts;
    @JsonProperty("changeSet")
    private Link changeSet;

    public Link getPrevRun() {
        return prevRun;
    }

    public void setPrevRun(Link prevRun) {
        this.prevRun = prevRun;
    }

    public Link getParent() {
        return parent;
    }

    public void setParent(Link parent) {
        this.parent = parent;
    }

    public Link getTests() {
        return tests;
    }

    public void setTests(Link tests) {
        this.tests = tests;
    }

    public Link getNodes() {
        return nodes;
    }

    public void setNodes(Link nodes) {
        this.nodes = nodes;
    }

    public Link getLog() {
        return log;
    }

    public void setLog(Link log) {
        this.log = log;
    }

    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }

    public Link getBlueTestSummary() {
        return blueTestSummary;
    }

    public void setBlueTestSummary(Link blueTestSummary) {
        this.blueTestSummary = blueTestSummary;
    }

    public Link getActions() {
        return actions;
    }

    public void setActions(Link actions) {
        this.actions = actions;
    }

    public Link getSteps() {
        return steps;
    }

    public void setSteps(Link steps) {
        this.steps = steps;
    }

    public Link getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(Link artifacts) {
        this.artifacts = artifacts;
    }

    public Link getChangeSet() {
        return changeSet;
    }

    public void setChangeSet(Link changeSet) {
        this.changeSet = changeSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRunLinks that = (JobRunLinks) o;
        return Objects.equal(prevRun, that.prevRun) &&
                Objects.equal(parent, that.parent) &&
                Objects.equal(tests, that.tests) &&
                Objects.equal(nodes, that.nodes) &&
                Objects.equal(log, that.log) &&
                Objects.equal(self, that.self) &&
                Objects.equal(blueTestSummary, that.blueTestSummary) &&
                Objects.equal(actions, that.actions) &&
                Objects.equal(steps, that.steps) &&
                Objects.equal(artifacts, that.artifacts) &&
                Objects.equal(changeSet, that.changeSet);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prevRun, parent, tests, nodes, log, self, blueTestSummary, actions, steps, artifacts, changeSet);
    }

    @Override
    public String toString() {
        return "JobRunLinks{" +
                "prevRun=" + prevRun +
                ", parent=" + parent +
                ", tests=" + tests +
                ", nodes=" + nodes +
                ", log=" + log +
                ", self=" + self +
                ", blueTestSummary=" + blueTestSummary +
                ", actions=" + actions +
                ", steps=" + steps +
                ", artifacts=" + artifacts +
                ", changeSet=" + changeSet +
                '}';
    }
}
