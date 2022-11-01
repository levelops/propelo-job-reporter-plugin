package io.jenkins.plugins.propelo.commons.models.jenkins.saas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class GenericRequest {
    @JsonProperty("request_type")
    private final String requestType;

    @JsonProperty("payload")
    private final String payload;

    public GenericRequest(String requestType, String payload) {
        this.requestType = requestType;
        this.payload = payload;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericRequest that = (GenericRequest) o;
        return requestType.equals(that.requestType) &&
                payload.equals(that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestType, payload);
    }

    @Override
    public String toString() {
        return "GenericRequest{" +
                "requestType='" + requestType + '\'' +
                ", payload=" + payload +
                '}';
    }
}