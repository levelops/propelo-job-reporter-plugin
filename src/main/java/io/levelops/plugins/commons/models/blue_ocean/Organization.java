package io.levelops.plugins.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Organization {
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("name")
    private String name;

    public Organization() {
    }

    public Organization(String displayName, String name) {
        this.displayName = displayName;
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equal(displayName, that.displayName) &&
                Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(displayName, name);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "displayName='" + displayName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}