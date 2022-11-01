package io.jenkins.plugins.propelo.commons.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JobConfigChangeType {
    CREATED("created"),
    RENAMED("renamed"),
    CHANGED("changed"),
    DELETED("deleted");

    private String text;

    JobConfigChangeType(String text) {
        this.text = text;
    }

    @JsonValue
    @Override
    public String toString() {
        return text;
    }
}
