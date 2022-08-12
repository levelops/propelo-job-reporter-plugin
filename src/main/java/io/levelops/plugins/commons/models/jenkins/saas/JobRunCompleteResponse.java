package io.levelops.plugins.commons.models.jenkins.saas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JobRunCompleteResponse {
    @JsonProperty("run_ids")
    private List<String> runIds;

    @JsonProperty("error")
    private String errorMessage;

    public JobRunCompleteResponse() {
    }

    public JobRunCompleteResponse(List<String> runIds, String errorMessage) {
        this.runIds = runIds;
        this.errorMessage  = errorMessage;
    }

    public List<String> getRunIds() {
        return runIds;
    }

    public void setRunIds(List<String> runIds) {
        this.runIds = runIds;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
