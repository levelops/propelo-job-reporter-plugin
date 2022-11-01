package io.jenkins.plugins.propelo.commons.service;


import io.jenkins.plugins.propelo.commons.service.JobRunGitChangesService;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class JobRunGitChangesServiceTest {
    @Test
    public void test() throws URISyntaxException, IOException {
        JobRunGitChangesService jobRunGitChangesService = new JobRunGitChangesService(null, null);
        File testDir = new File(this.getClass().getClassLoader().getResource("git_changes_dir/1").toURI());
        Assert.assertEquals("changelog.xml", jobRunGitChangesService.getChangeLogFile(testDir).getName());

        testDir = new File(this.getClass().getClassLoader().getResource("git_changes_dir/2").toURI());
        Assert.assertEquals("changelog1044854799909823881.xml", jobRunGitChangesService.getChangeLogFile(testDir).getName());

        testDir = new File(this.getClass().getClassLoader().getResource("git_changes_dir/3").toURI());
        Assert.assertEquals("changelog.xml", jobRunGitChangesService.getChangeLogFile(testDir).getName());

        testDir = new File(new File(this.getClass().getClassLoader().getResource("git_changes_dir").toURI()),"doesNotExist");
        Assert.assertNull(jobRunGitChangesService.getChangeLogFile(testDir));

        testDir = null;
        Assert.assertNull(jobRunGitChangesService.getChangeLogFile(testDir));

        testDir = Files.createTempDirectory("empty").toFile();
        Assert.assertNull(jobRunGitChangesService.getChangeLogFile(testDir));
        testDir.delete();
    }

}