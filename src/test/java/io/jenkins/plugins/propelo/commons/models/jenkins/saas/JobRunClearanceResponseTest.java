package io.jenkins.plugins.propelo.commons.models.jenkins.saas;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jenkins.plugins.propelo.commons.models.jenkins.saas.JobRunClearanceResponse;
import io.jenkins.plugins.propelo.commons.utils.JsonUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JobRunClearanceResponseTest {
    private static final ObjectMapper MAPPER = JsonUtils.buildObjectMapper();
    @Test
    public void test() throws IOException {
        String serialized = "{\"run_ids\":[\"140a0ade-1bf5-449c-b2ac-945b698a577d\",\"e09f3180-b87e-44cf-a4a3-3eeb0779c0af\",\"f6153f47-bf2f-466c-933d-70407879221b\"]}";
        JobRunClearanceResponse response = MAPPER.readValue(serialized, JobRunClearanceResponse.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(3, response.getRunIds().size());
    }
}