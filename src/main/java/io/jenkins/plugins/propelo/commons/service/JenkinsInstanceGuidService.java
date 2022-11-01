package io.jenkins.plugins.propelo.commons.service;

import io.jenkins.plugins.propelo.commons.utils.FileUtils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.jenkins.plugins.propelo.commons.plugins.Common.JENKINS_INSTANCE_GUID_FILE;
import static io.jenkins.plugins.propelo.commons.plugins.Common.UTF_8;


public class JenkinsInstanceGuidService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final File expandedLevelOpsPluginDir;
    private final File dataDirectory;
    private final File dataDirectoryWithVersion;

    public JenkinsInstanceGuidService(File expandedLevelOpsPluginDir, File dataDirectory, File dataDirectoryWithVersion) {
        this.expandedLevelOpsPluginDir = expandedLevelOpsPluginDir;
        this.dataDirectory = dataDirectory;
        this.dataDirectoryWithVersion = dataDirectoryWithVersion;
    }

    private void createJenkinsInstanceGuidFile(File jenkinsInstanceGuidFile, String jenkinsInstanceGuid) throws IOException {
        try {
            Files.write(jenkinsInstanceGuidFile.toPath(), jenkinsInstanceGuid.getBytes(UTF_8), StandardOpenOption.CREATE);
        } catch (IOException e) {
            if(!jenkinsInstanceGuidFile.exists()){
                throw e;
            }
        }
    }

    private String readJenkinsInstanceGuidFileIfExists(File jenkinsInstanceGuidFile)  {
        if(! jenkinsInstanceGuidFile.exists()) {
            LOGGER.log(Level.FINEST, "jenkinsInstanceGuidFile does not exist {0}", jenkinsInstanceGuidFile);
            return null;
        }
        try {
            return new String(Files.readAllBytes(jenkinsInstanceGuidFile.toPath()), UTF_8);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "JenkinsInstanceGuidService.readJenkinsInstanceGuidFile Error reading JenkinsInstanceGuidFile!", e);
            return null;
        }
    }

    public String createOrReturnInstanceGuid()  {
        LOGGER.finest("JenkinsInstanceGuidService createOrReturnInstanceGuid starting");
        //Create Plugin expandedPathDir if does not exist
        try {
            FileUtils.createDirectoryRecursively(expandedLevelOpsPluginDir);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "JenkinsInstanceGuidService.createOrReturnInstanceGuid Error creating expandedLevelOpsPluginDir!", e);
            return null;
        }

        //Read jenkins instance file in expandedPathDir if exists
        File jenkinsInstanceGuidFileInExpandedLevelOpsPluginDir = new File(expandedLevelOpsPluginDir, JENKINS_INSTANCE_GUID_FILE);
        String jenkinsInstanceGuid = readJenkinsInstanceGuidFileIfExists(jenkinsInstanceGuidFileInExpandedLevelOpsPluginDir);
        //If jenkins instance file in expandedPathDir has instance guid return it
        if(StringUtils.isNotBlank(jenkinsInstanceGuid)) {
            return jenkinsInstanceGuid;
        }

        //Check if file exists in data dir (old data dir without version)
        File jenkinsInstanceGuidFileInDataDirectory = new File(dataDirectory, JENKINS_INSTANCE_GUID_FILE);
        String jenkinsInstanceGuidFromDataDirectory = readJenkinsInstanceGuidFileIfExists(jenkinsInstanceGuidFileInDataDirectory);

        //If jenkinsInstanceGuid from Data Directory was not blank use it else use new guid
        jenkinsInstanceGuid = StringUtils.isNotBlank(jenkinsInstanceGuidFromDataDirectory) ? jenkinsInstanceGuidFromDataDirectory : UUID.randomUUID().toString();

        //Write jenkinsInstanceGuid to jenkins instance file in expandedPathDir
        try {
            createJenkinsInstanceGuidFile(jenkinsInstanceGuidFileInExpandedLevelOpsPluginDir, jenkinsInstanceGuid);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "JenkinsInstanceGuidService.createOrReturnInstanceGuid Error creating JenkinsInstanceGuidFile!", e);
            return null;
        }

        //Read the jenkinsInstanceGuid from the jenkins instance file in expandedPathDir
        jenkinsInstanceGuid = readJenkinsInstanceGuidFileIfExists(jenkinsInstanceGuidFileInExpandedLevelOpsPluginDir);
        //Return jenkinsInstanceGuid
        LOGGER.finest("jenkinsInstanceGuid = " + jenkinsInstanceGuid);
        LOGGER.finest("JenkinsInstanceGuidService createOrReturnInstanceGuid ending");
        return jenkinsInstanceGuid;
    }

    public boolean copyJenkinsInstanceGuidFileToDataDirectoryWithVersion() {
        //Create dataDirWithVersion if does not exist
        try {
            FileUtils.createDirectoryRecursively(dataDirectoryWithVersion);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "JenkinsInstanceGuidService.copyJenkinsInstanceGuidFileToDataDirectoryWithVersion Error creating dataDirectoryWithVersion!", e);
            return false;
        }

        //Check if Jenkins Instance file in dataDirWithVersion exists
        File jenkinsInstanceFileInDataDirWithVersion = new File(dataDirectoryWithVersion, JENKINS_INSTANCE_GUID_FILE);
        if(jenkinsInstanceFileInDataDirWithVersion.exists()) {
            LOGGER.log(Level.FINEST, "Jenkins Instance file in data dir with version exists {0}", jenkinsInstanceFileInDataDirWithVersion);
            return true;
        }

        //Read jenkins instance guid from default location (expandedPathDir)
        String jenkinsInstanceGuid = createOrReturnInstanceGuid();
        if(StringUtils.isBlank(jenkinsInstanceGuid)) {
            LOGGER.log(Level.FINEST, "jenkinsInstanceGuid is null or empty, cannot copy to data dir with version");
            return false;
        }

        //Write jenkinsInstanceGuid to jenkins instance file in dataDirWithVersion
        try {
            createJenkinsInstanceGuidFile(jenkinsInstanceFileInDataDirWithVersion, jenkinsInstanceGuid);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "JenkinsInstanceGuidService.createOrReturnInstanceGuid Error creating JenkinsInstanceGuidFile!", e);
            return false;
        }
        return true;
    }
}
