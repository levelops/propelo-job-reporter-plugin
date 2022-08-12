package io.levelops.plugins.commons.models.perforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PerforceChangelogChangeNumber {

    @JsonProperty("changeInfo")
    @JacksonXmlProperty
    String changeInfo;

    public String getChangeInfo() {
        return changeInfo;
    }
}
