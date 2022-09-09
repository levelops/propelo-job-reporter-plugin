package io.levelops.plugins.levelops_job_reporter.exrtensions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.levelops.plugins.levelops_job_reporter.extensions.LevelOpsPostBuildPublisher;

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
