package io.jenkins.plugins.propelo.commons.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import io.jenkins.plugins.propelo.commons.utils.FileUtils;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.jenkins.plugins.propelo.commons.plugins.Common.CONF_SCM_FILE;
import static io.jenkins.plugins.propelo.commons.plugins.Common.UTF_8;

public class JobSCMStorageService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final ObjectMapper objectMapper;
    private final File dataDirectoryWithRotation;
    private final JenkinsConfigSCMService jenkinsConfigSCMService;

    public JobSCMStorageService(ObjectMapper objectMapper, File dataDirectoryWithRotation) {
        this(objectMapper, dataDirectoryWithRotation, new JenkinsConfigSCMService());
    }
    public JobSCMStorageService(ObjectMapper objectMapper, File dataDirectoryWithRotation, JenkinsConfigSCMService jenkinsConfigSCMService) {
        this.objectMapper = objectMapper;
        this.dataDirectoryWithRotation = dataDirectoryWithRotation;
        this.jenkinsConfigSCMService = jenkinsConfigSCMService;
    }

    public Optional<JenkinsConfigSCMService.SCMResult> readConfigSCMFile(File configSCMFile){
        if(!configSCMFile.exists()){
            return Optional.absent();
        }
        try {
            String scmDataString = new String(Files.readAllBytes(configSCMFile.toPath()), UTF_8);
            JenkinsConfigSCMService.SCMResult scmResult = objectMapper.readValue(scmDataString, JenkinsConfigSCMService.SCMResult.class);
            return Optional.fromNullable(scmResult);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "IOException in JobSCMStorageService.readConfigSCMFile!", e);
            return Optional.absent();
        }
    }

    public void logSCM(final File hudsonHome, File configFile) {
        if(hudsonHome == null){
            LOGGER.log(Level.SEVERE, "Hudson Home is null, aborting logging SCM!");
            return;
        }
        if (!configFile.exists()) {
            LOGGER.log(Level.FINEST, "configFile does not exist : " + configFile.getAbsolutePath());
            return;
        }
        File storageFile = null;
        try {
            storageFile = buildConfigSCMFile(hudsonHome, configFile);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error building config scm file!", e);
            return;
        }
        Optional<JenkinsConfigSCMService.SCMResult> scmResultOptional = null;
        try {
            scmResultOptional = jenkinsConfigSCMService.parseSCMData(configFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.log(Level.WARNING, "Error parsing SCM Config from file " + configFile.getAbsolutePath(), e);
            return;
        }
        if(!scmResultOptional.isPresent()){
            return;
        }
        try {
            FileUtils.createFileRecursively(storageFile);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error creating storage file recursively" + storageFile.getAbsolutePath(), e);
            return;
        }
        String scmData = null;
        try {
            scmData = objectMapper.writeValueAsString(scmResultOptional.get());
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "Error serializing SCM Result", e);
            return;
        }
        try {
            Files.write(storageFile.toPath(), scmData.getBytes(UTF_8));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error writing scm result to storage file " + storageFile.getAbsolutePath(), e);
            return;
        }
    }

    private File buildConfigSCMFile(final File hudsonHome, final File configFile) throws IOException {
        LOGGER.finest("JobSCMStorageService.getHistoryDir starting");
        final String configRootDir = configFile.getParent();
        final String jenkinsRootDir = hudsonHome.getPath();
        LOGGER.log(Level.FINEST, "JobSCMStorageService.buildConfigSCMFile configRootDir = {0}, jenkinsRootDir = {1}", new Object[]{configRootDir, jenkinsRootDir});

        if (!configRootDir.startsWith(jenkinsRootDir)) {
            throw new IOException(
                    "Trying to get history dir for object outside of Jenkins: "
                            + configFile);
        }
        // if the file is stored directly under JENKINS_ROOT, it's a system
        // config
        // so create a distinct directory
        String underRootDir = null;
        if (configRootDir.equals(jenkinsRootDir)) {
            LOGGER.finest("ConfigHistoryService.buildConfigSCMFile configRootDir equals jenkinsRootDir");
            final String fileName = configFile.getName();
            underRootDir = fileName.substring(0, fileName.lastIndexOf('.'));
        } else {
            underRootDir = configRootDir.substring(jenkinsRootDir.length()+ 1);
        }
        LOGGER.finest("JobSCMStorageService.buildConfigSCMFile underRootDir = " + underRootDir);
        Path configHistoryFilePath = Paths.get(dataDirectoryWithRotation.getAbsolutePath(), underRootDir, CONF_SCM_FILE);
        final File configHistoryFile = configHistoryFilePath.toFile();
        LOGGER.finest("JobSCMStorageService.buildConfigSCMFile configHistoryFile = " + configHistoryFile.toString());
        return configHistoryFile;
    }
    public void logSCMForAllJobs(File hudsonHome) {
        File jobsDirectory = new File(hudsonHome, "jobs");
        if (!jobsDirectory.exists()){
            return;
        }
        Queue<File> dirs = new LinkedList<>();
        dirs.offer(jobsDirectory);
        while (dirs.peek() != null) {
            File currentDir = dirs.poll();
            File currentJobConfigFile = new File(currentDir, "config.xml");
            logSCM(hudsonHome, currentJobConfigFile);

            File[] children = currentDir.listFiles();
            if(children == null) {
                continue;
            }
            for(File currentChild : children) {
                if(! currentChild.isDirectory()){
                    continue;
                }
                if("builds".equals(currentChild.getName())) {
                    LOGGER.log(Level.FINEST,"Current dir is builds dir, will not traverse, {0}", currentChild);
                    continue;
                }
                dirs.offer(currentChild);
            }
        }
    }
}
