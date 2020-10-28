package com.grades.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.app.grades.R;
import com.google.gson.Gson;
import com.grades.adapters.DistrictSearchAdapter;
import com.grades.model.Classes;
import com.grades.model.DistrictData;
import com.grades.utils.CSVFile;
import com.grades.utils.NetworkDetector;
import com.grades.utils.ServerRequest;
import com.grades.utils.UserPreference;
import com.grades.utils.WebkitCookieManagerProxy;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class LoginActivity extends AppCompatActivity {
    static final String COOKIES_HEADER = "Set-Cookie";
    private static final String TAG = "LoginActivity";
    private List<NameValuePair> updateData = new ArrayList<>();
    Button district, login;
    TextView troubleLogin;
    Context mContext;
    Activity mActivity;
    EditText email, password;
    // district data list
    List<DistrictData> districtData = new ArrayList<>();
    Dialog dialog;
    DistrictSearchAdapter districtSearchAdapter;

    NetworkDetector networkDetector;

    ProgressBar circularProgressBar;

    String loginName = "", pass_word = "";

    WebView wv;

    DefaultHttpClient defaultHttpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mContext = this;
        mActivity = this;
        new UserPreference(mContext);
        networkDetector = new NetworkDetector(mContext);

        initView();
    }

    private void initView() {
        wv = new WebView(this);


        district = (Button) findViewById(R.id.district);
        district.setOnClickListener(onClickListener);
        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(onClickListener);
        login.setVisibility(View.VISIBLE);
        troubleLogin = (TextView) findViewById(R.id.troubleLogin);
        troubleLogin.setOnClickListener(onClickListener);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        String loginName = "shilpasaboo@yahoo.com";
        String pass_word = "SanaySaboo110";

        email.setText(loginName);
        password.setText(pass_word);

        circularProgressBar = (ProgressBar) findViewById(R.id.circularProgressBar);
        circularProgressBar.setVisibility(View.GONE);
        // read district data csv from raw folder
        InputStream inputStream = getResources().openRawResource(R.raw.districts);
        CSVFile csvFile = new CSVFile(inputStream);
        districtData = csvFile.read();
        android.webkit.CookieSyncManager.createInstance(this);
        android.webkit.CookieManager.getInstance().setAcceptCookie(true);
        WebkitCookieManagerProxy coreCookieManager = new WebkitCookieManagerProxy(null, java.net.CookiePolicy.ACCEPT_ALL);
        java.net.CookieHandler.setDefault(coreCookieManager);
       // callLogin("https://my.iusd.org/LoginParent.aspx");
        LinearLayout contentView = (LinearLayout) findViewById(R.id.llParent);


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.district:
                    if (districtData != null && districtData.size() > 0) {
                        openFullscreenDialog(districtData);
                    }
                    break;
                case R.id.loginButton:
                    if (networkDetector.isConnectingToInternet()) {
                        loginName = email.getText().toString().trim();
                        pass_word = password.getText().toString().trim();
                        if (loginName != null && loginName.length() > 0 && !loginName.isEmpty()) {
                            if (pass_word != null && !pass_word.isEmpty()) {
                                String BASE_URL = UserPreference.getInstance(mContext).getBaseUrl();
                                if (district.getText().toString().trim().equalsIgnoreCase("Select District")) {
                                    Toast.makeText(LoginActivity.this, "Please select your district", Toast.LENGTH_SHORT).show();
                                } else {
                                    //  check base url
                                    if (BASE_URL != null && !BASE_URL.isEmpty() && BASE_URL.length() > 0) {

                                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                                            circularProgressBar.setVisibility(View.VISIBLE);
                                        }

                                        callLogin(BASE_URL.trim() + "LoginParent.aspx");

                                        // new RequestTask().execute(BASE_URL.trim() + "LoginParent.aspx");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "API base url not generate properly", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "no internet connection available", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.troubleLogin:

                    break;
                default:
                    break;
            }
        }
    };


    private class LoginAsyncTaskPost extends AsyncTask<String, Void, String> {
        String loginUrl = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String responce = "";
            loginUrl = params[0];
            String userName = params[1];
            String password = params[2];
            updateData.clear();
            // static setting for login
            updateData.add(new BasicNameValuePair("checkCookiesEnabled", "true"));
            updateData.add(new BasicNameValuePair("checkMobileDevice", "false"));
            updateData.add(new BasicNameValuePair("checkStandaloneMode", " false"));
            updateData.add(new BasicNameValuePair("checkTabletDevice", "false"));
            updateData.add(new BasicNameValuePair("portalAccountPassword", password));
            updateData.add(new BasicNameValuePair("portalAccountUsername", userName));
            updateData.add(new BasicNameValuePair("portalAccountUsernameLabel", ""));
            updateData.add(new BasicNameValuePair("submit", ""));
            Log.i("updateData", " === " + updateData);
            responce = ServerRequest.makePostRequestForLogin(loginUrl, updateData);
            return responce;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.d("resss",result);
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                circularProgressBar.setVisibility(View.GONE);
            }
            if (result != null && !result.isEmpty()) {
                if (result.equalsIgnoreCase("Success")) {
                    UserPreference.getInstance(LoginActivity.this).setUserName(loginName);
                    Toast.makeText(mContext, "Login successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mContext, ViewAnimationActivity.class));
                } else if (result.equalsIgnoreCase("Invalid Email or password")) {
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, "The Username and Password entered are incorrect.", Toast.LENGTH_SHORT).show();
                } else if (result.equalsIgnoreCase("Failed")) {
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, "Login failed", Toast.LENGTH_SHORT).show();
                } else {
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            } else {
                login.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, "Internal Server error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetLoginAsyncTask extends AsyncTask<String, Void, String> {
        String loginUrl = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                circularProgressBar.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String responce = "";
            loginUrl = params[0];
            responce = ServerRequest.getResponseMethod(loginUrl);
            return responce;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
                if (result.equalsIgnoreCase("SUCCESS")) {
                    new LoginAsyncTaskPost().execute(loginUrl, loginName, pass_word);
                } else {
                    Toast.makeText(mContext, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "Internal Server error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // open dialog for select distrcit
    private void openFullscreenDialog(List<DistrictData> districtData) {
        dialog = new Dialog(mContext, android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view1 = inflater.inflate(R.layout.district_dialog, null);
        ListView listView = view1.findViewById(R.id.listView);
        SearchView searchView = view1.findViewById(R.id.searchView);
        districtSearchAdapter = new DistrictSearchAdapter(mContext, districtData, new DistrictSearchAdapter.OnDistrictSelectLister() {
            @Override
            public void onSelectItem(String name, String code, String Url, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                district.setText(name);
                UserPreference.getInstance(mContext).setBaseUrl(Url);
            }
        });
        listView.setAdapter(districtSearchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                districtSearchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        dialog.setContentView(view1);
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }


    int code;

    class RequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                circularProgressBar.setVisibility(View.VISIBLE);
            }
        }

        String line = "";
        String loginUrl = "";

        @Override
        protected String doInBackground(String... uri) {
/*
            loginUrl = uri[0];
            String userName = uri[1];
            String password = uri[2];
*/
            try {

                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(uri[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("POST");
                    code = urlConnection.getResponseCode();
                    if (code != 200) {
                        throw new IOException("Invalid response from server: " + code);
                    }
                    updateData.add(new BasicNameValuePair("checkCookiesEnabled", "true"));
                    updateData.add(new BasicNameValuePair("checkMobileDevice", "true"));
                    updateData.add(new BasicNameValuePair("checkStandaloneMode", " false"));
                    updateData.add(new BasicNameValuePair("checkTabletDevice", "false"));
                    updateData.add(new BasicNameValuePair("portalAccountPassword", pass_word));
                    updateData.add(new BasicNameValuePair("portalAccountUsername", loginName));
                    updateData.add(new BasicNameValuePair("portalAccountUsernameLabel", ""));
                    updateData.add(new BasicNameValuePair("submit", ""));
                    final String COOKIES_HEADER = "Set-Cookie";
                    java.net.CookieManager msCookieManager = new java.net.CookieManager();

                    Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                    List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                    if (cookiesHeader != null) {
                        for (String cookie : cookiesHeader) {
                            msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                            // Log.d("Cookiie",cookie);
                            String[] cookieSp = cookie.split(";");
                            Log.d("Cookiie", cookieSp[0].trim());
                            UserPreference.getInstance(LoginActivity.this).setCookie(cookieSp[0].trim());

                        }
                    }

                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getQuery(updateData));
                    writer.flush();
                    writer.close();


                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));

                    line = rd.readLine();

                    while ((line = rd.readLine()) != null) {
                        Log.i("data", line);

                    }

                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.getMessage();
                //TODO Handle problems..
            }
            return line;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                circularProgressBar.setVisibility(View.GONE);
            }
            Log.i("dataOnPostLogin", "data" + s);
            Gson gson = new Gson();
            Classes classes = gson.fromJson(s, Classes.class);
            if (code == 200) {
                UserPreference.getInstance(LoginActivity.this).setUserName(email.getText().toString());
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));

            }
        }
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


    public static class AsyncHttpPost extends AsyncTask<String, String, String> {
        public interface Listener {
            void onResult(String result);
        }

        private Listener mListener;
        private HashMap<String, String> mData = null;// post data


        /**
         * constructor
         */
        public AsyncHttpPost(HashMap<String, String> data) {
            mData = data;
        }

        public void setListener(Listener listener) {
            mListener = listener;
        }

        /**
         * background
         */
        @Override
        protected String doInBackground(String... params) {
            byte[] result = null;
            String str = "";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(params[0]);// in this case, params[0] is URL
            try {
                // set up post data
                ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                for (String key : mData.keySet()) {
                    nameValuePair.add(new BasicNameValuePair(key, mData.get(key)));
                }

                post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                final String COOKIES_HEADER = "Set-Cookie";
                java.net.CookieManager msCookieManager = new java.net.CookieManager();

                Header[] headers = post.getAllHeaders();
                Log.d("Hedaer", "" + headers.length);
                Header[] header = post.getHeaders("Set-Cookie");
                Log.d("Header", "" + header.length);
                for (Header value : header) {
                    // httpGet.addHeader("Set-Cookie", (header[i].getValue()));

                    Log.d("Header", "" + value.getValue());
                }

                // List<String> cookiesHeader = headers.length

                /*if (cookiesHeader != null) {
                    for (Header cookie : headers) {
                        msCookieManager.getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
                        // Log.d("Cookiie",cookie);
                        String[] cookieSp=cookie.split(";");
                        Log.d("Cookiie",cookieSp[0].trim());
                        UserPrefrence.getInstance(LoginActivity.this).setCookie(cookieSp[0].trim());

                    }
                }*/
                HttpResponse response = client.execute(post);
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
                    result = EntityUtils.toByteArray(response.getEntity());
                    str = new String(result, "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }

        /**
         * on getting result
         */
        @Override
        protected void onPostExecute(String result) {
            // something...
            if (mListener != null) {
                mListener.onResult(result);
            }
        }
    }


    private void updateScreenTitle(String title) {
        setTitle(title);
    }

    private void callLogin(String s) {
        CookieJar cookieJar = new CookieJar() {

            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                // here you get the cookies from Response
                cookieStore.put(url.host(), cookies);
                for (Cookie cookie : cookies) {
                    ncookies = cookie.value();
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
               cookies = cookieStore.get(url.host());

                return cookies != null ? cookies : new ArrayList<>();
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
        AndroidNetworking.get(s)
                .setOkHttpClient(okHttpClient)
                .build()


                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resss", "" + response);
                        callLoginPost(s);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", "" + anError.getResponse());
                    }
                });

    }


    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
   static String ncookies;
    List<Cookie> cookies;




    private void callLoginPost(String s){
        loginName = email.getText().toString().trim();
        pass_word = password.getText().toString().trim();




        OkHttpClient okHttpClient = new OkHttpClient.Builder()

                .build();

        AndroidNetworking.post(s)
                .setOkHttpClient(okHttpClient)
                .addBodyParameter("checkCookiesEnabled", "true")
                .addBodyParameter("checkMobileDevice", "true")
                .addBodyParameter("checkStandaloneMode", " false")
                .addBodyParameter("checkTabletDevice", "false")
                .addBodyParameter("portalAccountPassword", pass_word)
                .addBodyParameter("portalAccountUsername", loginName)
                .addBodyParameter("portalAccountUsernameLabel", "")
                .addHeaders("Cookie","ASP.NET_SessionId="+ncookies)
                .addHeaders("Content-Type","text/html")
                .addHeaders("Content-Encoding","gzip")
                .addHeaders("Last-Modified","Tue, 22 Sep 2020 00:12:41 GMT")
                .addHeaders("Accept-Ranges","bytes")
                .addHeaders("ETag","e6138d167590d61:0")
                .addHeaders("Vary","Accept-Encoding")
                .addHeaders("Server","Microsoft-IIS/8.5")
                .addHeaders("X-Frame-Options","SAMEORIGIN")
                .addHeaders("Date","Wed, 23 Sep 2020 08:56:54 GMT")
                .addHeaders("Content-Length","9652")
                .build()


                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resss", "" + response);
                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                            circularProgressBar.setVisibility(View.GONE);
                        }
                        UserPreference.getInstance(LoginActivity.this).setCookie(ncookies);
                        UserPreference.getInstance(LoginActivity.this).setUserName(loginName);
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                       // callHome();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", "" + anError.getResponse());
                    }
                });

    }

    private void callHome() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
               // .cookieJar(cookieJar)
                .followRedirects(false)
                .followSslRedirects(false)

                .build();
        AndroidNetworking.get("https://my.iusd.org/m/api/MobileWebAPI.asmx/GetStudentDemographicsData")
                .setOkHttpClient(okHttpClient)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Cookie","ASP.NET_SessionId="+ncookies)

                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resss", "" + response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", "" + anError.getResponse());
                        if (anError.getErrorCode() != 0) {
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                    }
                });

    }


}







