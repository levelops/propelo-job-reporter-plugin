package io.jenkins.plugins.propelo.commons.models;

import com.google.common.base.Objects;
import io.jenkins.plugins.propelo.commons.models.blue_ocean.JobRun;

import java.io.File;

public class JobRunCompleteData {
    private JobRun jobRun;
    private File completeDataDirectory;
    private File completeDataZipFile;


    public JobRunCompleteData(JobRun jobRun, File completeDataDirectory, File completeDataZipFile) {
        this.jobRun = jobRun;
        this.completeDataDirectory = completeDataDirectory;
        this.completeDataZipFile = completeDataZipFile;
    }

    public JobRun getJobRun() {
        return jobRun;
    }

    public void setJobRun(JobRun jobRun) {
        this.jobRun = jobRun;
    }

    public File getCompleteDataDirectory() {
        return completeDataDirectory;
    }

    public void setCompleteDataDirectory(File completeDataDirectory) {
        this.completeDataDirectory = completeDataDirectory;
    }

    public File getCompleteDataZipFile() {
        return completeDataZipFile;
    }

    public void setCompleteDataZipFile(File completeDataZipFile) {
        this.completeDataZipFile = completeDataZipFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRunCompleteData that = (JobRunCompleteData) o;
        return Objects.equal(jobRun, that.jobRun) &&
                Objects.equal(completeDataDirectory, that.completeDataDirectory) &&
                Objects.equal(completeDataZipFile, that.completeDataZipFile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jobRun, completeDataDirectory, completeDataZipFile);
    }

    @Override
    public String toString() {
        return "JobRunCompleteData{" +
                "jobRun=" + jobRun +
                ", completeDataDirectory=" + completeDataDirectory +
                ", completeDataZipFile=" + completeDataZipFile +
                '}';
    }
}
