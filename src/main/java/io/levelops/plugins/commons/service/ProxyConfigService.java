package io.levelops.plugins.commons.service;

import hudson.ProxyConfiguration;
import jenkins.model.Jenkins;

import java.lang.invoke.MethodHandles;
import java.net.Authenticator;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ProxyConfigService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static ProxyConfig generateConfigFromJenkinsProxyConfiguration(Jenkins jenkins) {
        if(jenkins == null) {
            LOGGER.log(Level.FINEST, "Jenkins instance is null, proxy is NO_PROXY");
            return ProxyConfig.NO_PROXY;
        }
        final ProxyConfiguration jenkinsProxyConfig = jenkins.proxy;
        if(jenkinsProxyConfig == null) {
            LOGGER.log(Level.FINEST, "proxyConfig is null, proxy is NO_PROXY");
            return ProxyConfig.NO_PROXY;
        }
        String proxyHost = jenkinsProxyConfig.name;
        Integer proxyPort = jenkinsProxyConfig.port;
        final String proxyUsername = jenkinsProxyConfig.getUserName();
        final String proxyPassword = jenkinsProxyConfig.getPassword();
        final List<Pattern> noProxyHostPatterns = jenkinsProxyConfig.getNoProxyHostPatterns();
        LOGGER.log(Level.FINE, "Proxy Host = {0}, Proxy Port = {1}, Proxy Username = {2}, Proxy Password = {3}, No Proxy Host Patterns = {4}", new Object[]{proxyHost, proxyPort, proxyUsername, proxyPassword, noProxyHostPatterns});

        ProxyConfig proxyConfig = new ProxyConfig(proxyHost, proxyPort, proxyUsername, proxyPassword, noProxyHostPatterns);
        return proxyConfig;
        /*
        LOGGER.log(Level.SEVERE, "pc u = {0}, p = {1}, tu = {2}, ep = {3}, no host = {4}, name = {5}, port = {6}", new Object[]{
        proxyConfig.getUserName(), proxyConfig.getPassword(), proxyConfig.getTestUrl(), proxyConfig.getEncryptedPassword(), proxyConfig.getNoProxyHostPatterns(), proxyConfig.name, proxyConfig.port});
        pc u = proxy_user, p = proxy_pass, tu = , ep = {AQAAABAAAAAQDKnnDFeSpD4hnnHBhfXm4B6UmHwun8Bt48b10vbjVLo=}, no host = [https://github\.com/, https://bitbucket\.org/], name = proxy.viraj.io, port = 65,432
         */
    }

    public static class ProxyConfig {
        private final String host;
        private final Integer port;
        private final String userName;
        private final String password;
        private final List<Pattern> noProxyHostPatterns;

        public final static ProxyConfig NO_PROXY = new ProxyConfig(null, null, null, null, new ArrayList<Pattern>());

        public ProxyConfig(String host, Integer port, String userName, String password, List<Pattern> noProxyHostPatterns) {
            this.host = host;
            this.port = port;
            this.userName = userName;
            this.password = password;
            this.noProxyHostPatterns = noProxyHostPatterns;
        }

        public String getHost() {
            return host;
        }

        public Integer getPort() {
            return port;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public List<Pattern> getNoProxyHostPatterns() {
            return noProxyHostPatterns;
        }
    }
}
