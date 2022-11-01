package io.jenkins.plugins.propelo.commons.service;

import io.jenkins.plugins.propelo.commons.service.JenkinsInstanceNameService;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JenkinsInstanceNameServiceTest {
    @Test
    public void testCreateOrReturnInstanceName() throws IOException {
        File tempDir = null;
        try {
            tempDir = Files.createTempDirectory("testCreateOrReturnInstanceName").toFile();
            JenkinsInstanceNameService service = new JenkinsInstanceNameService(tempDir);
            for(int i=0; i < 10; i++){
                String e = "Jenkisns US" + i;
                String a = service.createOrUpdateInstanceName(e);
                Assert.assertEquals(e, a);
            }
        } finally {
            if (tempDir != null){
                FileUtils.deleteDirectory(tempDir);
            }
        }
    }

    @Test
    public void testCreateOrReturnInstanceNameNullOrEmpty() throws IOException {
        File tempDir = null;
        try {
            tempDir = Files.createTempDirectory("testCreateOrReturnInstanceNameNullOrEmpty").toFile();
            JenkinsInstanceNameService service = new JenkinsInstanceNameService(tempDir);
            Assert.assertEquals(null, service.createOrUpdateInstanceName(null));
            Assert.assertEquals("", service.createOrUpdateInstanceName(""));
        } finally {
            if (tempDir != null){
                FileUtils.deleteDirectory(tempDir);
            }
        }
    }

}