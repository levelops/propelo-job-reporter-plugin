package io.levelops.plugins.commons.service;

import io.levelops.plugins.commons.utils.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.levelops.plugins.commons.plugins.Common.JENKINS_INSTANCE_URL_FILE;
import static io.levelops.plugins.commons.plugins.Common.UTF_8;

public class JenkinsInstanceUrlService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final File dataDirectoryWithVersion;

    public JenkinsInstanceUrlService(File dataDirectoryWithVersion) {
        this.dataDirectoryWithVersion = dataDirectoryWithVersion;
    }

    public void createOrUpdateJenkinsInstanceUrl(String jenkinsUrl){
        LOGGER.finest("JenkinsInstanceUrlService createOrUpdateJenkinsInstanceUrl starting");
        if(StringUtils.isBlank(jenkinsUrl)){
            return;
        }
        try {
            FileUtils.createDirectoryRecursively(dataDirectoryWithVersion);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error creating dataDirectoryWithVersion!", e);
            return;
        }
        File jenkinsInstanceUrlFile = buildJenkinsInstanceUrlFile();
        //Write without any Open Options
        try {
            Files.write(jenkinsInstanceUrlFile.toPath(), jenkinsUrl.getBytes(UTF_8));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error writing to jenkins instance url file!", e);
            return;
        }
    }

    private File buildJenkinsInstanceUrlFile() {
        File jenkinsInstanceUrlFile = Paths.get(dataDirectoryWithVersion.getAbsolutePath(), JENKINS_INSTANCE_URL_FILE).toFile();
        return jenkinsInstanceUrlFile;
    }

    public String readJenkinsInstanceUrl(){
        LOGGER.finest("JenkinsInstanceUrlService readJenkinsInstanceUrl starting");
        if((dataDirectoryWithVersion == null) || (dataDirectoryWithVersion.exists() == false)) {
            LOGGER.log(Level.FINEST, "dataDirectoryWithVersion is null or does not exist!");
            return null;
        }
        File jenkinsInstanceUrlFile = buildJenkinsInstanceUrlFile();
        if((jenkinsInstanceUrlFile == null) || (jenkinsInstanceUrlFile.exists() == false)) {
            LOGGER.log(Level.FINEST, "jenkinsInstanceUrlFile is null or does not exist!");
            return null;
        }
        try {
            return new String(Files.readAllBytes(jenkinsInstanceUrlFile.toPath()), UTF_8);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error reading jenkins instance url file!", e);
            return null;
        }
    }
}
