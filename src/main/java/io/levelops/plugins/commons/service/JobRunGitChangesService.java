package io.levelops.plugins.commons.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.model.Run;
import io.levelops.plugins.commons.models.JobRunDetail;
import io.levelops.plugins.commons.utils.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.levelops.plugins.commons.plugins.Common.JOBS_DATA_DIR_NAME;
import static io.levelops.plugins.commons.plugins.Common.RUN_GIT_CHANGES_HISTORY_FILE;
import static io.levelops.plugins.commons.plugins.Common.UTF_8;

public class JobRunGitChangesService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final Pattern CHANGE_FILE_NAME_PATTERN = Pattern.compile("changelog.*\\.xml");
    private final ObjectMapper objectMapper;
    private final File dataDirectoryWithRotation;

    public JobRunGitChangesService(ObjectMapper objectMapper, File dataDirectoryWithRotation) {
        this.objectMapper = objectMapper;
        this.dataDirectoryWithRotation = dataDirectoryWithRotation;
    }

    protected File getChangeLogFile(File buildDirectory){
        if (buildDirectory == null){
            LOGGER.log(Level.FINEST, "buildDirectory is null!");
            return null;
        }
        if(! buildDirectory.exists()){
            LOGGER.log(Level.FINEST, "buildDirectory does not exist! " + buildDirectory.getAbsolutePath());
            return null;
        }
        File defaultJobRunGitChangesFile = new File(buildDirectory, "changelog.xml");
        LOGGER.log(Level.FINEST, "defaultJobRunGitChangesFile = " + defaultJobRunGitChangesFile.getAbsolutePath());
        if (defaultJobRunGitChangesFile.exists()){
            LOGGER.log(Level.FINEST, "defaultJobRunGitChangesFile exists : " + defaultJobRunGitChangesFile.getAbsolutePath());
            return defaultJobRunGitChangesFile;
        }
        File[] children = buildDirectory.listFiles();
        if (children == null){
            LOGGER.log(Level.FINEST, "buildDirectory children is null!");
            return null;
        }
        for(File currentChild : children){
            if (currentChild == null){
                continue;
            }
            if(!currentChild.isFile()){
                continue;
            }
            String fileName = currentChild.getName();
            if(StringUtils.isBlank(fileName)){
                continue;
            }
            final Matcher matcher = CHANGE_FILE_NAME_PATTERN.matcher(fileName);
            if(matcher.matches()){
                LOGGER.log(Level.FINEST, "currentChild matches change file name pattern = " + currentChild.getAbsolutePath());
                return currentChild;
            }
        }
        LOGGER.log(Level.FINEST, "change file not found!");
        return null;
    }

    public List<String> parseGitCommitsForRun(Run build, JobRunDetail jobRunDetail){
        File jobRunGitChangesFile = getChangeLogFile(build.getRootDir());
        if (jobRunGitChangesFile == null){
            LOGGER.log(Level.FINEST, "jobRunGitChangesFile is null");
            return null;
        }
        JobRunGitChangesParserService parserService = new JobRunGitChangesParserService();
        List<String> commitIds = parserService.parseGitChangeCommitIds(jobRunGitChangesFile);
        return commitIds;
    }
    public List<String> parseAndSaveGitCommitsForRun(Run build, JobRunDetail jobRunDetail){
        List<String> commitIds = parseGitCommitsForRun(build, jobRunDetail);
        if((commitIds == null) || (commitIds.size() == 0)){
            LOGGER.finest("JobRunGitChangesService changeCommitIds is null or empty!!");
            return commitIds;
        }
        File runGitChangesHistoryFile = null;
        try {
            runGitChangesHistoryFile = buildAndCreateRunGitChangesFilePath(jobRunDetail.getJobFullName());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error creating run git changes history file !!", e);
            return commitIds;
        }

        long buildNumber = build.getNumber();
        JobRunGitChanges jobRunGitChanges = new JobRunGitChanges(buildNumber, commitIds);
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(jobRunGitChanges);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "Error serializing run git changes!!", e);
            return commitIds;
        }
        try {
            Files.write(runGitChangesHistoryFile.toPath(), payload.getBytes(UTF_8));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error writing run git changes!!", e);
            return commitIds;
        }
        return commitIds;
    }

    private File buildAndCreateRunGitChangesFilePath(String jobFullName) throws IOException {
        File buildRunMessageFile = Paths.get(dataDirectoryWithRotation.getAbsolutePath(), JOBS_DATA_DIR_NAME, jobFullName, RUN_GIT_CHANGES_HISTORY_FILE).toFile();
        return FileUtils.createFileRecursively(buildRunMessageFile);
    }

    public static class JobRunGitChanges {
        @JsonProperty("build_number")
        private long buildNumber;
        @JsonProperty("commit_ids")
        private List<String> commitIds;

        public JobRunGitChanges() {
        }

        public JobRunGitChanges(long buildNumber, List<String> commitIds) {
            this.buildNumber = buildNumber;
            this.commitIds = commitIds;
        }

        public long getBuildNumber() {
            return buildNumber;
        }

        public void setBuildNumber(long buildNumber) {
            this.buildNumber = buildNumber;
        }

        public List<String> getCommitIds() {
            return commitIds;
        }

        public void setCommitIds(List<String> commitIds) {
            this.commitIds = commitIds;
        }
    }



}
