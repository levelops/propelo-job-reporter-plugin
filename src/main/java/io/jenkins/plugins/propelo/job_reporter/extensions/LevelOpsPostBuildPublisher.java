package io.jenkins.plugins.propelo.job_reporter.extensions;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import io.jenkins.plugins.propelo.job_reporter.service.ArchiveJobRunResultFiles;
import jenkins.tasks.SimpleBuildStep;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is run when its post build action is enabled. It is responsible to pick files from workspace which match
 * a particular pattern and put them in runs so that LevelopsRunListener can pick them up later.
 * The pattern of the files and the project name are passed from the class's corresponding config.jelly file
 * (takes from UI) which is received in the constructor when annotated with {@link DataBoundConstructor}.
 */
public class LevelOpsPostBuildPublisher extends Recorder implements SimpleBuildStep {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private static final String LEVELOPS_CODE_COVERAGE_DIR_PREFIX = "levelops_code_coverage_";
    private static final String LEVELOPS_CODE_COVERAGE_DIR_SUFFIX = "_xml";
    private static final String COVERAGE_REPORTS_PATH = "**/Bullseye*.xml,**/Cov*.xml";

    private final String levelOpsCodeCoverageZip;
    private final String coverageReportPath;
    private final String projectName;

    @DataBoundConstructor
    public LevelOpsPostBuildPublisher(String coverageReportPath, String projectName) {
        this.projectName = projectName;
        this.coverageReportPath = coverageReportPath != null ? coverageReportPath : COVERAGE_REPORTS_PATH;
        String levelOpsCodeCoverageDir = StringUtils.isNotEmpty(projectName) ? getCodeCoverageDir(projectName) :
                "levelops_code_coverage_post_build_xml";
        levelOpsCodeCoverageZip = levelOpsCodeCoverageDir + ".zip";
    }

    private String getCodeCoverageDir(final String projectName) {
        return LEVELOPS_CODE_COVERAGE_DIR_PREFIX + sanatizeFileName(projectName) + LEVELOPS_CODE_COVERAGE_DIR_SUFFIX;
    }

    public static String sanatizeFileName(final String projectName) {
        return projectName.replaceAll("[^A-Za-z0-9]", "_").replaceAll("__+", "_");
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath workspace,
                        @Nonnull Launcher launcher, @Nonnull TaskListener listener)
            throws InterruptedException, IOException {
        LOGGER.log(Level.INFO, "Propelo post build publisher called for code coverage plugin");
        final List<Byte> serializedZipFile = workspace.act(new ArchiveJobRunResultFiles(coverageReportPath));
        final File codeCoverageResults = new File(run.getRootDir(), levelOpsCodeCoverageZip);
        LOGGER.log(Level.INFO, "codeCoverageResults = {0}", codeCoverageResults);
        FileUtils.writeByteArrayToFile(codeCoverageResults, ArrayUtils.toPrimitive(serializedZipFile.toArray(new Byte[0])));
        LOGGER.log(Level.INFO, "Stored code coverage result zip in directory: {}", run.getRootDir());
    }

    public String getCoverageReportPath() {
        return coverageReportPath;
    }

    public String getProjectName() {
        return projectName;
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Propelo Job Reporter Plugin";
        }
    }
}
