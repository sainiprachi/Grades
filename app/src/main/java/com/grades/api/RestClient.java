package com.grades.api;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grades.utils.NetworkDetector;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class RestClient extends AppCompatActivity {

    private static RestClient sInstance;

    private static APIServices REST_CLIENT;
    private Context mContext;
    private String base_url;

    private RestClient(Context context, String BASE_URL) {
        mContext = context.getApplicationContext();
        base_url=BASE_URL;
        init(base_url);
    }

    public static RestClient getInstance(Context context, String BASE_URL) {
        if (sInstance == null) {
            sInstance = new RestClient(context,BASE_URL);
        }
        return sInstance;
    }


    private void init(String base_url) {

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        // init cookie manager
//        CookieHandler cookieHandler = new CookieManager();
//
//        OkHttpClient client = new OkHttpClient();
//                client.networkInterceptors(interceptor)
//                .cookieJar(new JavaNetCookieJar(cookieHandler))
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//

        try {
            CookieManager cookieManager = new CookieManager(new PersistentCookieStore(mContext), CookiePolicy.ACCEPT_ALL);
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
            okHttpClient.setCookieHandler(cookieManager);
            okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);



            okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {

                    return true;
                }
            });

            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();


            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(base_url)
                    .setConverter(new GsonConverter(gson))


                    .setClient(new OkClient(okHttpClient))
                    //  .setClient(new OkClient(okHttpClient))
                    .setErrorHandler(new CustomErrorHandler(mContext))
                    .build();


            REST_CLIENT = restAdapter.create(APIServices.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static APIServices get() {
        return REST_CLIENT;
    }

    private class CustomErrorHandler implements ErrorHandler {
        private final Context ctx;

        public CustomErrorHandler(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public Throwable handleError(RetrofitError cause) {
            String errorDescription = "sd";
            try {
                Response r = cause.getResponse();
                if (new NetworkDetector(ctx).isConnectingToInternet()) {

                    if (r.getStatus() == 500) {
                        errorDescription = "Server Error";

                    } else if (r.getStatus() == 401) {
                        errorDescription = "Unauthorized";

                        Toast.makeText(ctx, "Your access token has been Expired, Please login again to get new Token.", Toast.LENGTH_SHORT).show();


                    } else {
                        errorDescription = "Unable to communicate to server. Please try again.";
                        if (r.getStatus() == 200) {
                            errorDescription = "Rejected";
                        }
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
/*
                            new ProgressBarCustom(ctx).cancelProgress();
                            Toast.makeText(ctx, ctx.getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
*/
                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Exception(errorDescription);
        }
    }


    public static class AddCookiesInterceptor implements Interceptor {

        public static final String PREF_COOKIES = "PREF_COOKIES";
        private Context context;

        public AddCookiesInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            HashSet<String> preferences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES, new HashSet<String>());

            Request original = chain.request();

            builder.addHeader("Cookie", "AeriesNet=LastSC_0=608&LastSN_0=26399&LastID_0=191091348; ASP.NET_SessionId=wgk0hzjzejyiyzx11e5t1mpb");
            builder.addHeader("Postman-Token","<calculated when request is sent>");



            return chain.proceed(builder.build());
        }
    }







}



