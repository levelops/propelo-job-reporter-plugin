package io.jenkins.plugins.propelo.commons.service;

import com.google.common.base.Strings;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.apache.commons.collections.CollectionUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class HttpClientFactory {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static OkHttpClient constructSafeHttpClient(ProxyConfigService.ProxyConfig proxyConfig, final HttpUrl url){
        OkHttpClient.Builder builder = constructHttpClientBuilder(proxyConfig, url);
        OkHttpClient client = builder.build();
        return client;
    }


    private static final boolean isUrlInNoProxyHosts(HttpUrl url, final List<Pattern> noProxyHostPatterns) {
        if (url == null) {
            return false;
        }
        String host = url.host();
        LOGGER.log(Level.FINEST, "host = {0}", new Object[]{host});
        if(Strings.isNullOrEmpty(host)) {
            LOGGER.log(Level.FINEST, "host is null or empty, isUrlInNoProxyHosts is false");
            return false;
        }
        if (CollectionUtils.isEmpty(noProxyHostPatterns)) {
            LOGGER.log(Level.FINEST, "noProxyHostPatterns is null or empty, isUrlInNoProxyHosts is false");
            return false;
        }
        for (Pattern p : noProxyHostPatterns) {
            if (p == null) {
                continue;
            }
            if (p.matcher(host).matches()) {
                LOGGER.log(Level.FINE, "No Proxy Host Pattern Matched url = {0}, host = {1}, pattern = {2}", new Object[]{url, host, p});
                return true;
            }
        }
        LOGGER.log(Level.FINEST, "host = {0} did not match any no proxy hosts, isUrlInNoProxyHosts is false", new Object[]{host});
        return false;
    }

    private static OkHttpClient.Builder constructHttpClientBuilder(ProxyConfigService.ProxyConfig proxyConfig, final HttpUrl url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES); // read timeout
        if(proxyConfig == null) {
            return builder;
        }
        if(isUrlInNoProxyHosts(url, proxyConfig.getNoProxyHostPatterns())) {
            LOGGER.log(Level.FINE, "url = {0} is in no proxy hosts, proxy is NO_PROXY", new Object[]{url});
            return builder;
        }
        if (Strings.isNullOrEmpty(proxyConfig.getHost())) {
            LOGGER.log(Level.FINE, "proxyHost is null or empty, proxy is NO_PROXY");
            return builder;
        }
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyConfig.getHost(), proxyConfig.getPort()));
        if((proxy == null) || (proxy.equals(Proxy.NO_PROXY))) {
            LOGGER.log(Level.FINE, "proxy is null or NO_PROXY");
            return builder;
        }
        builder.proxy(proxy);

        final String userName = proxyConfig.getUserName();
        final String password = proxyConfig.getPassword();

        if( (!Strings.isNullOrEmpty(userName)) && (!Strings.isNullOrEmpty(password))) {
            Authenticator proxyAuthenticator = new Authenticator() {
                @Override public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic(userName, password);
                    return response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                }
            };
            if(proxyAuthenticator != null) {
                builder.proxyAuthenticator(proxyAuthenticator);
            }
        } else {
            LOGGER.log(Level.FINE, "proxyUsername or proxyPassword is null or empty, proxy is NO_PROXY");
        }
        return builder;
    }

    private static OkHttpClient constructAllTrustingOkHttpClient(ProxyConfigService.ProxyConfig proxyConfig, final HttpUrl url) throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        OkHttpClient.Builder builder = constructHttpClientBuilder(proxyConfig, url);

        builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }

    public static OkHttpClient createHttpClient(boolean trustAllCertificates, final ProxyConfigService.ProxyConfig proxyConfig, final HttpUrl url) {
        if(trustAllCertificates){
            LOGGER.log(Level.FINEST, "creating all trusting http client trustAllCertificates = " + trustAllCertificates);
            try {
                return constructAllTrustingOkHttpClient(proxyConfig, url);
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                LOGGER.log(Level.WARNING, "Error creating all trust http client! Defaulting to safe http client", e);
                return constructSafeHttpClient(proxyConfig, url);
            }
        } else {
            LOGGER.log(Level.FINEST, "creating safe http client trustAllCertificates = " + trustAllCertificates);
            return constructSafeHttpClient(proxyConfig, url);
        }
    }
}
