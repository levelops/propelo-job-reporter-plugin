package io.jenkins.plugins.propelo.commons.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jenkins.plugins.propelo.commons.models.JobRunDetail;
import io.jenkins.plugins.propelo.commons.models.JobRunParam;
import io.jenkins.plugins.propelo.commons.models.JobRunParamsMessage;
import io.jenkins.plugins.propelo.commons.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static io.jenkins.plugins.propelo.commons.plugins.Common.JOBS_DATA_DIR_NAME;
import static io.jenkins.plugins.propelo.commons.plugins.Common.RUN_PARAMS_HISTORY_FILE;
import static io.jenkins.plugins.propelo.commons.plugins.Common.UTF_8;

public class JobRunParamsService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final Pattern PARAM_TYPE_REGEX = Pattern.compile("\\((.*)\\)(.*)", Pattern.DOTALL);
    private final File dataDirectoryWithRotation;
    private final ObjectMapper mapper;

    public JobRunParamsService(File dataDirectoryWithRotation, ObjectMapper mapper) {
        this.dataDirectoryWithRotation = dataDirectoryWithRotation;
        this.mapper = mapper;
    }

    private File buildAndCreateRunParamsFilePath(String jobFullName) throws IOException {
        File buildRunMessageFile = Paths.get(dataDirectoryWithRotation.getAbsolutePath(), JOBS_DATA_DIR_NAME, jobFullName, RUN_PARAMS_HISTORY_FILE).toFile();
        return FileUtils.createFileRecursively(buildRunMessageFile);
    }

    private void writeRunParamsMessage(File buildRunMessageFile, JobRunDetail jobRunDetail) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        fileContent.append(jobRunDetail.getBuildNumber()).append(",")
                .append(URLEncoder.encode(mapper.writeValueAsString(jobRunDetail.getJobRunParams()), "UTF-8")).append("\n");
        String buildMessageString = fileContent.toString();
        LOGGER.finest("JobRunParamsService:writeRunParamsMessage buildMessageString = " + buildMessageString);
        com.google.common.io.Files.append(buildMessageString, buildRunMessageFile, UTF_8);
        return;
    }

    public void saveRunParamMessage(JobRunDetail jobRunDetail) throws IOException {
        LOGGER.finest("jobName = " + jobRunDetail.getJobName());
        File buildRunMessageFile = buildAndCreateRunParamsFilePath(jobRunDetail.getJobFullName());
        LOGGER.finest("buildRunMessageFile = " + buildRunMessageFile.getAbsolutePath());
        writeRunParamsMessage(buildRunMessageFile, jobRunDetail);
        return;
    }

    public List<JobRunParamsMessage> readBuildMessages(String jobName) throws IOException {
        File jobRunParamsFile = Paths.get(dataDirectoryWithRotation.getAbsolutePath(), JOBS_DATA_DIR_NAME, jobName, RUN_PARAMS_HISTORY_FILE).toFile();
        if(!jobRunParamsFile.exists()){
            LOGGER.finest("JobRunParamsService jobRunParamsFile does not exist!! jobRunParamsFile = " + jobRunParamsFile.getAbsolutePath());
            return Collections.emptyList();
        }

        List<JobRunParamsMessage> jobRunsParams = new ArrayList<>();
        List<String> messagesStrings = Files.readAllLines(jobRunParamsFile.toPath(), UTF_8);
        LOGGER.finest("JobRunParamsService messagesStrings.size() = " + messagesStrings.size());
        for(String messageString : messagesStrings){
            String[] build = messageString.split(",");
            if((build == null) || (build.length != 2)){
                LOGGER.finest("Current jobParamMessageLine is not valid!! messageString = " + messageString);
                continue;
            }
            Long buildNumber = Long.parseLong(build[0]);
            String decodedJson = URLDecoder.decode(build[1], "UTF-8");
            List<JobRunParam> currentJobParams = mapper.readValue(decodedJson, mapper.getTypeFactory().constructCollectionType(List.class, JobRunParam.class));
            jobRunsParams.add(new JobRunParamsMessage(buildNumber, currentJobParams));
        }
        Collections.sort(jobRunsParams);
        return jobRunsParams;
    }
}
