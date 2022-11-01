package io.jenkins.plugins.propelo.commons.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;

import static io.jenkins.plugins.propelo.commons.service.JobRunParamsService.PARAM_TYPE_REGEX;

public class JobRunParamsServiceTest {
    @Test
    public void testRegex(){
        String data = "(StringParameterValue) env_name='STAGING'";
        Matcher matcher = PARAM_TYPE_REGEX.matcher(data);
        Assert.assertTrue(matcher.matches());
        Assert.assertEquals("StringParameterValue", matcher.group(1));
    }

    @Test
    public void testRegex2(){
        String data = "(TextParameterValue) boker_ids='broker1\n" +
                "broker2\n" +
                "broker3'";
        Matcher matcher = PARAM_TYPE_REGEX.matcher(data);
        Assert.assertTrue(matcher.matches());
        Assert.assertEquals("TextParameterValue", matcher.group(1));
    }


}