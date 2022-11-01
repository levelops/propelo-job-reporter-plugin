package io.jenkins.plugins.propelo.commons.models.jenkins.saas;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jenkins.plugins.propelo.commons.models.jenkins.saas.CheckRunbookRunsStatusResponse;
import io.jenkins.plugins.propelo.commons.models.jenkins.saas.GenericResponse;
import io.jenkins.plugins.propelo.commons.utils.JsonUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

public class CheckRunbookRunsStatusResponseTest {
    private static final ObjectMapper MAPPER = JsonUtils.buildObjectMapper();
    @Test
    public void test() throws IOException, URISyntaxException {
        File configFile = new File(this.getClass().getClassLoader().getResource("models/CheckRunbookRunsStatusResponse.json").toURI());
        String serialized = new String(Files.readAllBytes(configFile.toPath()));
        GenericResponse genericResponse = MAPPER.readValue(serialized, GenericResponse.class);
        List<CheckRunbookRunsStatusResponse> runResponses = MAPPER.readValue(genericResponse.getPayload(), MAPPER.getTypeFactory().constructCollectionLikeType(List.class, CheckRunbookRunsStatusResponse.class));
        Assert.assertEquals(2, runResponses.size());
    }
}