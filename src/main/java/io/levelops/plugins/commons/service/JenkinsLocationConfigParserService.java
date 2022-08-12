package io.levelops.plugins.commons.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.levelops.plugins.commons.models.jenkins.input.JenkinsLocationConfig;
import io.levelops.plugins.commons.utils.XmlUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JenkinsLocationConfigParserService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public JenkinsLocationConfigParserService() {
    }

    //ToDo: Future - Merge from JenkinsConfigService.parseJenkinsLocatorConfig to here.
    public String parseJenkinsInstanceUrl(File hudsonHome) {
        try {
            if (hudsonHome == null) {
                LOGGER.log(Level.FINEST, "hudsonHome is null, jenkins instance url is null");
                return null;
            }
            LOGGER.log(Level.FINEST, "hudsonHome = {0}", hudsonHome);
            File locationConfigurationFile = new File(hudsonHome, JenkinsLocationConfig.LOCATION_CONFIGURATION_FILE_NAME);
            if (!locationConfigurationFile.exists()) {
                LOGGER.log(Level.FINEST, "locationConfigurationFile does not exist, jenkins instance url is null {0}", locationConfigurationFile);
                return null;
            }
            XmlMapper xmlMapper = XmlUtils.getObjectMapper();
            JenkinsLocationConfig jenkinsLocationConfig = null;
            try {
                jenkinsLocationConfig = xmlMapper.readValue(locationConfigurationFile, JenkinsLocationConfig.class);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error parsing Jenkins Location Config!", e);
                return null;
            }
            if (jenkinsLocationConfig == null) {
                LOGGER.log(Level.FINEST, "jenkinsLocationConfig is null, jenkins instance url is null");
                return null;
            }
            String jenkinsInstanceUrl = jenkinsLocationConfig.getJenkinsUrl();
            LOGGER.log(Level.FINEST, "jenkinsInstanceUrl = {0}", jenkinsInstanceUrl);
            return jenkinsInstanceUrl;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing Jenkins Instance Url", e);
            return null;
        }
    }
}
