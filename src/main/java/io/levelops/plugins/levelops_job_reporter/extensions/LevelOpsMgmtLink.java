package io.levelops.plugins.levelops_job_reporter.extensions;

import hudson.Extension;
import hudson.model.Hudson;
import hudson.model.ManagementLink;
import hudson.util.Secret;
import io.levelops.plugins.levelops_job_reporter.plugins.LevelOpsPluginImpl;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.verb.POST;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

@Extension
public class LevelOpsMgmtLink extends ManagementLink {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String PLUGIN_NAME = "propelo-job-reporter";
    public static final String PLUGIN_DISPLAY_NAME = "Propelo Job Reporter";
    public static final String PLUGIN_DESCRIPTION = "Reports back to Propelo after each Job Run with metadata and unsuccessful job logs.";

    @Override
    public String getDisplayName() {
        return PLUGIN_DISPLAY_NAME;
    }

    @Override
    public String getIconFileName() {
        return "package.png";
    }

    @Override
    public String getUrlName() {
        return PLUGIN_NAME;
    }

    @Override
    public String getDescription() {
        return PLUGIN_DESCRIPTION;
    }

    @POST
    public void doSaveSettings(final StaplerRequest res, final StaplerResponse rsp,
                               @QueryParameter("levelOpsApiKey") final String levelOpsApiKey,
                               @QueryParameter("levelOpsPluginPath") final String levelOpsPluginPath,
                               @QueryParameter("jenkinsBaseUrl") final String jenkinsBaseUrl,
                               @QueryParameter("jenkinsUserName") final String jenkinsUserName,
                               @QueryParameter("jenkinsUserToken") final String jenkinsUserToken,
                               @QueryParameter("bullseyeXmlResultPaths") final String bullseyeXmlResultPaths,
                               @QueryParameter("productIds") final String productIds,
                               @QueryParameter("jenkinsInstanceName") final String jenkinsInstanceName,
                               @QueryParameter("trustAllCertificates") final boolean trustAllCertificates
    ) throws IOException {
        LOGGER.log(Level.FINE, "Starting doSaveSettings, levelOpsApiKey = {0}, levelOpsPluginPath = {1}, " +
                        "jenkinsBaseUrl = {2}, jenkinsUserName = {3}, jenkinsUserToken = {4}, productIds = {5}, jenkinsInstanceName = {6}, trustAllCertificates = {7}, bullseyeXmlResultPaths = {8}",
                new Object[] {levelOpsApiKey, levelOpsPluginPath, jenkinsBaseUrl, jenkinsUserName, jenkinsUserToken, productIds, jenkinsInstanceName, trustAllCertificates,bullseyeXmlResultPaths});

        Hudson.getInstance().checkPermission(Hudson.ADMINISTER);

        final LevelOpsPluginImpl plugin = LevelOpsPluginImpl.getInstance();
        plugin.setLevelOpsApiKey(Secret.fromString(levelOpsApiKey));
        plugin.setLevelOpsPluginPath(levelOpsPluginPath);
        plugin.setJenkinsBaseUrl(jenkinsBaseUrl);
        plugin.setJenkinsUserName(jenkinsUserName);
        plugin.setJenkinsUserToken(Secret.fromString(jenkinsUserToken));
        plugin.setBullseyeXmlResultPath(bullseyeXmlResultPaths);
        plugin.setProductIds(productIds);
        plugin.setJenkinsInstanceName(jenkinsInstanceName);
        plugin.setTrustAllCertificates(trustAllCertificates);
        plugin.save();
        LOGGER.log(Level.CONFIG, "Saving plugin settings done. plugin = {0}", plugin);
        rsp.sendRedirect(res.getContextPath() + "/" + PLUGIN_NAME);
    }

    public LevelOpsPluginImpl getConfiguration() {
        return LevelOpsPluginImpl.getInstance();
    }

    public String getJenkinsStatus() {
        return getConfiguration().getJenkinsStatus();
    }
}