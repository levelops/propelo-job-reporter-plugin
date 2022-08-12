package io.levelops.plugins.commons.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.levelops.plugins.commons.models.blue_ocean.Job;
import io.levelops.plugins.commons.models.blue_ocean.JobRun;
import io.levelops.plugins.commons.models.blue_ocean.Node;
import io.levelops.plugins.commons.models.blue_ocean.Organization;
import io.levelops.plugins.commons.models.blue_ocean.Step;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.levelops.plugins.commons.plugins.Common.UTF_8;


public class BlueOceanRestClient {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private final String COMMON = "blue/rest/organizations/";
    private final String BLUE = "blue";
    private final String REST = "rest";
    private final String ORGANIZATIONS = "organizations";

    private final String baseUrl;
    private final String userName;
    private final String apiToken;
    private final boolean trustAllCertificates;
    private final ObjectMapper mapper;
    private final ProxyConfigService.ProxyConfig proxyConfig;

    public BlueOceanRestClient(String baseUrl, String userName, String apiToken, boolean trustAllCertificates, ObjectMapper mapper, final ProxyConfigService.ProxyConfig proxyConfig) {
        this.baseUrl = baseUrl;
        this.userName = userName;
        this.apiToken = apiToken;
        this.trustAllCertificates = trustAllCertificates;
        this.mapper = mapper;
        this.proxyConfig = proxyConfig;
    }

    private void addHeaders(Request.Builder requestBldr){
        String credentials = userName + ":" + apiToken;
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes(UTF_8)), UTF_8);
        requestBldr.header("Accept", "application/json")
                .header("Authorization", "Basic " + encodedCredentials);
    }

    private String executeGetRequests(HttpUrl url) throws IOException {
        LOGGER.log(Level.FINER, "url = {0}", url);
        Request.Builder requestBldr = new Request.Builder()
                .url(url)
                .get();
        addHeaders(requestBldr);
        Request request = requestBldr.build();

        String responseString = null;
        OkHttpClient client = HttpClientFactory.createHttpClient(trustAllCertificates, proxyConfig, url);
        Call call = client.newCall(request);
        Response httpResponse = null;
        ResponseBody body = null;
        try {
            httpResponse = call.execute();
            body = httpResponse.body();
            if (!httpResponse.isSuccessful() || body == null) {
                String errorBody = null;
                if (body != null) {
                    try {
                        errorBody = body.string();
                    } catch (Exception e) {
                        errorBody = e.toString();
                    }
                }
                throw new IOException("Response not successful: " + httpResponse.code() + " Error Body: " + errorBody);
            }
            responseString = body.string();
        } finally {
            if (body != null) {
                body.close();
            }
            if (httpResponse != null){
                httpResponse.close();
            }
        }
        return responseString;
    }

    // region Org
    public List<Organization> getOrganizations() throws IOException {
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(COMMON)
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            Collections.emptyList();
        }
        List<Organization> organizations = mapper.readValue(responseString, mapper.getTypeFactory().constructCollectionType(List.class, Organization.class));
        return organizations;
    }
    // endregion

    // region Job
    public Job getJob(String orgName, String pipelineJobPath) throws IOException {
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(COMMON)
                .addPathSegment(orgName)
                .addPathSegment("pipelines")
                .addPathSegments(pipelineJobPath)
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        Job job = mapper.readValue(responseString, Job.class);
        return job;
    }

    public Job getJobUsingLink(String jobLinkHref) throws IOException {
        if(StringUtils.isBlank(jobLinkHref)) {
            return null;
        }
        String linkHrefSanitized = sanitizeLinkHref(jobLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(linkHrefSanitized)
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        Job job = mapper.readValue(responseString, Job.class);
        return job;
    }
    // endregion

    // region Job Run
    public JobRun getJobRun(String orgName, String pipelineJobPath, Long jobRunNumber) throws IOException {
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(COMMON)
                .addPathSegment(orgName)
                .addPathSegment("pipelines")
                .addPathSegments(pipelineJobPath)
                .addPathSegment("runs")
                .addPathSegment(jobRunNumber.toString())
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        JobRun jobRun = mapper.readValue(responseString, JobRun.class);
        return jobRun;
    }

    public JobRun getJobRunUsingLink(String jobRunLinkHref) throws IOException {
        if(StringUtils.isBlank(jobRunLinkHref)) {
            return null;
        }
        String linkHrefSanitized = sanitizeLinkHref(jobRunLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(linkHrefSanitized)
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        JobRun jobRun = mapper.readValue(responseString, JobRun.class);
        return jobRun;
    }
    // endregion

    // region Job Run Log
    public String getJobRunLogUsingLink(String jobRunLinkHref) throws IOException {
        if(StringUtils.isBlank(jobRunLinkHref)) {
            return null;
        }
        String linkHrefSanitized = sanitizeLinkHref(jobRunLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(linkHrefSanitized)
                .addPathSegment("log")
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        return responseString;
    }
    // endregion

    // region Job Run Nodes
    public List<Node> getJobRunNodes(String orgName, String pipelineJobPath, Long jobRunNumber) throws IOException {
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(COMMON)
                .addPathSegment(orgName)
                .addPathSegment("pipelines")
                .addPathSegments(pipelineJobPath)
                .addPathSegment("runs")
                .addPathSegment(jobRunNumber.toString())
                .addPathSegment("nodes")
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        List<Node> nodes = mapper.readValue(responseString, mapper.getTypeFactory().constructCollectionType(List.class, Node.class));
        return nodes;
    }

    private String sanitizeLinkHref (String linkHref) {
        String linkHrefSanitized = linkHref;
        if(linkHref.startsWith("/")) {
            linkHrefSanitized = linkHref.substring(1);
        }
        return linkHrefSanitized;
    }

    public List<Node> getJobRunNodesUsingJobRunLink(String jobRunLinkHref) throws IOException {
        if(StringUtils.isBlank(jobRunLinkHref)) {
            return null;
        }
        String linkHrefSanitized = sanitizeLinkHref(jobRunLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(linkHrefSanitized)
                .addPathSegment("nodes")
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        List<Node> nodes = mapper.readValue(responseString, mapper.getTypeFactory().constructCollectionType(List.class, Node.class));
        return nodes;
    }

    public List<Node> getJobRunNodesUsingJobRunNodesLink(String jobRunNodesLinkHref) throws IOException {
        if(StringUtils.isBlank(jobRunNodesLinkHref)) {
            return null;
        }
        String linkHrefSanitized = sanitizeLinkHref(jobRunNodesLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(linkHrefSanitized)
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        List<Node> nodes = mapper.readValue(responseString, mapper.getTypeFactory().constructCollectionType(List.class, Node.class));
        return nodes;
    }
    // endregion

    // region Job Run Node Log
    public String getJobRunNodeLog(String orgName, String pipelineJobPath, Long jobRunNumber, String nodeNumber) throws IOException {
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(COMMON)
                .addPathSegment(orgName)
                .addPathSegment("pipelines")
                .addPathSegments(pipelineJobPath)
                .addPathSegment("runs")
                .addPathSegment(jobRunNumber.toString())
                .addPathSegment("nodes")
                .addPathSegment(nodeNumber)
                .addPathSegment("log")
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        return responseString;
    }

    public String getJobRunNodeLogUsingJobRunLink(String jobRunLinkHref, String nodeNumber) throws IOException {
        if(StringUtils.isBlank(jobRunLinkHref)) {
            return null;
        }
        String linkHrefSanitized = sanitizeLinkHref(jobRunLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(linkHrefSanitized)
                .addPathSegment("nodes")
                .addPathSegment(nodeNumber)
                .addPathSegment("log")
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        return responseString;
    }

    public String getJobRunNodeLogUsingJobRunNodeLink(String jobRunNodeLinkHref) throws IOException {
        if(StringUtils.isBlank(jobRunNodeLinkHref)) {
            return null;
        }
        String linkHrefSanitized = sanitizeLinkHref(jobRunNodeLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(linkHrefSanitized)
                .addPathSegment("log")
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        return responseString;
    }
    // endregion

    // region Job Run Node Steps
    public List<Step> getJobRunNodeStepsUsingJobRunNodeLink(String jobRunNodeLinkHref) throws IOException {
        if(StringUtils.isBlank(jobRunNodeLinkHref)) {
            return null;
        }
        String jobRunNodeLinkHrefSanitized = sanitizeLinkHref(jobRunNodeLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(jobRunNodeLinkHrefSanitized)
                .addPathSegment("steps")
                .addQueryParameter("limit", "1000")
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        List<Step> steps = mapper.readValue(responseString, mapper.getTypeFactory().constructCollectionType(List.class, Step.class));
        return steps;
    }
    // endregion

    // region Job Run Node Step Log
    public String getJobRunNodeStepLogUsingJobRunNodeStepLink(String jobRunNodeStepLinkHref) throws IOException {
        if(StringUtils.isBlank(jobRunNodeStepLinkHref)) {
            return null;
        }
        String jobRunNodeStepLinkHrefSanitized = sanitizeLinkHref(jobRunNodeStepLinkHref);
        HttpUrl url = HttpUrl.get(baseUrl)
                .newBuilder()
                .addPathSegments(jobRunNodeStepLinkHrefSanitized)
                .addPathSegment("log")
                .build();
        String responseString = executeGetRequests(url);
        if(StringUtils.isBlank(responseString)) {
            return null;
        }
        return responseString;
    }
    // endregion
}
