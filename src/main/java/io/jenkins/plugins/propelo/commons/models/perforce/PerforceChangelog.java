package io.jenkins.plugins.propelo.commons.models.perforce;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "changelog")
public class PerforceChangelog {

    @JacksonXmlProperty(localName = "entry")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<PerforceChangelogEntry> entries;

    public void setEntries(List<PerforceChangelogEntry> entries) {
        this.entries = entries;
    }

    public List<PerforceChangelogEntry> getEntries() {
        return entries;
    }
}
