package io.levelops.plugins.commons.service;

import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class JenkinsConfigSCMServiceTest {
    @Test
    public void testSCMUserRemoteConfig() throws URISyntaxException, IOException, SAXException, ParserConfigurationException {
        File configFile = new File(this.getClass().getClassLoader().getResource("configs_scm/SCMUserRemoteConfig.xml").toURI());
        JenkinsConfigSCMService jenkinsConfigSCMService = new JenkinsConfigSCMService();
        Optional<JenkinsConfigSCMService.SCMResult> result = jenkinsConfigSCMService.parseSCMData(configFile);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().getUrl(), "https://virajajgaonkar@bitbucket.org/virajajgaonkar/leetcode.git");
        Assert.assertEquals(result.get().getUserName(), null);
    }

    @Test
    public void testBitbucketSCMNavigator() throws URISyntaxException, IOException, SAXException, ParserConfigurationException {
        File configFile = new File(this.getClass().getClassLoader().getResource("configs_scm/BitbucketSCMNavigator.xml").toURI());
        JenkinsConfigSCMService jenkinsConfigSCMService = new JenkinsConfigSCMService();
        Optional<JenkinsConfigSCMService.SCMResult> result = jenkinsConfigSCMService.parseSCMData(configFile);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().getUrl(), "https://bitbucket.org");
        Assert.assertEquals(result.get().getUserName(), "virajajgaonkar");
    }

    @Test
    public void testGithubProjectProperty() throws URISyntaxException, IOException, SAXException, ParserConfigurationException {
        File configFile = new File(this.getClass().getClassLoader().getResource("configs_scm/GithubProjectProperty.xml").toURI());
        JenkinsConfigSCMService jenkinsConfigSCMService = new JenkinsConfigSCMService();
        Optional<JenkinsConfigSCMService.SCMResult> result = jenkinsConfigSCMService.parseSCMData(configFile);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().getUrl(), "https://github.com/AnamikaN/WarpService.git");
        Assert.assertEquals(result.get().getUserName(), null);
    }

    @Test
    public void testGitHubPushTrigger() throws URISyntaxException, IOException, SAXException, ParserConfigurationException {
        File configFile = new File(this.getClass().getClassLoader().getResource("configs_scm/GitHubPushTrigger.xml").toURI());
        JenkinsConfigSCMService jenkinsConfigSCMService = new JenkinsConfigSCMService();
        Optional<JenkinsConfigSCMService.SCMResult> result = jenkinsConfigSCMService.parseSCMData(configFile);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().getUrl(), "https://github.com/TechPrimers/jenkins-example.git");
        Assert.assertEquals(result.get().getUserName(), null);
    }

    @Test
    public void testBitbucketSCMNavigator3() throws URISyntaxException, IOException, SAXException, ParserConfigurationException {
        File configFile = new File(this.getClass().getClassLoader().getResource("configs_scm/Stream-Maven-2.xml").toURI());
        JenkinsConfigSCMService jenkinsConfigSCMService = new JenkinsConfigSCMService();
        Optional<JenkinsConfigSCMService.SCMResult> result = jenkinsConfigSCMService.parseSCMData(configFile);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().getUrl(), "https://github.com/virajajgaonkar/LeetCode.git");
        Assert.assertEquals(result.get().getUserName(), null);
    }

    @Test
    public void testPerforceScmUrl() throws URISyntaxException, IOException, SAXException, ParserConfigurationException {
        File configFile = new File(this.getClass().getClassLoader().getResource("configs_scm/PerforceConfig.xml").toURI());
        JenkinsConfigSCMService jenkinsConfigSCMService = new JenkinsConfigSCMService();
        Optional<JenkinsConfigSCMService.SCMResult> result = jenkinsConfigSCMService.parseSCMData(configFile);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().getUrl(), "sandbox");
        Assert.assertEquals(result.get().getUserName(), null);
    }
}