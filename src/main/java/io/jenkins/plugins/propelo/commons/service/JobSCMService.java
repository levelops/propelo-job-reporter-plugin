package io.jenkins.plugins.propelo.commons.service;

import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobSCMService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final JenkinsConfigSCMService.SCMResult EMPTY_RESULT = new JenkinsConfigSCMService.SCMResult(null, null);

    private final JenkinsConfigSCMService jenkinsConfigSCMService;

    public JobSCMService(JenkinsConfigSCMService jenkinsConfigSCMService) {
        this.jenkinsConfigSCMService = jenkinsConfigSCMService;
    }

    public JenkinsConfigSCMService.SCMResult parseSCMConfigForJob(File hudsonHome, String jobFullName) {
        if((hudsonHome == null) || (StringUtils.isBlank(jobFullName))) {
            LOGGER.log(Level.FINEST, "hudsonHome is null or jobFullName is null or empty, return empty scm config!");
            return EMPTY_RESULT;
        }
        File jobConfigFile = Paths.get(hudsonHome.getAbsolutePath(), "jobs", jobFullName, "config.xml").toFile();
        LOGGER.log(Level.FINEST, "jobConfigFile = {0}", jobConfigFile);

        if(!jobConfigFile.exists()) {
            LOGGER.log(Level.FINEST, "scmConfigFile does not exist, return empty scm config {0}", jobConfigFile);
            return EMPTY_RESULT;
        }
        Optional<JenkinsConfigSCMService.SCMResult> scmResultOptional = null;
        try {
            scmResultOptional = jenkinsConfigSCMService.parseSCMData(jobConfigFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.log(Level.WARNING, "Error parsing SCM Config from file " + jobConfigFile.getAbsolutePath(), e);
            return EMPTY_RESULT;
        }
        JenkinsConfigSCMService.SCMResult scmResult = scmResultOptional.or(EMPTY_RESULT);
        LOGGER.log(Level.FINEST, "scmResult = {0}", scmResult);
        return scmResult;
    }
}
