package io.levelops.plugins.commons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeartbeatRequest {

    @JsonProperty("instance_id")
    String instanceId;

    @JsonProperty("timestamp")
    Long timestamp;

    @JsonProperty("details")
    InstanceDetails instanceDetails;

    public HeartbeatRequest(String instanceId, Long timestamp, InstanceDetails instanceDetails) {
        this.instanceId = instanceId;
        this.timestamp = timestamp;
        this.instanceDetails = instanceDetails;
    }

    public HeartbeatRequest() {
    }

    @Override
    public String toString() {
        return "HeartbeatRequest{" +
                "instanceId='" + instanceId + '\'' +
                ", timestamp=" + timestamp +
                ", instanceDetails=" + instanceDetails +
                '}';
    }

    public static class InstanceDetails {

        @JsonProperty("jenkins_version")
        String jenkinsVersion;

        @JsonProperty("plugin_version")
        String pluginVersion;

        @JsonProperty("config_updated_at")
        long configUpdatedAt;

        @JsonProperty("jenkins_instance_url")
        String instanceUrl;

        @JsonProperty("jenkins_instance_name")
        String instanceName;

        public InstanceDetails(String jenkinsVersion, String pluginVersion, long configUpdatedAt, String instanceUrl, String instanceName) {
            this.jenkinsVersion = jenkinsVersion;
            this.pluginVersion = pluginVersion;
            this.configUpdatedAt = configUpdatedAt;
            this.instanceUrl = instanceUrl;
            this.instanceName =  instanceName;
        }

        public InstanceDetails() {
        }
    }
}
