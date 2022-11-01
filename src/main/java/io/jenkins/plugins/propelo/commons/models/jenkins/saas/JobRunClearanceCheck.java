package io.jenkins.plugins.propelo.commons.models.jenkins.saas;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenkins.plugins.propelo.commons.models.JobRunParam;

import java.util.List;

public class JobRunClearanceCheck {
    @JsonProperty("job_name")
    private String jobName;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("job_run_params")
    private List<JobRunParam> jobRunParams;
    @JsonProperty("repo_url")
    private String repoUrl;
    @JsonProperty("scm_user_id")
    private String scmUserId;

    @JsonProperty("jenkins_instance_guid")
    private String jenkinsInstanceGuid;
    @JsonProperty("jenkins_instance_name")
    private String jenkinsInstanceName;

    @JsonProperty("upstream_job_run")
    private JobRunClearanceCheck upstreamJobRun;

    public JobRunClearanceCheck(String jobName, String userId, List<JobRunParam> jobRunParams, String repoUrl, String scmUserId, String jenkinsInstanceGuid, String jenkinsInstanceName, JobRunClearanceCheck upstreamJobRun) {
        this.jobName = jobName;
        this.userId = userId;
        this.jobRunParams = jobRunParams;
        this.repoUrl = repoUrl;
        this.scmUserId = scmUserId;
        this.jenkinsInstanceGuid = jenkinsInstanceGuid;
        this.jenkinsInstanceName = jenkinsInstanceName;
        this.upstreamJobRun = upstreamJobRun;
    }

    public String getJobName() {
        return jobName;
    }

    public String getUserId() {
        return userId;
    }

    public List<JobRunParam> getJobRunParams() {
        return jobRunParams;
    }

    public JobRunClearanceCheck getUpstreamJobRun() {
        return upstreamJobRun;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public String getScmUserId() {
        return scmUserId;
    }

    public String getJenkinsInstanceGuid() {
        return jenkinsInstanceGuid;
    }

    public String getJenkinsInstanceName() {
        return jenkinsInstanceName;
    }
}
