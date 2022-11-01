package io.jenkins.plugins.propelo.job_reporter.exrtensions;

import java.util.ArrayList;
import java.util.List;

import io.jenkins.plugins.propelo.job_reporter.extensions.LevelOpsPostBuildPublisher;

import org.junit.Test;

public class LevelOpsPostBuildPublisherTest {

    @Test
    public void cleanProjectNameTest(){
        List<String> projectNames = new ArrayList<String>(){{
            new String(".Test");
            new String("..Test");
            new String("../.Test");
            new String("../Test");
            new String("../../../Test");
            new String("../../../..Test");
        }};
        for (String projectName:projectNames) {
            LevelOpsPostBuildPublisher.sanatizeFileName(projectName).equals("_Test");
        }
    }
}
