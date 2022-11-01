package io.jenkins.plugins.propelo.commons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PluginResultDTO {
    @JsonProperty("plugin_name")
    private final String pluginName;

    @JsonProperty("class")
    private final String pluginClass;

    @JsonProperty("tool")
    private final String tool; // unique name of the tool or UUID if custom

    @JsonProperty("version")
    private final String version;

    @JsonProperty("tags")
    private final List<String> tags = new ArrayList<>();

    @JsonProperty("product_ids")
    private final List<String> productIds;

    @JsonProperty("successful")
    private final Boolean successful;

    @JsonProperty("metadata")
    private final Map<String, Object> metadata;

    @JsonProperty("labels")
    private final Map<String, List<String>> labels;

    @JsonProperty("results")
    private final Map<String, Object> results;

    public PluginResultDTO(String pluginName, String pluginClass, String tool, String version, List<String> productIds,
                           Boolean successful, Map<String, Object> metadata, Map<String, List<String>> labels, Map<String, Object> results) {
        this.pluginName = pluginName;
        this.pluginClass = pluginClass;
        this.tool = tool;
        this.version = version;
        this.productIds = productIds;
        this.successful = successful;
        this.metadata = metadata;
        this.labels = labels;
        this.results = results;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginClass() {
        return pluginClass;
    }

    public String getTool() {
        return tool;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public Map<String, List<String>> getLabels() {
        return labels;
    }

    public Map<String, Object> getResults() {
        return results;
    }
}
