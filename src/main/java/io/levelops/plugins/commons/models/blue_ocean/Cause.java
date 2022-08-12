package io.levelops.plugins.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Cause {
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("upstreamBuild")
    private Long upstreamBuild;
    @JsonProperty("upstreamProject")
    private String upstreamProject;
    @JsonProperty("upstreamUrl")
    private String upstreamUrl;


    public Cause() {
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUpstreamBuild() {
        return upstreamBuild;
    }

    public void setUpstreamBuild(Long upstreamBuild) {
        this.upstreamBuild = upstreamBuild;
    }

    public String getUpstreamProject() {
        return upstreamProject;
    }

    public void setUpstreamProject(String upstreamProject) {
        this.upstreamProject = upstreamProject;
    }

    public String getUpstreamUrl() {
        return upstreamUrl;
    }

    public void setUpstreamUrl(String upstreamUrl) {
        this.upstreamUrl = upstreamUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cause cause = (Cause) o;
        return Objects.equal(shortDescription, cause.shortDescription) &&
                Objects.equal(userId, cause.userId) &&
                Objects.equal(userName, cause.userName) &&
                Objects.equal(upstreamBuild, cause.upstreamBuild) &&
                Objects.equal(upstreamProject, cause.upstreamProject) &&
                Objects.equal(upstreamUrl, cause.upstreamUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shortDescription, userId, userName, upstreamBuild, upstreamProject, upstreamUrl);
    }

    @Override
    public String toString() {
        return "Cause{" +
                "shortDescription='" + shortDescription + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", upstreamBuild=" + upstreamBuild +
                ", upstreamProject='" + upstreamProject + '\'' +
                ", upstreamUrl='" + upstreamUrl + '\'' +
                '}';
    }
}
