package io.levelops.plugins.commons.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.levelops.plugins.commons.utils.JsonUtils;
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