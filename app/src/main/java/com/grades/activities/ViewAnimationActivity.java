package com.grades.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.app.grades.R;
import com.google.gson.Gson;
import com.grades.utils.ServerRequest;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ViewAnimationActivity extends AppCompatActivity {
    LinearLayout layoutbg;
    int[] BackGroundColoranimate = {Color.parseColor("#FF5733"), Color.parseColor("#F6FF33"), Color.parseColor("#ED33FF"), Color.parseColor("#FF334F"), Color.parseColor("#258BD4")};
    ProgressBar circularProgressBar;
    Runnable runnable;
    Handler handler;
    private boolean run = true;

    public void stopThread() {
        run = false;
    }

    volatile boolean doStop = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        layoutbg = findViewById(R.id.layoutbg);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        circularProgressBar.setVisibility(View.VISIBLE);
          startActivity(new Intent(ViewAnimationActivity.this, HomeActivity.class));

/*
        handler = new Handler();
        runnable = new Runnable() {
            int i;

            public void run() {
                while (run) {
                    for (i = 0; i <= BackGroundColoranimate.length; i++) {
                        if (i == BackGroundColoranimate.length) {
                            i = 0;
                        }


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layoutbg.setBackgroundColor(BackGroundColoranimate[i]);
                                final String BASE_URL = UserPrefrence.getInstance(ViewAnimationActivity.this).getBaseUrl();
                                if (BASE_URL != null && !BASE_URL.isEmpty()) {

                                    //getStudentList(BASE_URL + "m/api/MobileWebAPI.asmx/GetStudentsOfCurrentAccount");
                                    //new GetLoginAsyncTask().execute(BASE_URL.trim()+"m/api/MobileWebAPI.asmx/GetStudentsOfCurrentAccount");

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String strRes=executeGetRequest(BASE_URL.trim()+"m/api/MobileWebAPI.asmx/GetStudentsOfCurrentAccount");
                                            assert strRes != null;
                                            Log.d("responseData",strRes);
                                        }
                                    }).start();
                                    //new GetAsynTask().execute(BASE_URL.trim() + "m/api/MobileWebAPI.asmx/GetStudentsOfCurrentAccount");

                                    //startActivity(new Intent(ViewAnimationActivity.this, MainActivity.class));

                                } else {
                                    Toast.makeText(ViewAnimationActivity.this, "API base url not generate properly", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 0);
                        //Add some downtime
                        SystemClock.sleep(1500);
                    }

                    //large code block
                    if (!run) break; //to make sure the thread will stop in time
                    //another large code block
                }
            }
        };
        new Thread(runnable).start();
*/

    }

    boolean bExit = false;

    public void exit(boolean bExit) {
        this.bExit = bExit;
    }

    private void getStudentList(String BASE_URL) {
        AndroidNetworking.get(BASE_URL)
                .setTag("test")
                .addHeaders("Content-Type:application/json;")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("resss", response);
                        doStop = true;

                        //startActivity(new Intent(ViewAnimationActivity.this, MainActivity.class));
                        try {
                            Gson gson = new Gson();
                            JSONObject jsonObject = new JSONObject(response);
                            //boolean status = jsonObject.getBoolean("status");
                            //startActivity(new Intent(ViewAnimationActivity.this, MainActivity.class));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("anr", anError.getErrorBody());

                    }
                });
    }


    private class GetLoginAsyncTask extends AsyncTask<String, Void, String> {
        String loginUrl = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                circularProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected String doInBackground(String... params) {
            String responce;
            loginUrl = params[0];
            responce = ServerRequest.getResponseMethod(loginUrl);
            return responce;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("response", result);
            if (!result.isEmpty()) {
                if (result.equalsIgnoreCase("SUCCESS")) {

                } else {
                }
            } else {
                Toast.makeText(ViewAnimationActivity.this, "Internal Server error", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class GetAsynTask extends AsyncTask<String, Void, String> {
        String loginUrl = "";

        @Override
        protected String doInBackground(String... params) {
            String responce;
            loginUrl = params[0];
            try {
                // Creating & connection Connection with url and required Header.
                URL url = new URL(loginUrl);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                urlConnection.setRequestMethod("GET");   //POST or GET
                urlConnection.connect();


                // Write Request to output stream to server.
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());

                out.close();

                // Check the connection status.
                int statusCode = urlConnection.getResponseCode();

                // Connection success. Proceed to fetch the response.
                if (statusCode == 200) {
                    InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    StringBuilder dta = new StringBuilder();
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        dta.append(chunks);
                    }
                    String returndata = dta.toString();
                    return returndata;
                } else {
                    Toast.makeText(ViewAnimationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String resultData) {
            super.onPostExecute(resultData);
            Toast.makeText(ViewAnimationActivity.this, "" + resultData, Toast.LENGTH_SHORT).show();
           /* try {
                JSONObject obj = new JSONObject(resultData);
                String name= obj.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
    }


    public static String executeGetRequest(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            Log.e("timer", reqUrl + "&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
            }
            conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
            HttpURLConnection.setFollowRedirects(false);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");

            if (200 != conn.getResponseCode()) {


                // throw new CustomException("Failed to upload code:" + connection.getResponseCode() + " " + connection.getResponseMessage());
                // inputStream= conn.getInputStream();
                String result = convertStreamToString(conn.getErrorStream());
                System.out.println("Response Error " + result);

                return result;

            }
//                conn.setReadTimeout(60000);
            // read the response

            if (conn.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                // InputStream in = new BufferedInputStream(conn.getInputStream());

                response = convertStreamToString(in);
            } else {
                System.out.println("Errro Vik " + conn.getErrorStream());
                System.out.println("Errro Vik " + convertStreamToString(conn.getErrorStream()));
                System.out.println("Errro Vik " + conn.getErrorStream());
                // System.out.println("Errro Vik "+in.toString());
            }


                /*InputStream in = conn.getInputStream();
                String encoding = conn.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                String body = IOUtils.toString(in, encoding);*/

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("error", "Network is unreachable");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("error", "Network is unreachable");
            return new JSONObject(map).toString();
        }
        return response;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}
