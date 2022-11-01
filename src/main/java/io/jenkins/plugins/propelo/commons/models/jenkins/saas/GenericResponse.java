package io.jenkins.plugins.propelo.commons.models.jenkins.saas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class GenericResponse {
    @JsonProperty("response_type")
    private String responseType;
    @JsonProperty("payload")
    private String payload;

    public GenericResponse() {
    }

    public GenericResponse(String responseType, String payload) {
        this.responseType = responseType;
        this.payload = payload;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getPayload() {
        return payload;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericResponse that = (GenericResponse) o;
        return responseType.equals(that.responseType) &&
                payload.equals(that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseType, payload);
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "responseType='" + responseType + '\'' +
                ", payload=" + payload +
                '}';
    }
}
