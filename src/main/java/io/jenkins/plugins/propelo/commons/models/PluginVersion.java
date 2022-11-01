package io.jenkins.plugins.propelo.commons.models;

import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

public class PluginVersion implements Comparable<PluginVersion> {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final PluginVersion EMPTY_PLUGIN_VERSION = new PluginVersion("", 0,0,0);

    private final String version;
    private final Integer majorVersion;
    private final Integer minorVersion;
    private final Integer patchVersion;

    public PluginVersion(String version, int majorVersion, int minorVersion, int patchVersion) {
        this.version = version;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.patchVersion = patchVersion;
    }

    public String getVersion() {
        return version;
    }

    public Integer getMajorVersion() {
        return majorVersion;
    }

    public Integer getMinorVersion() {
        return minorVersion;
    }

    public Integer getPatchVersion() {
        return patchVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginVersion that = (PluginVersion) o;
        return majorVersion == that.majorVersion &&
                minorVersion == that.minorVersion &&
                patchVersion == that.patchVersion &&
                Objects.equal(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(version, majorVersion, minorVersion, patchVersion);
    }

    @Override
    public String toString() {
        return "PluginVersion{" +
                "version='" + version + '\'' +
                ", majorVersion=" + majorVersion +
                ", minorVersion=" + minorVersion +
                ", patchVersion=" + patchVersion +
                '}';
    }

    @Override
    public int compareTo(@NotNull PluginVersion o) {
        int result = this.getMajorVersion().compareTo(o.getMajorVersion());
        if(result != 0) {
            return result;
        }
        result = this.getMinorVersion().compareTo(o.getMinorVersion());
        if(result != 0) {
            return result;
        }
        result = this.getPatchVersion().compareTo(o.getPatchVersion());
        return result;
    }
}
