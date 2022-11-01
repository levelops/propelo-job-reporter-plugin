package io.jenkins.plugins.propelo.commons.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jenkins.plugins.propelo.commons.models.JobConfigChange;
import io.jenkins.plugins.propelo.commons.models.JobConfigChangeType;
import io.jenkins.plugins.propelo.commons.models.JobNameDetails;
import io.jenkins.plugins.propelo.commons.utils.JsonUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JobConfigChangeTest {
    private final static ObjectMapper MAPPER = JsonUtils.buildObjectMapper();
    @Test
    public void test() throws IOException {
        JobNameDetails jobNameDetails = new JobNameDetails("pipeline-1","master", "pipeline-1/branches/master", null, "pipeline-1/master");
        JobConfigChange jobConfigChange = new JobConfigChange(jobNameDetails, JobConfigChangeType.CHANGED, System.currentTimeMillis(), "viraj", "Viraj Ajgaonkar");
        String text = MAPPER.writeValueAsString(jobConfigChange);
        Assert.assertNotNull(text);
    }
}