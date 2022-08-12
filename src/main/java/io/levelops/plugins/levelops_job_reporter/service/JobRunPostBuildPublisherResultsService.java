package io.levelops.plugins.levelops_job_reporter.service;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copy files from runs to data directory once LevelopsRunListener starts to gather job results.
 * */
public class JobRunPostBuildPublisherResultsService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private static final String BULLSEYE_COVERAGE_REPORTS_ZIP = "levelops_code_coverage_xml.zip";

    public boolean gatherJobRunCodeCoverageResults(Run run, File completeDataDirectory){
        if (!(run instanceof AbstractBuild)) {
            LOGGER.log(Level.FINE, "Cannot gather job run code coverage results, run is NOT instanceof AbstractBuild");
            return false;
        }

        LOGGER.log(Level.FINEST, "run is instanceof AbstractBuild");
        AbstractBuild build = (AbstractBuild) run;
        FilePath workspace = build.getWorkspace();
        if (workspace == null) {
            LOGGER.log(Level.FINE, "Cannot gather job run code coverage results, no workspace in " + build.toString());
            return false;
        }
        try {
            LOGGER.log(Level.FINEST, "Started copying zip file from job to data directory");
            final File codeCoverageFromPublisher = getFileWithMatchingPattern(run.getRootDir());
            if (codeCoverageFromPublisher == null)
                return false;
            final File codeCoverageResults = new File(completeDataDirectory, codeCoverageFromPublisher.getName());
            FileUtils.copyFile(codeCoverageFromPublisher, codeCoverageResults);
            LOGGER.log(Level.FINEST, "Completed copying zip file from job to data directory");
            return codeCoverageResults.listFiles() != null && codeCoverageResults.listFiles().length > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while copying  zip file from job to data directory", e);
        }
        return false;
    }

    private File getFileWithMatchingPattern(File runRootDir) {
        if (runRootDir == null)
            return null;
        File[] files = runRootDir.listFiles();
        if (files != null) {
            for (File file: files) {
                String filename = file.getName();
                if (filename.startsWith("levelops_code_coverage_") && filename.endsWith("_xml.zip") &&
                        !filename.equals(BULLSEYE_COVERAGE_REPORTS_ZIP))
                    return file;
            }
        }
        return null;
    }
}
