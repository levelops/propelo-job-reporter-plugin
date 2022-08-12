package io.levelops.plugins.commons.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.levelops.plugins.commons.models.jenkins.saas.CheckRunbookRunsStatusResponse;
import io.levelops.plugins.commons.models.jenkins.saas.GenericResponse;
import io.levelops.plugins.commons.models.jenkins.saas.JobRunClearanceResponse;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.Proxy;
import java.util.List;
import java.util.logging.Logger;

public class RunbookRunsStatusCheckService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final String RUNBOOK_RUNS_STATUS_CHECK_REQUEST = "RunbookRunsStatusCheckRequest";

    private final String apiUrl;
    private final ObjectMapper mapper;

    public RunbookRunsStatusCheckService(String apiUrl, ObjectMapper mapper) {
        this.apiUrl = apiUrl;
        this.mapper = mapper;
    }

    public List<CheckRunbookRunsStatusResponse> checkRunbookRunsStatus(final String apiKey, List<String> runIds, final boolean trustAllCertificates, final ProxyConfigService.ProxyConfig proxyConfig) throws IOException {
        JobRunClearanceResponse payloadObj = new JobRunClearanceResponse(runIds);

        String payload = null;
        try {
            payload = mapper.writeValueAsString(payloadObj);
        } catch (JsonProcessingException e) {
            throw new IOException("Error converting JobRunClearanceResponse to json!!", e);
        }

        GenericRequestService genericRequestService = new GenericRequestService(apiUrl, mapper);
        GenericResponse genericResponse = genericRequestService.performGenericRequest(apiKey, RUNBOOK_RUNS_STATUS_CHECK_REQUEST, payload, trustAllCertificates, null, proxyConfig);

        List<CheckRunbookRunsStatusResponse> runResponses = mapper.readValue(genericResponse.getPayload(), mapper.getTypeFactory().constructCollectionLikeType(List.class, CheckRunbookRunsStatusResponse.class));
        return runResponses;
    }


}
