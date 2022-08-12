package io.levelops.plugins.commons.service;

import io.levelops.plugins.commons.models.PluginVersion;
import io.levelops.plugins.commons.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.levelops.plugins.commons.plugins.Common.PLUGIN_VERSION_FILE;
import static io.levelops.plugins.commons.plugins.Common.UTF_8;

public class PluginVersionStorageService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final File expandedLevelOpsPluginDir;
    private final File dataDirectoryWithVersion;
    private final PluginVersionService pluginVersionService;


    public PluginVersionStorageService(File expandedLevelOpsPluginDir, File dataDirectoryWithVersion, PluginVersionService pluginVersionService) {
        this.expandedLevelOpsPluginDir = expandedLevelOpsPluginDir;
        this.dataDirectoryWithVersion = dataDirectoryWithVersion;
        this.pluginVersionService = pluginVersionService;
    }

    private boolean shouldUpdatePluginVersionFileInExpandedDir (File pluginVersionFile, PluginVersion newPluginVersion) {
        if(!pluginVersionFile.exists()) {
            LOGGER.log(Level.FINEST, "pluginVersionFile does not exist, will update pluginVersionFile = " + pluginVersionFile.getAbsolutePath());
            return true;
        }
        String pluginVersionString = null;
        try {
            pluginVersionString = new String(Files.readAllBytes(pluginVersionFile.toPath()), UTF_8);
            LOGGER.log(Level.FINEST, "pluginVersionString = {0}", pluginVersionString);
        } catch (IOException e) {
            LOGGER.log(Level.FINEST, "Error reading pluginVersionFile " + pluginVersionFile.getAbsolutePath() , e);
            return true;
        }
        PluginVersion existing = pluginVersionService.parsePluginVersionString(pluginVersionString);
        LOGGER.log(Level.FINEST, "existing = {0}", existing);
        boolean shouldUpdate = ((newPluginVersion.compareTo(existing)) > 0);
        LOGGER.log(Level.FINEST, "shouldUpdate = {0}", shouldUpdate);
        return shouldUpdate;
    }
    private void persistPluginVersionInExpandedDir(PluginVersion pluginVersion) {
        try {
            FileUtils.createDirectoryRecursively(expandedLevelOpsPluginDir);
        } catch (IOException e) {
            LOGGER.log(Level.FINEST, "Cannot create expandedLevelOpsPluginDir", e);
            return;
        }
        File pluginVersionFile = new File(expandedLevelOpsPluginDir, PLUGIN_VERSION_FILE);
        if(!shouldUpdatePluginVersionFileInExpandedDir(pluginVersionFile, pluginVersion)) {
            LOGGER.log(Level.FINEST, "shouldUpdatePluginVersionFileInExpandedDir is false, will not update plugin version file");
            return;
        }
        try {
            Files.write(pluginVersionFile.toPath(), pluginVersion.getVersion().getBytes(UTF_8));
            LOGGER.log(Level.FINEST, "Successfully wrote plugin version to pluginVersionFile = " + pluginVersionFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.log(Level.FINEST, "Error writing plugin version to pluginVersionFile = " + pluginVersionFile.getAbsolutePath(), e);
        }
    }
    private void persistPluginVersionInDataDirWithVersion(PluginVersion pluginVersion){
        File pluginVersionFile = new File(dataDirectoryWithVersion, PLUGIN_VERSION_FILE);
        try {
            Files.write(pluginVersionFile.toPath(), pluginVersion.getVersion().getBytes(UTF_8));
            LOGGER.log(Level.FINEST, "Successfully wrote plugin version to pluginVersionFile = " + pluginVersionFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.log(Level.FINEST, "Error writing plugin version to pluginVersionFile = " + pluginVersionFile.getAbsolutePath(), e);
        }
    }

    public void persistPluginVersion(Class clazz) {
        LOGGER.log(Level.FINEST, "persistPluginVersion starting");
        PluginVersion pluginVersion = pluginVersionService.getPluginVersion(clazz);
        LOGGER.log(Level.FINEST, "persistPluginVersionInExpandedDir starting");
        persistPluginVersionInExpandedDir(pluginVersion);
        LOGGER.log(Level.FINEST, "persistPluginVersionInExpandedDir completed");
        persistPluginVersionInDataDirWithVersion(pluginVersion);
        LOGGER.log(Level.FINEST, "persistPluginVersion completed");
    }
}
