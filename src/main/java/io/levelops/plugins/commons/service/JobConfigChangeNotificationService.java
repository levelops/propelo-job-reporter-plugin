package io.levelops.plugins.commons.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.levelops.plugins.commons.models.JobConfigChange;
import io.levelops.plugins.commons.models.jenkins.saas.GenericResponse;
import io.levelops.plugins.commons.models.jenkins.saas.JobConfigChangeRequest;
import io.levelops.plugins.commons.models.jenkins.saas.JobRunCompleteResponse;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobConfigChangeNotificationService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final String JENKINS_PLUGIN_JOB_CONFIG_CHANGE = "JenkinsPluginJobConfigChange";

    private final String apiUrl;
    private final ObjectMapper mapper;

    public JobConfigChangeNotificationService(String apiUrl, ObjectMapper mapper) {
        this.apiUrl = apiUrl;
        this.mapper = mapper;
    }

    public List<String> submitJobConfigChangeRequest(final String apiKey, JobConfigChange jobConfigChange, String scmUrl, String scmUserId,
                                                    String jenkinsInstanceGuid, String jenkinsInstanceName, String jenkinsInstanceUrl, final boolean trustAllCertificates, final ProxyConfigService.ProxyConfig proxyConfig
    ) throws IOException {
        JobConfigChangeRequest jobConfigChangeRequest = new JobConfigChangeRequest(
                jobConfigChange.getJobNameDetails().getJobName(), jobConfigChange.getJobNameDetails().getBranchName(), jobConfigChange.getJobNameDetails().getModuleName(),
                jobConfigChange.getJobNameDetails().getJobFullName(), jobConfigChange.getJobNameDetails().getJobNormalizedFullName(),
                jenkinsInstanceGuid, jenkinsInstanceName, jenkinsInstanceUrl, scmUrl, scmUserId,
                jobConfigChange.getChangeType(), jobConfigChange.getChangeTime(), jobConfigChange.getUserId(), jobConfigChange.getUsersName());
        LOGGER.log(Level.FINEST, "jobConfigChangeRequest = {0}", jobConfigChangeRequest);
        String payload = null;
        try {
            payload = mapper.writeValueAsString(jobConfigChangeRequest);
        } catch (JsonProcessingException e) {
            throw new IOException("Error converting JobConfigChangeRequest to json!!", e);
        }
        LOGGER.finest("payload = " + payload);

        GenericRequestService genericRequestService = new GenericRequestService(apiUrl, mapper);
        LOGGER.log(Level.FINEST, "perform Generic Request starting");
        GenericResponse genericResponse = genericRequestService.performGenericRequest(apiKey, JENKINS_PLUGIN_JOB_CONFIG_CHANGE, payload, trustAllCertificates, null, proxyConfig);
        LOGGER.log(Level.FINEST, "perform Generic Request completed, {0}", genericResponse);
        JobRunCompleteResponse jobRunCompleteResponse = mapper.readValue(genericResponse.getPayload(), JobRunCompleteResponse.class);
        return jobRunCompleteResponse.getRunIds();
    }
}
