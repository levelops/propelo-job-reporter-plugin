package io.levelops.plugins.commons.service;

import io.levelops.plugins.commons.models.PluginVersion;
import org.junit.Assert;
import org.junit.Test;

public class PluginVersionServiceTest {
    @Test
    public void testParsePluginVersionString() {
        PluginVersionService pluginVersionService = new PluginVersionService();
        Assert.assertEquals(PluginVersion.EMPTY_PLUGIN_VERSION, pluginVersionService.parsePluginVersionString(null));
        Assert.assertEquals(PluginVersion.EMPTY_PLUGIN_VERSION, pluginVersionService.parsePluginVersionString(""));
        Assert.assertEquals(PluginVersion.EMPTY_PLUGIN_VERSION, pluginVersionService.parsePluginVersionString("a.b.c-SNAPSHOT"));
        Assert.assertEquals(PluginVersion.EMPTY_PLUGIN_VERSION, pluginVersionService.parsePluginVersionString("a.b.c"));
        Assert.assertEquals(new PluginVersion("1.2.5-SNAPSHOT", 1, 2, 5), pluginVersionService.parsePluginVersionString("1.2.5-SNAPSHOT"));
        Assert.assertEquals(new PluginVersion("1.2.5", 1, 2, 5), pluginVersionService.parsePluginVersionString("1.2.5"));
    }

    @Test
    public void testCompareTo() {
        Assert.assertEquals(0, PluginVersion.EMPTY_PLUGIN_VERSION.compareTo(PluginVersion.EMPTY_PLUGIN_VERSION));

        Assert.assertEquals(0, (new PluginVersion("", 1,2,3)).compareTo(new PluginVersion("", 1,2,3)));

        Assert.assertEquals(-1, (new PluginVersion("", 1,1,1)).compareTo(new PluginVersion("", 1,1,2)));
        Assert.assertEquals(1, (new PluginVersion("", 1,1,2)).compareTo(new PluginVersion("", 1,1,1)));

        Assert.assertEquals(-1, (new PluginVersion("", 1,2,3)).compareTo(new PluginVersion("", 1,3,2)));
        Assert.assertEquals(-1, (new PluginVersion("", 1,2,3)).compareTo(new PluginVersion("", 1,3,3)));
        Assert.assertEquals(-1, (new PluginVersion("", 1,2,3)).compareTo(new PluginVersion("", 1,3,4)));
        Assert.assertEquals(1, (new PluginVersion("", 1,3,3)).compareTo(new PluginVersion("", 1,2,2)));
        Assert.assertEquals(1, (new PluginVersion("", 1,3,3)).compareTo(new PluginVersion("", 1,2,3)));
        Assert.assertEquals(1, (new PluginVersion("", 1,3,3)).compareTo(new PluginVersion("", 1,2,4)));

        Assert.assertEquals(-1, (new PluginVersion("", 2,3,4)).compareTo(new PluginVersion("", 3,2,4)));
        Assert.assertEquals(-1, (new PluginVersion("", 2,3,4)).compareTo(new PluginVersion("", 3,3,4)));
        Assert.assertEquals(-1, (new PluginVersion("", 2,3,4)).compareTo(new PluginVersion("", 3,4,4)));

        Assert.assertEquals(1, (new PluginVersion("", 2,3,4)).compareTo(new PluginVersion("", 1,2,4)));
        Assert.assertEquals(1, (new PluginVersion("", 2,3,4)).compareTo(new PluginVersion("", 1,3,4)));
        Assert.assertEquals(1, (new PluginVersion("", 2,3,4)).compareTo(new PluginVersion("", 1,4,4)));
    }
}