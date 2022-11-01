package io.jenkins.plugins.propelo.commons.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.model.Run;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobRunPerforceChangesService {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final Pattern CHANGE_FILE_NAME_PATTERN = Pattern.compile("changelog.*\\.xml");
    private final ObjectMapper objectMapper;

    public JobRunPerforceChangesService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected File getChangeLogFile(File buildDirectory) {
        if (buildDirectory == null) {
            LOGGER.log(Level.FINEST, "buildDirectory is null!");
            return null;
        }
        if (!buildDirectory.exists()) {
            LOGGER.log(Level.FINEST, "buildDirectory does not exist! " + buildDirectory.getAbsolutePath());
            return null;
        }
        File defaultJobRunPerforceChangesFile = new File(buildDirectory, "changelog.xml");
        LOGGER.log(Level.FINEST, "defaultJobRunPerforceChangesFile = " + defaultJobRunPerforceChangesFile.getAbsolutePath());
        if (defaultJobRunPerforceChangesFile.exists()) {
            LOGGER.log(Level.FINEST, "defaultJobRunPerforceChangesFile exists : " + defaultJobRunPerforceChangesFile.getAbsolutePath());
            return defaultJobRunPerforceChangesFile;
        }
        File[] children = buildDirectory.listFiles();
        if (children == null) {
            LOGGER.log(Level.FINEST, "buildDirectory children is null!");
            return null;
        }
        for (File currentChild : children) {
            if (currentChild == null) {
                continue;
            }
            if (!currentChild.isFile()) {
                continue;
            }
            String fileName = currentChild.getName();
            if (StringUtils.isBlank(fileName)) {
                continue;
            }
            final Matcher matcher = CHANGE_FILE_NAME_PATTERN.matcher(fileName);
            if (matcher.matches()) {
                LOGGER.log(Level.FINEST, "currentChild matches change file name pattern = " + currentChild.getAbsolutePath());
                return currentChild;
            }
        }
        LOGGER.log(Level.FINEST, "change file not found!");
        return null;
    }

    public List<String> parsePerforceCommitsForRun(Run build) throws IOException {
        File jobRunPerforceChangesFile = getChangeLogFile(build.getRootDir());
        if (jobRunPerforceChangesFile == null) {
            LOGGER.log(Level.FINEST, "jobRunPerforceChangesFile is null");
            return null;
        }
        JobRunPerforceChangesParserService parserService = new JobRunPerforceChangesParserService();
        List<String> commitIds = parserService.parsePerforceChangeCommitIds(objectMapper, jobRunPerforceChangesFile);
        return commitIds;
    }
}
