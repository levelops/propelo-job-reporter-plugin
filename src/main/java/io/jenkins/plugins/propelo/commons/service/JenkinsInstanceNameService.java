package io.jenkins.plugins.propelo.commons.service;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.jenkins.plugins.propelo.commons.plugins.Common.JENKINS_INSTANCE_NAME_FILE;
import static io.jenkins.plugins.propelo.commons.plugins.Common.UTF_8;


public class JenkinsInstanceNameService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final File dataDirectoryWithVersion;

    public JenkinsInstanceNameService(File dataDirectoryWithVersion) {
        this.dataDirectoryWithVersion = dataDirectoryWithVersion;
    }

    private void createDataDirectoryWithVersionIfNotExists() throws IOException {
        if(dataDirectoryWithVersion.exists()){
            return;
        }
        if(!dataDirectoryWithVersion.mkdirs()){
            throw new IOException("Could not create dataDirectoryWithVersion " + dataDirectoryWithVersion.toString());
        }
    }

    private void createOrUpdateJenkinsInstanceNameFile(File jenkinsInstanceNameFile, String jenkinsInstanceName) throws IOException {
        //Write without any Open Options
        Files.write(jenkinsInstanceNameFile.toPath(), jenkinsInstanceName.getBytes(UTF_8));
    }

    private String readJenkinsInstanceNameFile(File jenkinsInstanceNameFile) throws IOException {
        return new String(Files.readAllBytes(jenkinsInstanceNameFile.toPath()), UTF_8);
    }

    public String createOrUpdateInstanceName(String jenkinsInstanceName)  {
        LOGGER.finest("JenkinsInstanceNameService createOrUpdateInstanceName starting");
        if(StringUtils.isBlank(jenkinsInstanceName)) {
            LOGGER.log(Level.FINEST, "JenkinsInstanceNameService.createOrUpdateInstanceName jenkinsInstanceName is null or empty, not writing instance name file");
            return jenkinsInstanceName;
        }
        try {
            createDataDirectoryWithVersionIfNotExists();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "JenkinsInstanceNameService.createOrUpdateInstanceName Error creating dataDirectoryWithVersion!", e);
            return null;
        }
        File jenkinsInstanceNameFile = Paths.get(dataDirectoryWithVersion.getAbsolutePath(), JENKINS_INSTANCE_NAME_FILE).toFile();
        try {
            createOrUpdateJenkinsInstanceNameFile(jenkinsInstanceNameFile, jenkinsInstanceName);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "JenkinsInstanceNameService.createOrUpdateInstanceName Error upserting jenkinsInstanceNameFile!", e);
            return null;
        }
        String jenkinsInstanceNameRead = null;
        try {
            jenkinsInstanceNameRead = readJenkinsInstanceNameFile(jenkinsInstanceNameFile);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "JenkinsInstanceNameService.createOrUpdateInstanceName Error reading jenkinsInstanceNameFile!", e);
            return null;
        }
        LOGGER.finest("jenkinsInstanceNameRead = " + jenkinsInstanceNameRead);
        LOGGER.finest("JenkinsInstanceNameService createOrUpdateInstanceName ending");
        return jenkinsInstanceNameRead;
    }
}
