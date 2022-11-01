package io.jenkins.plugins.propelo.commons.models.blue_ocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Link {
    @JsonProperty("href")
    private String href;

    public Link() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equal(href, link.href);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(href);
    }

    @Override
    public String toString() {
        return "Link{" +
                "href='" + href + '\'' +
                '}';
    }
}
