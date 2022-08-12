package io.levelops.plugins.commons.models.jenkins.saas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.levelops.plugins.commons.models.JobConfigChangeType;
import io.levelops.plugins.commons.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;

public class JobConfigChangeRequestTest {
    private static final ObjectMapper MAPPER = JsonUtils.buildObjectMapper();

    @Test
    public void test() throws JsonProcessingException {
        JobConfigChangeRequest test = new JobConfigChangeRequest("pipeline-1", "master", null, "pipeline-1/branches/master", "pipeline-1/master",
                "abcd", "abcd", "url", null,  null, JobConfigChangeType.CHANGED, 100l, "v", "v"   );
        String data = MAPPER.writeValueAsString(test);
        Assert.assertNotNull(data);
    }
}