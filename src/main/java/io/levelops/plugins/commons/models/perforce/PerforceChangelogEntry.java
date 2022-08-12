package io.levelops.plugins.commons.models.perforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PerforceChangelogEntry {

    @JacksonXmlProperty(localName = "changenumber")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<PerforceChangelogChangeNumber> changeNumbers;

    public List<PerforceChangelogChangeNumber> getChangeNumbers() {
        return changeNumbers;
    }
}
