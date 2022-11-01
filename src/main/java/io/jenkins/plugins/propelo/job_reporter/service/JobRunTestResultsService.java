package io.jenkins.plugins.propelo.job_reporter.service;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobRunTestResultsService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String LEVELOPS_JUNIT_REPORTS_ZIP = "levelops_junit_reports.zip";
    private static final String JUNIT_TEST_REPORTS_PATH = "**/TEST-*.xml";

    public void gatherJobRunTestResults(Run run, File completeDataDirectory){
        if (!(run instanceof AbstractBuild)) {
            LOGGER.log(Level.FINE, "Cannot gather job run test results, run is NOT instanceof AbstractBuild");
            return;
        }

        LOGGER.log(Level.FINEST, "run is instanceof AbstractBuild");
        AbstractBuild build = (AbstractBuild) run;
        FilePath workspace = build.getWorkspace();
        if (workspace == null) {
            LOGGER.log(Level.FINE, "Cannot gather job run test results, no workspace in " + build.toString());
            return;
        }
        try {
            LOGGER.log(Level.FINEST, "Propelo's junit post build publisher called");
            final List<Byte> bytes = workspace.act(new ArchiveJobRunResultFiles(JUNIT_TEST_REPORTS_PATH));
            final File junitReports = new File(completeDataDirectory, LEVELOPS_JUNIT_REPORTS_ZIP);
            LOGGER.log(Level.FINEST, "junitReports = {0}", junitReports);
            FileUtils.writeByteArrayToFile(junitReports, ArrayUtils.toPrimitive(bytes.toArray(new Byte[0])));
            LOGGER.log(Level.FINEST, "Stored test result reports zip");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in LevelOpsPostBuildPublisher perform!", e);
        }

    }
}
