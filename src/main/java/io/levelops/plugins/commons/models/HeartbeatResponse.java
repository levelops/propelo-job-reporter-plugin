package io.levelops.plugins.commons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeartbeatResponse {
    @JsonProperty("success")
    boolean success;

    @JsonProperty("server_version")
    String serverVersion;

    @JsonProperty("configuration")
    CiCdInstanceConfig configuration;

    public HeartbeatResponse(boolean success, String serverVersion, CiCdInstanceConfig configuration) {
        this.success = success;
        this.serverVersion = serverVersion;
        this.configuration = configuration;
    }

    public HeartbeatResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public CiCdInstanceConfig getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        return "HeartbeatResponse{" +
                "success=" + success +
                ", serverVersion='" + serverVersion + '\'' +
                ", configuration=" + configuration +
                '}';
    }

    public static class CiCdInstanceConfig {

        @JsonProperty("heartbeat_duration")
        Integer heartbeatDuration;

        @JsonProperty("bullseye_report_paths")
        String bullseyeReportPaths;

        public CiCdInstanceConfig(Integer heartbeatDuration, String bullseyeReportPaths) {
            this.heartbeatDuration = heartbeatDuration;
            this.bullseyeReportPaths = bullseyeReportPaths;
        }

        public CiCdInstanceConfig() {
        }

        public Integer getHeartbeatDuration() {
            return heartbeatDuration;
        }

        public String getBullseyeReportPaths() {
            return bullseyeReportPaths;
        }

        @Override
        public String toString() {
            return "CiCdInstanceConfig{" +
                    "heartbeatDuration=" + heartbeatDuration +
                    ", bullseyeReportPaths='" + bullseyeReportPaths + '\'' +
                    '}';
        }
    }
}
