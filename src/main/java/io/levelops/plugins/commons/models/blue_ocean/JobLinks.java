package io.levelops.plugins.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class JobLinks {
    @JsonProperty("self")
    private Link self;

    @JsonProperty("scm")
    private Link scm;
    @JsonProperty("actions")
    private Link actions;
    @JsonProperty("runs")
    private Link runs;
    @JsonProperty("trends")
    private Link trends;
    @JsonProperty("queue")
    private Link queue;

    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }

    public Link getScm() {
        return scm;
    }

    public void setScm(Link scm) {
        this.scm = scm;
    }

    public Link getActions() {
        return actions;
    }

    public void setActions(Link actions) {
        this.actions = actions;
    }

    public Link getRuns() {
        return runs;
    }

    public void setRuns(Link runs) {
        this.runs = runs;
    }

    public Link getTrends() {
        return trends;
    }

    public void setTrends(Link trends) {
        this.trends = trends;
    }

    public Link getQueue() {
        return queue;
    }

    public void setQueue(Link queue) {
        this.queue = queue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobLinks jobLinks = (JobLinks) o;
        return Objects.equal(self, jobLinks.self) &&
                Objects.equal(scm, jobLinks.scm) &&
                Objects.equal(actions, jobLinks.actions) &&
                Objects.equal(runs, jobLinks.runs) &&
                Objects.equal(trends, jobLinks.trends) &&
                Objects.equal(queue, jobLinks.queue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(self, scm, actions, runs, trends, queue);
    }

    @Override
    public String toString() {
        return "JobLinks{" +
                "self=" + self +
                ", scm=" + scm +
                ", actions=" + actions +
                ", runs=" + runs +
                ", trends=" + trends +
                ", queue=" + queue +
                '}';
    }
}
