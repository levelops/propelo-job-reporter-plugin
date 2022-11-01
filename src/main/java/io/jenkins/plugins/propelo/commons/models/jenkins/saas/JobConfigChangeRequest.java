package io.jenkins.plugins.propelo.commons.models.jenkins.saas;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenkins.plugins.propelo.commons.models.JobConfigChangeType;

public class JobConfigChangeRequest {
    @JsonProperty("job_name")
    private String jobName;
    @JsonProperty("branch_name")
    private String branchName;
    @JsonProperty("module_name")
    private String moduleName;
    @JsonProperty("job_full_name")
    private String jobFullName;
    @JsonProperty("job_normalized_full_name")
    private String jobNormalizedFullName;

    @JsonProperty("jenkins_instance_guid")
    private String jenkinsInstanceGuid;
    @JsonProperty("jenkins_instance_name")
    private String jenkinsInstanceName;
    @JsonProperty("jenkins_instance_url")
    private String jenkinsInstanceUrl;

    @JsonProperty("repo_url")
    private String repoUrl;
    @JsonProperty("scm_user_id")
    private String scmUserId;

    @JsonProperty("change_type")
    private JobConfigChangeType changeType;
    @JsonProperty("change_time")
    private long changeTime;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("users_name")
    private String usersName;

    public JobConfigChangeRequest(String jobName, String branchName, String moduleName, String jobFullName, String jobNormalizedFullName, String jenkinsInstanceGuid, String jenkinsInstanceName, String jenkinsInstanceUrl, String repoUrl, String scmUserId, JobConfigChangeType changeType, long changeTime, String userId, String usersName) {
        this.jobName = jobName;
        this.branchName = branchName;
        this.moduleName = moduleName;
        this.jobFullName = jobFullName;
        this.jobNormalizedFullName = jobNormalizedFullName;
        this.jenkinsInstanceGuid = jenkinsInstanceGuid;
        this.jenkinsInstanceName = jenkinsInstanceName;
        this.jenkinsInstanceUrl = jenkinsInstanceUrl;
        this.repoUrl = repoUrl;
        this.scmUserId = scmUserId;
        this.changeType = changeType;
        this.changeTime = changeTime;
        this.userId = userId;
        this.usersName = usersName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getJobFullName() {
        return jobFullName;
    }

    public void setJobFullName(String jobFullName) {
        this.jobFullName = jobFullName;
    }

    public String getJobNormalizedFullName() {
        return jobNormalizedFullName;
    }

    public void setJobNormalizedFullName(String jobNormalizedFullName) {
        this.jobNormalizedFullName = jobNormalizedFullName;
    }

    public String getJenkinsInstanceGuid() {
        return jenkinsInstanceGuid;
    }

    public void setJenkinsInstanceGuid(String jenkinsInstanceGuid) {
        this.jenkinsInstanceGuid = jenkinsInstanceGuid;
    }

    public String getJenkinsInstanceName() {
        return jenkinsInstanceName;
    }

    public void setJenkinsInstanceName(String jenkinsInstanceName) {
        this.jenkinsInstanceName = jenkinsInstanceName;
    }

    public String getJenkinsInstanceUrl() {
        return jenkinsInstanceUrl;
    }

    public void setJenkinsInstanceUrl(String jenkinsInstanceUrl) {
        this.jenkinsInstanceUrl = jenkinsInstanceUrl;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getScmUserId() {
        return scmUserId;
    }

    public void setScmUserId(String scmUserId) {
        this.scmUserId = scmUserId;
    }

    public JobConfigChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(JobConfigChangeType changeType) {
        this.changeType = changeType;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }
}
