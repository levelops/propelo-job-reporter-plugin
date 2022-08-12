package io.levelops.plugins.commons.models;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.Objects;

public class LevelOpsConfig {
    @JsonProperty("api_url")
    private String apiUrl;

    public LevelOpsConfig() {
    }

    public LevelOpsConfig(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelOpsConfig that = (LevelOpsConfig) o;
        return Objects.equal(apiUrl, that.apiUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(apiUrl);
    }

    @Override
    public String toString() {
        return "LevelOpsConfig{" +
                "apiUrl='" + apiUrl + '\'' +
                '}';
    }
}
