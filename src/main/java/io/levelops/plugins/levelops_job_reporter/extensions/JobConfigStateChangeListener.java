package io.levelops.plugins.levelops_job_reporter.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.Extension;
import hudson.model.AbstractItem;
import hudson.model.Item;
import hudson.model.listeners.ItemListener;
import io.levelops.plugins.commons.service.JenkinsConfigSCMService;
import io.levelops.plugins.commons.service.JobRunParserService;
import io.levelops.plugins.commons.service.JobSCMStorageService;
import io.levelops.plugins.commons.utils.JsonUtils;
import io.levelops.plugins.levelops_job_reporter.plugins.LevelOpsPluginImpl;
import io.levelops.plugins.levelops_job_reporter.service.ConfigChangeService;
import org.apache.commons.lang.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINEST;

@Extension
public class JobConfigStateChangeListener extends ItemListener {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final ObjectMapper mapper = JsonUtils.buildObjectMapper();
    private final LevelOpsPluginImpl plugin = LevelOpsPluginImpl.getInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreated(Item item) {
        try {
            if (item == null) {
                LOGGER.finest("Incomplete input received");
                return;
            }
            if (!(item instanceof AbstractItem)) {
                return;
            }
            LOGGER.fine("JobConfigStateChangeListener.onCreated started");
            if (plugin.isExpandedLevelOpsPluginPathNullOrEmpty()) {
                LOGGER.log(Level.SEVERE, "Propelo Plugin Directory is invalid, cannot process config create! path: " + plugin.getLevelOpsPluginPath());
                return;
            }
            if (plugin.getLevelOpsApiKey() != null && StringUtils.isBlank(plugin.getLevelOpsApiKey().getPlainText())) {
                LOGGER.log(Level.FINE, "Propelo Api Key is null or empty, will not collect data");
                return;
            }
            AbstractItem abstractItem = (AbstractItem) item;
            LOGGER.log(FINEST, "In onCreated for {0}", abstractItem.getConfigFile());
            final JobSCMStorageService jobSCMStorageService = new JobSCMStorageService(mapper, plugin.getDataDirectoryWithRotation(), new JenkinsConfigSCMService());
            final JobRunParserService jobRunParserService = new JobRunParserService();
            final ConfigChangeService configChangeService = new ConfigChangeService(plugin, jobSCMStorageService, jobRunParserService, mapper);
            configChangeService.processConfigCreate(abstractItem.getConfigFile().getFile());
            LOGGER.log(FINE, "onCreated for {0} done.", abstractItem.toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in JobConfigStateChangeListener.onCreated", e);
        }
    }

    @Override
    public void onLocationChanged(Item item, String oldFullName, String newFullName) {
        try {
            if ((item == null) || (StringUtils.isBlank(oldFullName)) || (StringUtils.isBlank(newFullName))) {
                LOGGER.finest("Incomplete input received");
                return;
            }
            if (!(item instanceof AbstractItem)) {
                return;
            }
            LOGGER.fine("JobConfigStateChangeListener.onLocationChanged started");
            if (plugin.isExpandedLevelOpsPluginPathNullOrEmpty()) {
                LOGGER.log(Level.SEVERE, "LevelOps Plugin Directory is invalid, cannot process config location change! path: " + plugin.getLevelOpsPluginPath());
                return;
            }
            if (plugin.getLevelOpsApiKey() != null && StringUtils.isBlank(plugin.getLevelOpsApiKey().getPlainText())) {
                LOGGER.log(Level.FINE, "LevelOps Api Key is null or empty, will not collect data");
                return;
            }
            AbstractItem abstractItem = (AbstractItem) item;
            final String onLocationChangedDescription = "old full name: " + oldFullName + ", new full name: " + newFullName;
            LOGGER.log(FINEST, "In onLocationChanged for {0}{1}", new Object[]{abstractItem.getConfigFile(), onLocationChangedDescription});
            final JobSCMStorageService jobSCMStorageService = new JobSCMStorageService(mapper, plugin.getDataDirectoryWithRotation(), new JenkinsConfigSCMService());
            final JobRunParserService jobRunParserService = new JobRunParserService();
            final ConfigChangeService configChangeService = new ConfigChangeService(plugin, jobSCMStorageService, jobRunParserService, mapper);
            configChangeService.processConfigRename(abstractItem.getConfigFile().getFile(), oldFullName, newFullName);
            LOGGER.log(FINE, "Completed onLocationChanged for {0}", abstractItem.getConfigFile());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in JobConfigStateChangeListener.onLocationChanged", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Also checks if we have history stored under the old name. If so, copies
     * all history to the folder for new name, and deletes the old history
     * folder.
     */
    @Override
    public void onRenamed(Item item, String oldName, String newName) {
        try {
            if ((item == null) || (StringUtils.isBlank(oldName)) || (StringUtils.isBlank(newName))) {
                LOGGER.finest("Incomplete input received");
                return;
            }
            if (!(item instanceof AbstractItem)) {
                return;
            }
            LOGGER.fine("JobConfigStateChangeListener.onRenamed started");
            if (plugin.isExpandedLevelOpsPluginPathNullOrEmpty()) {
                LOGGER.log(Level.SEVERE, "LevelOps Plugin Directory is invalid, cannot process config rename! path: " + plugin.getLevelOpsPluginPath());
                return;
            }
            if (plugin.getLevelOpsApiKey() != null && StringUtils.isBlank(plugin.getLevelOpsApiKey().getPlainText())) {
                LOGGER.log(Level.FINE, "LevelOps Api Key is null or empty, will not collect data");
                return;
            }
            AbstractItem abstractItem = (AbstractItem) item;
            final String onRenameDesc = " old name: " + oldName + ", new name: "
                    + newName;
            LOGGER.log(FINEST, "In onRenamed for {0}{1}",
                    new Object[]{abstractItem.getConfigFile(), onRenameDesc});
            //switchHistoryDao(item).renameItem(item, oldName, newName);
            LOGGER.log(FINE, "Completed onRename for {0} done.", abstractItem.getConfigFile());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in JobConfigStateChangeListener.onRenamed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleted(Item item) {
        try {
            if (item == null) {
                LOGGER.finest("Incomplete input received");
                return;
            }
            if (!(item instanceof AbstractItem)) {
                return;
            }
            LOGGER.fine("JobConfigStateChangeListener.onDeleted started");
            if (plugin.isExpandedLevelOpsPluginPathNullOrEmpty()) {
                LOGGER.log(Level.SEVERE, "LevelOps Plugin Directory is invalid, cannot process config delete! path: " + plugin.getLevelOpsPluginPath());
                return;
            }
            if (plugin.getLevelOpsApiKey() != null && StringUtils.isBlank(plugin.getLevelOpsApiKey().getPlainText())) {
                LOGGER.log(Level.FINE, "LevelOps Api Key is null or empty, will not collect data");
                return;
            }
            AbstractItem abstractItem = (AbstractItem) item;
            LOGGER.log(FINEST, "In onDeleted for {0}", abstractItem.getConfigFile());
            final JobSCMStorageService jobSCMStorageService = new JobSCMStorageService(mapper, plugin.getDataDirectoryWithRotation(), new JenkinsConfigSCMService());
            final JobRunParserService jobRunParserService = new JobRunParserService();
            final ConfigChangeService configChangeService = new ConfigChangeService(plugin, jobSCMStorageService, jobRunParserService, mapper);
            configChangeService.processConfigDelete(abstractItem.getConfigFile().getFile());
            LOGGER.log(FINE, "onDeleted for {0} done.", abstractItem.getConfigFile());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in JobConfigStateChangeListener.onDeleted", e);
        }
    }
}