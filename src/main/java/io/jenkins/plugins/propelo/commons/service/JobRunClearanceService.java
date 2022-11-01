package io.jenkins.plugins.propelo.commons.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jenkins.plugins.propelo.commons.models.JobRunDetail;
import io.jenkins.plugins.propelo.commons.models.jenkins.saas.GenericResponse;
import io.jenkins.plugins.propelo.commons.models.jenkins.saas.JobRunClearanceCheck;
import io.jenkins.plugins.propelo.commons.models.jenkins.saas.JobRunClearanceResponse;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

public class JobRunClearanceService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final String JENKINS_PLUGIN_JOB_RUN_CLEARANCE_REQUEST = "JenkinsPluginJobRunClearanceRequest";

    private final String apiUrl;
    private final ObjectMapper mapper;

    public JobRunClearanceService(String apiUrl, ObjectMapper mapper) {
        this.apiUrl = apiUrl;
        this.mapper = mapper;
    }

    public List<String> submitJobRunClearanceRequest(final String apiKey, JobRunDetail jobRunDetail, String scmUrl, String scmUserId, String jenkinsInstanceGuid, String jenkinsInstanceName, final boolean trustAllCertificates, final ProxyConfigService.ProxyConfig proxyConfig) throws IOException {
        JobRunClearanceCheck jobRunClearanceCheck = new JobRunClearanceCheck(jobRunDetail.getJobName(), jobRunDetail.getUserId(),
                jobRunDetail.getJobRunParams(), scmUrl, scmUserId, jenkinsInstanceGuid, jenkinsInstanceName, null);

        String payload = null;
        try {
            payload = mapper.writeValueAsString(jobRunClearanceCheck);
        } catch (JsonProcessingException e) {
            throw new IOException("Error converting JobRunClearanceCheck to json!!", e);
        }
        LOGGER.finest("payload = " + payload);

        GenericRequestService genericRequestService = new GenericRequestService(apiUrl, mapper);
        GenericResponse genericResponse = genericRequestService.performGenericRequest(apiKey, JENKINS_PLUGIN_JOB_RUN_CLEARANCE_REQUEST, payload, trustAllCertificates, null, proxyConfig);
        JobRunClearanceResponse jobRunClearanceResponse = mapper.readValue(genericResponse.getPayload(), JobRunClearanceResponse.class);
        return jobRunClearanceResponse.getRunIds();
    }
}
