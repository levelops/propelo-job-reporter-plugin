package io.levelops.plugins.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Action {
    @JsonProperty("description")
    private String description;

    @JsonProperty("link")
    private Link link;

    @JsonProperty("urlName")
    private String urlName;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equal(description, action.description) &&
                Objects.equal(link, action.link) &&
                Objects.equal(urlName, action.urlName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description, link, urlName);
    }

    @Override
    public String toString() {
        return "Action{" +
                "description='" + description + '\'' +
                ", link=" + link +
                ", urlName='" + urlName + '\'' +
                '}';
    }
}
