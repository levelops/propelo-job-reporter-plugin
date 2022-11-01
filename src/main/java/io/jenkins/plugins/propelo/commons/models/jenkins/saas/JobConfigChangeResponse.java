package io.jenkins.plugins.propelo.commons.models.jenkins.saas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JobConfigChangeResponse {
    @JsonProperty("run_ids")
    private List<String> runIds;

    public JobConfigChangeResponse() {
    }

    public JobConfigChangeResponse(List<String> runIds) {
        this.runIds = runIds;
    }

    public List<String> getRunIds() {
        return runIds;
    }

    public void setRunIds(List<String> runIds) {
        this.runIds = runIds;
    }
}
