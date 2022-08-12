package io.levelops.plugins.commons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ArtifactTags {

    @JsonProperty("product")
    private final String product;

    @JsonProperty("release")
    private final String release;

    @JsonProperty("type")
    private final String type;

    public ArtifactTags(String product, String release, String type) {
        this.product = product;
        this.release = release;
        this.type = type;
    }

    public ArtifactTags() {
        this(null, null, null);
    }

    public String getProduct() {
        return product;
    }

    public String getRelease() {
        return release;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArtifactTags that = (ArtifactTags) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(release, that.release) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, release, type);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArtifactTags{");
        sb.append("product='").append(product).append('\'');
        sb.append(", release='").append(release).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
