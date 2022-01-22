package com.inicu.postgres.utility;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import javax.persistence.Basic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpClient {
    /** The time it takes for our client to timeout */
    public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds

    /** Single instance of our HttpClient */
    private static HttpClient mHttpClient;

    /**
     * Get our single instance of our HttpClient object.
     *
     * @return an HttpClient object with connection parameters set
     */
    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    /**
     * Performs an HTTP Post request to the specified url with the
     * specified parameters.
     *
     * @param url The web address to post the request to
     * @param postParameters The parameters to send via the request
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Performs an HTTP GET request to the specified url.
     *
     * @param url The web address to post the request to
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String executeWowzaHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet(url);

            Map<String, String> headers = new HashMap<>();
            headers.put("Accept","application/json");
            headers.put("Content-Type","application/json; charset=utf-8");

            for (String headerType : headers.keySet()) {
                request.setHeader(headerType, headers.get(headerType));
            }

            HttpHost targetHost = new HttpHost(BasicConstants.WOWZA_IP , BasicConstants.WOWZA_PORT, BasicConstants.WOWZA_SCHEME);

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(BasicConstants.WOWZA_USERNAME, BasicConstants.WOWZA_PASSWORD));

            AuthCache authCache = new BasicAuthCache();

            DigestScheme digestScheme = new DigestScheme();
            digestScheme.overrideParamter("realm", "Wowza");
            digestScheme.overrideParamter("nonce", "MTU5NTI2MDc4NjQwODozMmNhNWY4OTA0ODI5MzI2NjlmZmQxMzUyYjgzYjljZA==");
            authCache.put(targetHost, digestScheme);

            // Add AuthCache to the execution context
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);

            HttpResponse response = client.execute(targetHost,request,context);
            System.out.println(response.getStatusLine().getStatusCode());

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String executeHttpPostWithJsonObject(String url,JSONObject object){

        String resultReponse = "";
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        BufferedReader in = null;
        try {
            StringEntity params =new StringEntity(object.toString());

            HttpPost request = new HttpPost(url);
            request.setEntity(params);
            request.addHeader("content-type", "application/json; charset=utf-8");
            request.addHeader("Accept","application/json");

            HttpHost targetHost = new HttpHost(BasicConstants.WOWZA_IP , BasicConstants.WOWZA_PORT, BasicConstants.WOWZA_SCHEME);

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(BasicConstants.WOWZA_USERNAME, BasicConstants.WOWZA_PASSWORD));

            AuthCache authCache = new BasicAuthCache();

            DigestScheme digestScheme = new DigestScheme();
            digestScheme.overrideParamter("realm", "Wowza");
            digestScheme.overrideParamter("nonce", "MTU5NTI2MDc4NjQwODozMmNhNWY4OTA0ODI5MzI2NjlmZmQxMzUyYjgzYjljZA==");
            authCache.put(targetHost, digestScheme);

            // Add AuthCache to the execution context
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);

            HttpResponse response = httpClient.execute(targetHost,request,context);
            System.out.println(response.getStatusLine().getStatusCode());

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  resultReponse;
    }

    public static String executeHttpPutRequest(String url){

        String resultReponse = "";
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        BufferedReader in = null;
        try {
            HttpPut httpPut = new HttpPut(url);

            Map<String, String> headers = new HashMap<>();
            headers.put("Accept","application/json");
            headers.put("Content-Type","application/json");

            for (String headerType : headers.keySet()) {
                httpPut.setHeader(headerType, headers.get(headerType));
            }

            HttpHost targetHost = new HttpHost(BasicConstants.WOWZA_IP , BasicConstants.WOWZA_PORT, BasicConstants.WOWZA_SCHEME);

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(BasicConstants.WOWZA_USERNAME, BasicConstants.WOWZA_PASSWORD));

            AuthCache authCache = new BasicAuthCache();

            DigestScheme digestScheme = new DigestScheme();
            digestScheme.overrideParamter("realm", "Wowza");
            digestScheme.overrideParamter("nonce", "MTU5NTI2MDc4NjQwODozMmNhNWY4OTA0ODI5MzI2NjlmZmQxMzUyYjgzYjljZA==");
            authCache.put(targetHost, digestScheme);

            // Add AuthCache to the execution context
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);

            HttpResponse response = httpClient.execute(targetHost,httpPut,context);
//            HttpResponse httpResponse = httpClient.execute(httpPut);

            System.out.println(response.getStatusLine().getStatusCode());

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  resultReponse;
    }

}