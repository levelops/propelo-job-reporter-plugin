package io.jenkins.plugins.propelo.commons.utils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.jenkins.plugins.propelo.commons.plugins.Common.UTF_8;

public class JobUtils {
    private static final java.util.logging.Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static UUID writeLogData(File currentJobRunCompleteDataDirectory, String logData) {
        if (logData == null) {
            return null;
        }
        UUID uuid = UUID.randomUUID();
        LOGGER.log(Level.FINEST, "uuid = {0}", uuid);
        File logFile = new File(currentJobRunCompleteDataDirectory, uuid.toString());
        LOGGER.log(Level.FINEST, "logFile = {0}", logFile);
        try {
            //This is to create parent folders
            io.jenkins.plugins.propelo.commons.utils.FileUtils.createFileRecursively(logFile);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating log file!", e);
            return null;
        }
        //Write without any Open Options
        try {
            Files.write(logFile.toPath(), logData.getBytes(UTF_8));
            LOGGER.log(Level.FINEST, "Successfully wrote logFile {0}", logFile);
            return uuid;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving log file!", e);
            return null;
        }
    }
}
