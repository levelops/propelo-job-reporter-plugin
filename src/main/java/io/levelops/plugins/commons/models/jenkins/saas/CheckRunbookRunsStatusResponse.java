package io.levelops.plugins.commons.models.jenkins.saas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckRunbookRunsStatusResponse {
    @JsonProperty("run_id")
    private String runId;
    @JsonProperty("state")
    private String state;
    @JsonProperty("output")
    private Output output;

    public String getRunId() {
        return runId;
    }

    public String getState() {
        return state;
    }

    public Output getOutput() {
        return output;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public static class Output {
        @JsonProperty("result")
        private KVV result;
        @JsonProperty("stop_build")
        private KVV stopBuild;

        public KVV getResult() {
            return result;
        }

        public void setResult(KVV result) {
            this.result = result;
        }

        public KVV getStopBuild() {
            return stopBuild;
        }

        public void setStopBuild(KVV stopBuild) {
            this.stopBuild = stopBuild;
        }
    }

    public static class KVV {
        @JsonProperty("key")
        private String key;
        @JsonProperty("value_type")
        private String valueType;
        @JsonProperty("value")
        private String value;

        public String getKey() {
            return key;
        }

        public String getValueType() {
            return valueType;
        }

        public String getValue() {
            return value;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValueType(String valueType) {
            this.valueType = valueType;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
