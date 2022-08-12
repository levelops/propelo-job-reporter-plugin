package io.levelops.plugins.levelops_job_reporter.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Saveable;
import hudson.model.TopLevelItem;
import hudson.model.listeners.SaveableListener;
import io.levelops.plugins.commons.service.JenkinsConfigSCMService;
import io.levelops.plugins.commons.service.JobRunParserService;
import io.levelops.plugins.commons.service.JobSCMStorageService;
import io.levelops.plugins.commons.utils.JsonUtils;
import io.levelops.plugins.levelops_job_reporter.plugins.LevelOpsPluginImpl;
import io.levelops.plugins.levelops_job_reporter.service.ConfigChangeService;
import org.apache.commons.lang.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINER;
import static java.util.logging.Level.FINEST;
import static java.util.logging.Level.SEVERE;

@Extension
public class JobConfigContentChangeListener extends SaveableListener {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final ObjectMapper mapper = JsonUtils.buildObjectMapper();
    private final LevelOpsPluginImpl plugin = LevelOpsPluginImpl.getInstance();

    /** {@inheritDoc} */
    @Override
    public void onChange(final Saveable o, final XmlFile xmlFile) {
        try {
            if ((o == null) || (xmlFile == null)) {
                LOGGER.finest("Incomplete input received");
                return;
            }
            if (!(o instanceof TopLevelItem)) {
                return;
            }
            //We get too many calls here when multibranch project is checked for changes, moving to finer
            LOGGER.finer("JobConfigHistorySaveableListener.onChange started");
            if (plugin.isExpandedLevelOpsPluginPathNullOrEmpty()) {
                LOGGER.log(SEVERE, "LevelOps Plugin Directory is invalid, cannot process config change! path: " + plugin.getLevelOpsPluginPath());
                return;
            }
            if (StringUtils.isBlank(plugin.getLevelOpsApiKey())) {
                LOGGER.log(FINE, "LevelOps Api Key is null or empty, will not collect data");
                return;
            }
            LOGGER.log(FINEST, "JobConfigContentChangeListener onChange {0}", xmlFile.getFile());
            LOGGER.log(FINEST, "In onChange for {0}", o);
            final JobSCMStorageService jobSCMStorageService = new JobSCMStorageService(mapper, plugin.getDataDirectoryWithRotation(), new JenkinsConfigSCMService());
            final JobRunParserService jobRunParserService = new JobRunParserService();
            final ConfigChangeService configChangeService = new ConfigChangeService(plugin, jobSCMStorageService, jobRunParserService, mapper);
            configChangeService.processConfigChange(xmlFile.getFile());
            LOGGER.log(FINER, "onChange for {0} done.", o);
        } catch (Exception e) {
            LOGGER.log(SEVERE, "Error in JobConfigContentChangeListener onChange", e);
        }
    }
}
