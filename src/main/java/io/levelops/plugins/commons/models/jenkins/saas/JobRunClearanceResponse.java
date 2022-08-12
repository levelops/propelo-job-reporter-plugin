package io.levelops.plugins.commons.models.jenkins.saas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JobRunClearanceResponse {
    @JsonProperty("run_ids")
    private List<String> runIds;

    public JobRunClearanceResponse() {
    }

    public JobRunClearanceResponse(List<String> runIds) {
        this.runIds = runIds;
    }

    public List<String> getRunIds() {
        return runIds;
    }

    public void setRunIds(List<String> runIds) {
        this.runIds = runIds;
    }
}
