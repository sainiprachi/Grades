package com.grades.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class ServerRequest {
    private static final String TAG = ServerRequest.class.getSimpleName();
    private static String responceMessage = "";

    public static String makePostRequestForLogin(String loginUrl, List<NameValuePair> params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(loginUrl).openConnection());
            conn.setInstanceFollowRedirects(false);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);




            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            String location = conn.getHeaderField("Location");
            System.out.println(location);
            if (responseCode != 0) {
                if (responseCode == 302) {
                    responceMessage = "Success";
                } else if (responseCode == 200) {
                    responceMessage = "Invalid Email or password";
                } else if (responseCode == 400) {
                    responceMessage = "Failed";
                } else if (responseCode == 500) {
                    responceMessage = "";
                }
            } else {
                responceMessage = "Failed";
            }
            return responceMessage;
        } catch (MalformedURLException ex) {
            responceMessage = "MalformedURLException";
        } catch (IOException ex) {
            responceMessage = "IOException";
        }
        return responceMessage;
    }

    private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        return result.toString();
    }
    public static String getResponseMethod(String urlString) {
        HttpGet get = new HttpGet(urlString);
        get.setHeader("Content-Type", "application/json; charset=utf-8");

        HttpResponse resp = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter("http.protocol.version",
                    HttpVersion.HTTP_1_0);

            resp = httpClient.execute(get);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resp != null) {
            if (resp != null) {
                int statusCode = resp.getStatusLine().getStatusCode();
                Log.i("getResponse", " == " + statusCode);
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    return "SERVER_ERROR";
                } else if (statusCode == 200) {
                    return "SUCCESS";
                } else {
                    return "FAILED";
                }
            } else {
                return null;
            }
        } else {
            // there was a problem
            return null;
        }
    }


    public static String newGetResponse(String urlString) {
        HttpGet get = new HttpGet(urlString);
        // get.setHeader("Content-Type", "application/json; charset=utf-8");
        get.setHeader(HTTP.CONTENT_TYPE,
                "application/json");

        HttpResponse resp = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            resp = httpClient.execute(get);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resp != null) {
            if (resp != null) {
                int statusCode = resp.getStatusLine().getStatusCode();
                Log.i("getResponse", " == " + statusCode);
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    return "SERVER_ERROR";
                } else if (statusCode == 200) {
                    return "SUCCESS";
                } else {
                    return "FAILED";
                }
            } else {
                return null;
            }
        } else {
            // there was a problem
            return null;
        }
    }
}
