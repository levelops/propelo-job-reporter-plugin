package io.levelops.plugins.commons.service;

import io.levelops.plugins.commons.models.PluginVersion;
import org.apache.commons.lang.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PluginVersionService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final Pattern PLUGIN_VERSION_PATTERN = Pattern.compile("(?<major>\\d*)\\.(?<minor>\\d*)\\.(?<patch>\\d*)(-SNAPSHOT)?");

    private int safeIntParse(String input) {
        if(StringUtils.isBlank(input)) {
            return 0;
        }
        try {
            int value = Integer.parseInt(input);
            return value;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getPluginVersionString(Class clazz) {
        String pluginVersionString = clazz.getPackage().getImplementationVersion();
        LOGGER.log(Level.FINEST, "getPluginVersionString completed pluginVersionString = {0}", pluginVersionString);
        return pluginVersionString;
    }

    public PluginVersion parsePluginVersionString(String pluginVersionString) {
        if(StringUtils.isBlank(pluginVersionString)) {
            LOGGER.log(Level.FINEST, "pluginVersion = {0}", PluginVersion.EMPTY_PLUGIN_VERSION);
            return PluginVersion.EMPTY_PLUGIN_VERSION;
        }
        final Matcher matcher = PLUGIN_VERSION_PATTERN.matcher(pluginVersionString);
        if(!matcher.find()) {
            LOGGER.log(Level.FINEST, "pluginVersion = {0}", PluginVersion.EMPTY_PLUGIN_VERSION);
            return PluginVersion.EMPTY_PLUGIN_VERSION;
        }
        int majorVersion = safeIntParse(matcher.group("major"));
        int minorVersion = safeIntParse(matcher.group("minor"));
        int patchVersion = safeIntParse(matcher.group("patch"));
        PluginVersion pluginVersion = new PluginVersion(pluginVersionString, majorVersion, minorVersion, patchVersion);
        LOGGER.log(Level.FINEST, "pluginVersion = {0}", pluginVersion);
        return pluginVersion;
    }

    public PluginVersion getPluginVersion(Class clazz) {
        String pluginVersionString = getPluginVersionString(clazz);
        return parsePluginVersionString(pluginVersionString);
    }
}
