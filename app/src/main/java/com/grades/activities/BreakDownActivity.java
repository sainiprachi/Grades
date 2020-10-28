package com.grades.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.app.grades.R;
import com.google.gson.Gson;
import com.grades.adapters.BreakDownAdapters;
import com.grades.adapters.ClassesDetailsAdapter;
import com.grades.model.BreakDownModel;
import com.grades.utils.UserPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class BreakDownActivity extends AppCompatActivity implements ClassesDetailsAdapter.OnClickParent, View.OnClickListener {
    private static final String TAG = "BreakDownActivity";
    ProgressBar circularProgressBar;
    BreakDownAdapters breakDownAdapters;
    List<BreakDownModel.DBean.ResultsBean> breakDownList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_breakdown_dialog);
        initView();
    }


    private void initView() {
        circularProgressBar = findViewById(R.id.circularProgressBar);
        RecyclerView recyclerBreakDown = findViewById(R.id.recyclerBreakDown);
        RelativeLayout rlParent = findViewById(R.id.rlParent);
        breakDownAdapters = new BreakDownAdapters(this, breakDownList);
        recyclerBreakDown.setLayoutManager(new LinearLayoutManager(this));
        recyclerBreakDown.setAdapter(breakDownAdapters);
        TextView txtBreakDown = findViewById(R.id.txtBreakDown);
        TextView txtSubject = findViewById(R.id.txtSubject);
        ImageView ivClose = findViewById(R.id.ivClose);
        ivClose.setOnClickListener(this);
        String theme = UserPreference.getInstance(BreakDownActivity.this).getTheme();
        switch (theme) {
            case "Red":
                txtBreakDown.setTextColor(getResources().getColor(R.color.red));
                txtSubject.setTextColor(getResources().getColor(R.color.red));
                rlParent.setBackgroundColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkgray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }

                break;

            case "darkRed":
                txtBreakDown.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                rlParent.setBackgroundColor(getResources().getColor(R.color.red));
                //setTheme(R.style.darkRed);
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkRed)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkRed));
                }
                break;


            case "orange":
                txtSubject.setTextColor(getResources().getColor(R.color.orange));
                txtBreakDown.setTextColor(getResources().getColor(R.color.orange));
                rlParent.setBackgroundColor(getResources().getColor(R.color.white));

                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkgray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
                break;

            case "darkOrange":
                txtBreakDown.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                rlParent.setBackgroundColor(getResources().getColor(R.color.orange));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkOrange)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkOrange));
                }
                break;


            case "yellow":
                txtBreakDown.setTextColor(getResources().getColor(R.color.yellow));
                txtSubject.setTextColor(getResources().getColor(R.color.yellow));
                rlParent.setBackgroundColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkgray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }


                break;

            case "darkYellow":
                txtBreakDown.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                rlParent.setBackgroundColor(getResources().getColor(R.color.yellow));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkYellow)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkYellow));
                }
                break;

            case "green":
                txtBreakDown.setTextColor(getResources().getColor(R.color.green));
                txtSubject.setTextColor(getResources().getColor(R.color.green));
                rlParent.setBackgroundColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkgray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }


                break;


            case "darkGreen":
                txtBreakDown.setTextColor(getResources().getColor(R.color.white));
                rlParent.setBackgroundColor(getResources().getColor(R.color.green));
                txtSubject.setTextColor(getResources().getColor(R.color.green));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkGreen)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkGreen));
                }
                break;

            case "lightGreen":
                txtBreakDown.setTextColor(getResources().getColor(R.color.lightGreen));
                txtSubject.setTextColor(getResources().getColor(R.color.lightGreen));
                rlParent.setBackgroundColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkgray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
                break;

            case "newGreen":
                txtBreakDown.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.newGreen));
                rlParent.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.newGreen)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.newGreen));
                }
                break;


            case "purple":
                txtBreakDown.setTextColor(getResources().getColor(R.color.purple));
                txtSubject.setTextColor(getResources().getColor(R.color.purple));
                rlParent.setBackgroundColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkgray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
                break;

            case "darkPurple":
                txtBreakDown.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                rlParent.setBackgroundColor(getResources().getColor(R.color.purple));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkPurple)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkPurple));
                }
                break;


            case "blue":
                txtBreakDown.setTextColor(getResources().getColor(R.color.blue));
                txtSubject.setTextColor(getResources().getColor(R.color.blue));
                rlParent.setBackgroundColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkgray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
                break;

            case "darkBlueNew":
            default:

                txtBreakDown.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                rlParent.setBackgroundColor(getResources().getColor(R.color.blue));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(BreakDownActivity.this, R.color.darkBlueNew)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlueNew));
                }


        }
        if (getIntent() != null) {
            int gradebookNumber = getIntent().getIntExtra("gradebookNumber", 0);
            String term = getIntent().getStringExtra("term");
            String classname = getIntent().getStringExtra("classname");
            txtSubject.setText(classname);
            String Url = UserPreference.getInstance(this).getBaseUrl();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("gradebookNumber", gradebookNumber);
                jsonObject.put("term", term);
                jsonObject.put("requestedPage", 1);
                jsonObject.put("pageSize", 200);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  new GetJsonTask(Url.trim() + "m/api/MobileWebAPI.asmx/GetGradebookDetailedSummaryData", jsonObject).execute();
            callGetGrades(Url.trim() + "m/api/MobileWebAPI.asmx/GetGradebookDetailedSummaryData", jsonObject);
        }

    }


    private void callGetGrades(String Url, JSONObject jsonObject) {
        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
            circularProgressBar.setVisibility(View.VISIBLE);
        }

        String cook = UserPreference.getInstance(BreakDownActivity.this).getCookie();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        AndroidNetworking.post(Url)
                .setOkHttpClient(okHttpClient)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Cookie", "ASP.NET_SessionId=" + cook).build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resss", "" + response);
                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                            circularProgressBar.setVisibility(View.GONE);
                        }
                        if (!response.equals("")) {
                            Gson gson = new Gson();
                            BreakDownModel breakDownModel = gson.fromJson(response, BreakDownModel.class);
                            breakDownList.addAll(breakDownModel.getD().getResults());
                            breakDownAdapters.notifyDataSetChanged();


                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", "" + anError.getResponse());
                        if (anError.getErrorCode() != 0) {
                            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                                circularProgressBar.setVisibility(View.GONE);
                            }
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Toast.makeText(BreakDownActivity.this, "Session Expire Login Again", Toast.LENGTH_SHORT).show();
                            UserPreference.getInstance(BreakDownActivity.this).clearSession();
                            startActivity(new Intent(BreakDownActivity.this,LoginActivity.class));
                            finish();

                            Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + anError.getErrorBody());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                        }
                        Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
                    }
                });

    }


    @Override
    public void onClickItem(int position) {

    }

    @Override
    public void onSwipeSeekBar(int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivClose:
                onBackPressed();
                break;
        }
    }

    public class GetJsonTask extends AsyncTask<String, Void, String> {
        private String URL;
        private JSONObject jsonObjSend;
        String output = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                circularProgressBar.setVisibility(View.VISIBLE);
            }
        }

        public GetJsonTask(String URL, JSONObject jsonObjSend) {
            this.URL = URL;
            this.jsonObjSend = jsonObjSend;
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                java.net.URL url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Cookie", "ASP.NET_SessionId=grghuuhme0vnq1ebigcplfn3");
                conn.setRequestProperty("Content-type", "application/json");
                String input = jsonObjSend.toString();
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

              /*  if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }*/

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));


                System.out.println("Output from Server .... \n");
               /* while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }*/

                output = br.readLine();

                conn.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return output;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("gradeBook", "newwww" + s);
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                circularProgressBar.setVisibility(View.GONE);
            }
            if (!s.equals("")) {
                Gson gson = new Gson();
                BreakDownModel breakDownModel = gson.fromJson(s, BreakDownModel.class);
                breakDownList.addAll(breakDownModel.getD().getResults());
                breakDownAdapters.notifyDataSetChanged();
            }


        }
    }

}