package io.jenkins.plugins.propelo.commons.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jenkins.plugins.propelo.commons.models.jenkins.saas.GenericRequest;
import io.jenkins.plugins.propelo.commons.models.jenkins.saas.GenericResponse;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

public class GenericRequestService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String GENERIC_REQUESTS_MULTIPART_API = "/v1/generic-requests/multipart";
    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.parse("application/json; charset=utf-8");

    private final String apiUrl;
    private final ObjectMapper mapper;

    public GenericRequestService(String apiUrl, ObjectMapper mapper) {
        this.apiUrl = apiUrl;
        this.mapper = mapper;
    }

    public GenericResponse performGenericRequest(final String apiKey, final String genericRequestType, String payload, final boolean trustAllCertificates, File zipFile, final ProxyConfigService.ProxyConfig proxyConfig) throws IOException {
        GenericRequest genericRequest = new GenericRequest(genericRequestType, payload);
        String genericRequestJson = mapper.writeValueAsString(genericRequest);

        MultipartBody.Builder requestBodyBldr = new MultipartBody.Builder()
                .addFormDataPart("json", "json", okhttp3.RequestBody.create(genericRequestJson.getBytes(UTF_8)));
        if((zipFile != null) && (zipFile.exists())) {
            LOGGER.log(Level.FINEST, "multipart zip part exists");
            requestBodyBldr.addFormDataPart("file", zipFile.getName(), okhttp3.RequestBody.create(Files.readAllBytes(zipFile.toPath())));
        } else {
            LOGGER.log(Level.FINEST, "multipart zip part does not exist");
        }
        MultipartBody requestBody = requestBodyBldr.build();

        final String genericRequestsUrl = apiUrl + GENERIC_REQUESTS_MULTIPART_API;
        LOGGER.log(Level.FINER, "genericRequestsUrl = {0}", genericRequestsUrl);
        HttpUrl url = HttpUrl.get(genericRequestsUrl);
        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Authorization", "Apikey " + apiKey)
                .url(url)
                .post(requestBody)
                .build();

        String responseString = null;
        OkHttpClient client = HttpClientFactory.createHttpClient(trustAllCertificates, proxyConfig, url);
        Call call = client.newCall(request);
        Response httpResponse = null;
        ResponseBody body = null;
        try {
            httpResponse = call.execute();
            body = httpResponse.body();
            if (!httpResponse.isSuccessful() || body == null) {
                String errorBody = null;
                if (body != null) {
                    try {
                        errorBody = body.string();
                    } catch (Exception e) {
                        errorBody = e.toString();
                    }
                }
                throw new IOException("Response not successful: " + httpResponse.code() + " Error Body: " + errorBody);
            }
            responseString = body.string();
        } finally {
            if (body != null) {
                body.close();
            }
            if (httpResponse != null){
                httpResponse.close();
            }
        }
        LOGGER.finest("responseString = " + responseString);
        GenericResponse response = mapper.readValue(responseString, GenericResponse.class);
        if( (response.getResponseType() == null) || (response.getPayload() == null) ) {
            throw new IOException("Invalid response");
        }
        return response;
    }
}
