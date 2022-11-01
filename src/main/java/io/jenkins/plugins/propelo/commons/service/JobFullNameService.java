package io.jenkins.plugins.propelo.commons.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jenkins.plugins.propelo.commons.models.JobNameDetails;
import io.jenkins.plugins.propelo.commons.utils.FileUtils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.jenkins.plugins.propelo.commons.plugins.Common.JOBS_DATA_DIR_NAME;
import static io.jenkins.plugins.propelo.commons.plugins.Common.JOB_FULL_NAME_FILE;
import static io.jenkins.plugins.propelo.commons.plugins.Common.UTF_8;

public class JobFullNameService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final File dataDirectoryWithRotation;
    private final ObjectMapper mapper;

    public JobFullNameService(File dataDirectoryWithRotation, ObjectMapper mapper) {
        this.dataDirectoryWithRotation = dataDirectoryWithRotation;
        this.mapper = mapper;
    }

    private File buildAndCreateJobFullNameFilePath(String jobFullName) throws IOException {
        File buildRunMessageFile = Paths.get(dataDirectoryWithRotation.getAbsolutePath(), JOBS_DATA_DIR_NAME, jobFullName, JOB_FULL_NAME_FILE).toFile();
        return FileUtils.createFileRecursively(buildRunMessageFile);
    }

    private void writeJobFullNameFile(File jobFullNameFile, JobNameDetails jobNameDetails)  {
        String fileContent = null;
        try {
            fileContent = mapper.writeValueAsString(jobNameDetails);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "Error serializing job full name, will not write job full name file!", e);
            return;
        }
        LOGGER.finest("JobFullNameService::writeJobFullNameFile fileContent = " + fileContent);

        if(StringUtils.isBlank(fileContent)){
            LOGGER.finest("JobFullNameService::writeJobFullNameFile fileContent is Blank will not write job full name file!");
            return;
        }

        try {
            Files.write(jobFullNameFile.toPath(), fileContent.getBytes(UTF_8), StandardOpenOption.CREATE);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error writing job full name file!", e);
            return;
        }
        return;
    }

    public void saveJobFullName(JobNameDetails jobNameDetails) throws IOException {
        LOGGER.log(Level.FINEST,"jobNameDetails = {0}", jobNameDetails);
        File jobFullNameFile = buildAndCreateJobFullNameFilePath(jobNameDetails.getJobFullName());
        LOGGER.finest("jobFullNameFile = " + jobFullNameFile.getAbsolutePath());
        writeJobFullNameFile(jobFullNameFile, jobNameDetails);
        return;
    }
}
