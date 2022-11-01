package io.jenkins.plugins.propelo.commons.service;

import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.jenkins.plugins.propelo.commons.plugins.Common.UTF_8;


public class JobRunGitChangesParserService {
    Pattern COMMIT_ID_PATTERN = Pattern.compile("^commit ([A-Za-z0-9_]*)$");
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public List<String> parseGitChangeCommitIds(File jobRunGitChangesCommitsFile){
        if(!jobRunGitChangesCommitsFile.exists()){
            return Collections.emptyList();
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(jobRunGitChangesCommitsFile.toPath(), UTF_8);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error reading Job Run Git Changes Commits File " + jobRunGitChangesCommitsFile.getAbsolutePath(), e);
            return Collections.emptyList();
        }
        if(CollectionUtils.isEmpty(lines)){
            return Collections.emptyList();
        }
        List<String> commitIds = new ArrayList<>();
        for(String currentLine : lines){
            if((currentLine == null) || (currentLine.length() == 0)){
                continue;
            }
            Matcher matcher = COMMIT_ID_PATTERN.matcher(currentLine);
            if(!matcher.matches()){
                continue;
            }
            String commitId = matcher.group(1);
            commitIds.add(commitId);
        }
        return commitIds;
    }
}
