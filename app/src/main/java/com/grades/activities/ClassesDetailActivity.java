package com.grades.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.app.grades.R;
import com.google.gson.Gson;
import com.grades.TrendsActivity;
import com.grades.adapters.ClassesDetailsAdapter;
import com.grades.model.ClassesDetailModel;
import com.grades.model.GradeBookDetailSummary;
import com.grades.utils.UserPreference;

import org.apache.http.NameValuePair;
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
import java.util.Objects;

import io.blackbox_vision.wheelview.view.WheelView;
import okhttp3.OkHttpClient;

public class ClassesDetailActivity extends AppCompatActivity implements ClassesDetailsAdapter.OnClickParent, View.OnClickListener {
    private static final String TAG = "ClassesDetailActivity";
    RelativeLayout rlMock;
    View viewMock;
    LinearLayout llPercentage;
    TextView txtAddMock;
    ProgressBar circularProgressBar;
    int gradebookNumber;
    String term;
    ClassesDetailsAdapter classesDetailsAdapter;
    String classname;
    RelativeLayout llParent;
    List<ClassesDetailModel.DBean.ResultsBean> resultsBeans = new ArrayList<>();
    TextView txtPercentage;
    TextView txtGetPercentage;
    TextView txtMaxPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_detail);
        initView();
    }

    RecyclerView recyclerClassesDetail;
    private List<NameValuePair> updateData = new ArrayList<>();

    private void initView() {
        recyclerClassesDetail = findViewById(R.id.recyclerClassesDetail);
        recyclerClassesDetail.setLayoutManager(new LinearLayoutManager(this));
        viewMock = findViewById(R.id.viewMock);
        llPercentage = findViewById(R.id.llPercentage);
        txtMaxPercentage = findViewById(R.id.txtMaxPercentage);
        txtGetPercentage = findViewById(R.id.txtGetPercentage);
        classesDetailsAdapter = new ClassesDetailsAdapter(resultsBeans, ClassesDetailActivity.this, this, false);
        recyclerClassesDetail.setAdapter(classesDetailsAdapter);
        rlMock = findViewById(R.id.rlMock);
        txtPercentage = findViewById(R.id.txtPercentage);
        llParent = findViewById(R.id.llParent);
        TextView txtSubject = findViewById(R.id.txtSubject);
        ImageView ivClose = findViewById(R.id.ivClose);
        TextView txtAddMock = findViewById(R.id.txtAddMock);
        ImageView ivCalculator = findViewById(R.id.ivCalculator);
        ImageView ivMenu = findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        ivCalculator.setOnClickListener(this);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        txtAddMock.setOnClickListener(this);
        if (getIntent() != null) {
            gradebookNumber = getIntent().getIntExtra("gradebookNumber", 0);
            term = getIntent().getStringExtra("term");
            classname = getIntent().getStringExtra("classname");
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
            //new GetJsonTask(Url.trim() + "m/api/MobileWebAPI.asmx/GetGradebookDetailsData", jsonObject).execute();

            callGetClass(Url.trim()+"m/api/MobileWebAPI.asmx/GetGradebookDetailsData",jsonObject);
        }

        String theme = UserPreference.getInstance(ClassesDetailActivity.this).getTheme();
        switch (theme) {
            case "Red":
                // setTheme(R.style.red);
                txtPercentage.setTextColor(getResources().getColor(R.color.red));
                txtSubject.setTextColor(getResources().getColor(R.color.gray));
                llParent.setBackgroundColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }


                break;

            case "darkRed":
                llParent.setBackgroundColor(getResources().getColor(R.color.red));
                txtPercentage.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkRed)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkRed)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkRed)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkRed));
                }

                //setTheme(R.style.darkRed);

                break;


            case "orange":
                llParent.setBackgroundColor(getResources().getColor(R.color.white));
                txtPercentage.setTextColor(getResources().getColor(R.color.orange));
                txtSubject.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }


                break;

            case "darkOrange":
                llParent.setBackgroundColor(getResources().getColor(R.color.orange));
                txtPercentage.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkOrange)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkOrange)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkOrange)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkOrange));
                }

                break;


            case "yellow":
                llParent.setBackgroundColor(getResources().getColor(R.color.white));
                txtPercentage.setTextColor(getResources().getColor(R.color.yellow));
                txtSubject.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }

                break;

            case "darkYellow":
                llParent.setBackgroundColor(getResources().getColor(R.color.yellow));
                txtPercentage.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkYellow)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkYellow)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkYellow)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkYellow));
                }

                break;

            case "green":
                llParent.setBackgroundColor(getResources().getColor(R.color.white));
                txtPercentage.setTextColor(getResources().getColor(R.color.green));
                txtSubject.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.green));
                }


                break;


            case "darkGreen":
                llParent.setBackgroundColor(getResources().getColor(R.color.green));
                txtPercentage.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkGreen)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkGreen)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkGreen)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkGreen));
                }

                break;

            case "lightGreen":
                llParent.setBackgroundColor(getResources().getColor(R.color.white));
                txtPercentage.setTextColor(getResources().getColor(R.color.lightGreen));
                txtSubject.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }

                break;

            case "newGreen":
                llParent.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                txtPercentage.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.newGreen)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.newGreen)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.newGreen)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.newGreen));
                }

                break;


            case "blue":
                llParent.setBackgroundColor(getResources().getColor(R.color.white));
                txtPercentage.setTextColor(getResources().getColor(R.color.blue));
                txtSubject.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }

                break;

            case "purple":
                llParent.setBackgroundColor(getResources().getColor(R.color.white));
                txtPercentage.setTextColor(getResources().getColor(R.color.purple));
                txtSubject.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkgray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }

                break;

            case "darkPurple":
                llParent.setBackgroundColor(getResources().getColor(R.color.purple));
                txtPercentage.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkPurple)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkPurple)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkPurple)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkPurple));
                }


                break;

            case "darkBlueNew":

            default:
                llParent.setBackgroundColor(getResources().getColor(R.color.blue));
                txtPercentage.setTextColor(getResources().getColor(R.color.white));
                txtSubject.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkBlueNew)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkBlueNew)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.getDrawable()),
                        ContextCompat.getColor(ClassesDetailActivity.this, R.color.darkBlueNew)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlueNew));
                }


        }

    }


    @Override
    public void onClickItem(int position) {
        resultsBeans.get(position).setSeekBarVisible(true);
        classesDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwipeSeekBar(int position) {
        calculateGrade();
    }

    public interface OnClickCalculator {
        void OnClick();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMenu:
                populateViewForDialog();
                break;

            case R.id.ivClose:
                onBackPressed();
                break;

            case R.id.txtAddMock:
                addMockAssignment();
                break;

            case R.id.ivCalculator:
                if (rlMock.getVisibility()==View.VISIBLE){
                    rlMock.setVisibility(View.GONE);
                }else {
                    String theme = UserPreference.getInstance(ClassesDetailActivity.this).getTheme();
                    switch (theme) {
                        case "Red":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.darkRed));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.red));


                            break;

                        case "darkRed":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.darkRed));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkRed));

                            //setTheme(R.style.darkRed);

                            break;


                        case "orange":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.orange));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkOrange));
                            break;

                        case "darkOrange":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.darkOrange));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkOrange));

                            break;


                        case "yellow":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.yellow));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                            break;

                        case "darkYellow":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                            break;

                        case "green":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.green));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkGreen));

                            break;


                        case "darkGreen":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.darkGreen));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkGreen));

                            break;

                        case "lightGreen":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.newGreen));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.lightGreen));

                        case "newGreen":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.newGreen));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.newGreen));

                            break;


                        case "blue":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.darkBlueNew));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.blue));

                            break;

                        case "purple":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.purple));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkPurple));

                            break;

                        case "darkPurple":
                            viewMock.setBackgroundColor(getResources().getColor(R.color.darkPurple));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkPurple));

                            break;

                        case "darkBlueNew":

                        default:
                            viewMock.setBackgroundColor(getResources().getColor(R.color.darkBlueNew));
                            rlMock.setBackgroundColor(getResources().getColor(R.color.darkBlueNew));


                    }
                    rlMock.setVisibility(View.VISIBLE);
                    if (resultsBeans.size() > 0) {
                        resultsBeans.get(0).setSeekBarVisible(true);
                        classesDetailsAdapter.notifyDataSetChanged();

                    }
                }

                break;

        }
    }


    public void populateViewForDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        String theme = UserPreference.getInstance(ClassesDetailActivity.this).getTheme();
        View popupView = inflater.inflate(R.layout.popup_menu_layout, null);
        PopupWindow pw = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        CardView cardParent = popupView.findViewById(R.id.cardParent);
        TextView txtBreakDown = popupView.findViewById(R.id.txtBreakDown);
        TextView txtTrends = popupView.findViewById(R.id.txtTrends);
        TextView txtShowImpact=popupView.findViewById(R.id.txtShowImpact);
        txtShowImpact.setText("Show Impacts");
        txtTrends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClassesDetailActivity.this, TrendsActivity.class));
            }
        });

        txtShowImpact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtShowImpact.getText().toString().equals("Show Impacts")){
                    for (int i = 0; i < resultsBeans.size(); i++) {
                        resultsBeans.get(i).setShowImpact(true);
                    }
                    classesDetailsAdapter.notifyDataSetChanged();
                    txtShowImpact.setText("Hide Impacts");
                }else {
                    for (int i = 0; i < resultsBeans.size(); i++) {
                        resultsBeans.get(i).setShowImpact(false);
                    }

                    classesDetailsAdapter.notifyDataSetChanged();
                    pw.dismiss();
                }

            }
        });
        txtBreakDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassesDetailActivity.this, BreakDownActivity.class);
                intent.putExtra("term", term);
                intent.putExtra("classname", classname);
                intent.putExtra("gradebookNumber", gradebookNumber);
                startActivity(intent);

            }
        });
        pw.showAtLocation(this.findViewById(R.id.ivMenu), Gravity.TOP | Gravity.RIGHT, 20, 120);
        switch (theme) {
            case "Red":
                // setTheme(R.style.red);
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.red));


                break;

            case "darkRed":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.darkRed));

                break;


            case "orange":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.orange));


                break;

            case "darkOrange":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.darkOrange));

                break;


            case "yellow":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.yellow));

                break;

            case "darkYellow":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.darkYellow));

                break;

            case "green":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.green));


                break;


            case "darkGreen":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.darkGreen));

                break;

            case "lightGreen":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.lightGreen));

                break;

            case "newGreen":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.newGreen));

                break;


            case "blue":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.blue));
                break;

            case "purple":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.purple));

                break;

            case "darkPurple":
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.darkPurple));


                break;

            case "darkBlueNew":

            default:
                cardParent.setCardBackgroundColor(getResources().getColor(R.color.darkBlueNew));


        }


    }

    private void addMockAssignment() {
        final String[] type = {""};
        List<String> options = new ArrayList<>();
        final Dialog dialog = new Dialog(this, R.style.PopupDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_mock_dialog);
        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        WheelView picker_ui_view = dialog.findViewById(R.id.loop_view);
        Button btnAddAssignment = dialog.findViewById(R.id.btnAddAssignment);
        TextView txtHeading = dialog.findViewById(R.id.txtHeading);
        EditText edtAssignmentName = dialog.findViewById(R.id.edtAssignmentName);
        EditText edtTotalMarks = dialog.findViewById(R.id.edtTotalMarks);
        EditText edtMarks = dialog.findViewById(R.id.edtMarks);

        edtMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtMarks.getText().length()==2){
                    edtTotalMarks.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        llParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                RelativeLayout contentView = (RelativeLayout) findViewById(R.id.llParent);
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                Log.d("Nifras", "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // lytMaster.setTop(0);

                    picker_ui_view.setVisibility(View.GONE);

                }
                else {
                    picker_ui_view.setVisibility(View.VISIBLE);

                    // lytMaster.setBaselineAligned(true);
                    // keyboard is closed
                }
            }
        });


        btnAddAssignment.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtAssignmentName.getText().toString())) {
                Toast.makeText(ClassesDetailActivity.this, "Enter Assignment", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(edtMarks.getText().toString())) {
                Toast.makeText(ClassesDetailActivity.this, "Enter Marks", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(edtTotalMarks.getText().toString())) {
                Toast.makeText(ClassesDetailActivity.this, "Enter Total Marks", Toast.LENGTH_SHORT).show();
            } else if (Double.parseDouble(edtMarks.getText().toString())>Double.parseDouble(edtTotalMarks.getText().toString())){
                Toast.makeText(this, "Marks Should not be Greater than total marks", Toast.LENGTH_SHORT).show();
            }

            else {
                dialog.dismiss();
                boolean isGraded=false;
                for (int i = 0; i <resultsBeans.size() ; i++) {
                    if (resultsBeans.get(i).isIsGraded()){
                     isGraded=true;
                    }
                }
                ClassesDetailModel.DBean.ResultsBean resultsBean = new ClassesDetailModel.DBean.ResultsBean();
                resultsBean.setType(type[0]);
                resultsBean.setDescription(edtAssignmentName.getText().toString());
                resultsBean.setMaxScore(Double.parseDouble(edtTotalMarks.getText().toString()));
                resultsBean.setScore(Double.parseDouble(edtMarks.getText().toString()));
                double percent=resultsBean.getScore()/resultsBean.getMaxScore()*100.00;
                resultsBean.setPercent(percent);
                resultsBean.setSeekBarVisible(true);
                resultsBean.setIsGraded(isGraded);
                resultsBean.setAddedMock(true);
                resultsBeans.set(0, resultsBean);
                classesDetailsAdapter.notifyDataSetChanged();
                calculateGrade();






            }
        });

        boolean isShow=false;

      /*  if (edtAssignmentName.hasFocusable()){
            picker_ui_view.setVisibility(View.GONE);
            edtAssignmentName.lo
        }

        if (isShow){
            picker_ui_view.setVisibility(View.VISIBLE);
        }*/


        ImageView ivCloseDialog = dialog.findViewById(R.id.ivCloseDialog);
        ivCloseDialog.setOnClickListener(view -> dialog.dismiss());

        for (int i = 0; i < resultsBeans.size(); i++) {
            if (!options.contains(resultsBeans.get(i).getType())) {
                options.add(resultsBeans.get(i).getType());
            }
        }
        picker_ui_view.setCanLoop(false);

        picker_ui_view.setLoopListener(item -> {
            type[0] = options.get(item);
        });
        picker_ui_view.setTextSize(14);
        if (options.size()>0){
            picker_ui_view.setItems(options);
        }

        String theme = UserPreference.getInstance(ClassesDetailActivity.this).getTheme();
        switch (theme) {
            case "Red":
                txtHeading.setTextColor(getResources().getColor(R.color.red));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_red);
                break;

            case "darkRed":
                txtHeading.setTextColor(getResources().getColor(R.color.darkRed));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_darkred);

                //setTheme(R.style.darkRed);

                break;


            case "orange":
                txtHeading.setTextColor(getResources().getColor(R.color.orange));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_orange);


                break;

            case "darkOrange":
                txtHeading.setTextColor(getResources().getColor(R.color.darkOrange));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_orange);

                break;


            case "yellow":
                txtHeading.setTextColor(getResources().getColor(R.color.yellow));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_yellow);
                break;

            case "darkYellow":
                txtHeading.setTextColor(getResources().getColor(R.color.darkYellow));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_yellow);
                break;

            case "green":
                txtHeading.setTextColor(getResources().getColor(R.color.green));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_green);

                break;


            case "darkGreen":
                txtHeading.setTextColor(getResources().getColor(R.color.darkGreen));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_green);

                break;

            case "lightGreen":
                txtHeading.setTextColor(getResources().getColor(R.color.lightGreen));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_light_green);

            case "newGreen":
                txtHeading.setTextColor(getResources().getColor(R.color.newGreen));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_new_green);

                break;


            case "blue":
                txtHeading.setTextColor(getResources().getColor(R.color.blue));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_blue);

                break;

            case "purple":
                txtHeading.setTextColor(getResources().getColor(R.color.purple));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_purple);

                break;

            case "darkPurple":
                txtHeading.setTextColor(getResources().getColor(R.color.darkPurple));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_purple);

                break;

            case "darkBlueNew":

            default:
                txtHeading.setTextColor(getResources().getColor(R.color.darkBlueNew));
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_blue_new);


        }
        // picker_ui_view.setVisibility(View.VISIBLE);


        dialog.show();


    }

    private boolean keyboardShown(View rootView) {

        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
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
                URL url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Cookie", "ASP.NET_SessionId=itv3gqf2ll02kheyg02www03");
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
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                circularProgressBar.setVisibility(View.GONE);
            }
            super.onPostExecute(s);
            Log.d("OutPut", "newwww" + s);
            if (!s.equals("")) {
                Gson gson = new Gson();
                ClassesDetailModel classes = gson.fromJson(s, ClassesDetailModel.class);
                resultsBeans.addAll(classes.getD().getResults());
                assignmentList = new ArrayList<>(classes.getD().getResults());
                classesDetailsAdapter.notifyDataSetChanged();
            }
            String Url = UserPreference.getInstance(ClassesDetailActivity.this).getBaseUrl();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("gradebookNumber", gradebookNumber);
                jsonObject.put("term", term);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            new GetGrades(Url.trim() + "/m/api/MobileWebAPI.asmx/GetGradebookDetailedSummaryData", jsonObject).execute();


        }

    }


    public class GetGrades extends AsyncTask<String, Void, String> {
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

        public GetGrades(String URL, JSONObject jsonObjSend) {
            this.URL = URL;
            this.jsonObjSend = jsonObjSend;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Cookie", "ASP.NET_SessionId=uyq5vd4guki5rjkhaeqhzwlb");
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
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                circularProgressBar.setVisibility(View.GONE);
            }
            super.onPostExecute(s);
            Log.d("OutPutGrade", "newwww" + s);
            if (!s.equals("")) {
                Gson gson = new Gson();
                GradeBookDetailSummary classes = gson.fromJson(s, GradeBookDetailSummary.class);
                List<GradeBookDetailSummary.DBean.ResultsBean> resultsBeans = new ArrayList<>(classes.getD().getResults());
                tempBeans = new ArrayList<>(classes.getD().getResults());
                for (int i = 0; i < resultsBeans.size(); i++) {
                    if (!resultsBeans.get(i).getType().equals("TOTAL")) {
                        GradeBookDetailSummary.DBean.ResultsBean resultsBean = resultsBeans.get(i);
                        tempBeans.add(resultsBean);
                        if (resultsBeans.get(i).isIsDoingWeight()) {
                            isDoingWeight = true;
                        }


                    }
                }
                calculateGrade();
                txtMaxPercentage.setText(percentFinal+"%");


            }


        }

        /*calculate grade*/


    }

    double percentFinal;

    private void calculateGrade() {
        if (isDoingWeight) {
            ScorePair scorePair = new ScorePair();
            for (ClassesDetailModel.DBean.ResultsBean resultsBean : resultsBeans) {
                boolean isGraded = resultsBean.isIsGraded();
                double score = resultsBean.getScore();
                double maxScore = resultsBean.getMaxScore();
                if (isGraded) {
                    scorePair.first += score;
                    scorePair.second += maxScore;
                }

            }
            // Convert them to a percent and then return it
            double finalScore = (scorePair.first / scorePair.second) * 100.0;
            @SuppressLint("DefaultLocale") String percentString = String.format("%.2f", finalScore);
            double percent = Double.parseDouble(percentString);
            Log.d("isgraded", "" + percent);
            txtPercentage.setText(percent + "%");
            percentFinal=percent;
            txtGetPercentage.setText(percent+"%");
            // Round it to two decimal places first
        } else {

           // List<GradeBookDetailSummary.DBean.ResultsBean> tempBean = new ArrayList<>();

            double totalWeight=1.0;
            double resultGrade = 0.0;

            List<GradeBookDetailSummary.DBean.ResultsBean>tempBeansnew=new ArrayList<>();
            ScorePair scorePair = new ScorePair();

            for (ClassesDetailModel.DBean.ResultsBean resultsBean : resultsBeans) {
                boolean isGraded = resultsBean.isIsGraded();
                double score = resultsBean.getScore();
                double maxScore = resultsBean.getMaxScore();
                if (isGraded) {
                    scorePair.first += score;
                    scorePair.second += maxScore;
                }

            }

            List<ScorePair>scorePairs=new ArrayList<>();
            scorePairs.add(scorePair);

            for (int i = 0; i < tempBeans.size(); i++) {
                if (scorePair.second ==0.0) {
                    GradeBookDetailSummary.DBean.ResultsBean resultsBean = tempBeans.get(i);
                    //tempBeans.remove(i);
                    totalWeight -= resultsBean.getPercentOfGrade()/100.0;
                    String percentString = String.format("%.2f", totalWeight);
                    totalWeight = Double.parseDouble(percentString);
                }

            }

            for (int i = 0; i < tempBeans.size(); i++) {
               if (tempBeans.get(i).getPercentEarned()!=0.0){
                   GradeBookDetailSummary.DBean.ResultsBean resultsBean = tempBeans.get(i);
                   tempBeansnew.add(resultsBean);
               }
            }




            for (int i = 0; i < tempBeansnew.size(); i++) {
                if (tempBeansnew.get(i).getPercentOfGrade() != 0.0 && totalWeight != 0.0) {
                    double weight = tempBeansnew.get(i).getPercentOfGrade() / totalWeight;
                    String weightS = String.format("%.2f", weight);
                    weight = Double.parseDouble(weightS);
                    double totalUnWeightedScore = Double.parseDouble(String.format("%.2f", tempBeansnew.get(i).getMaxPoints()))
                            / Double.parseDouble(String.format("%.2f", tempBeansnew.get(i).getNumberOfPoints()));
                    resultGrade += weight * totalUnWeightedScore;
                    resultGrade = Double.parseDouble(String.format("%.2f", resultGrade));
                }


            }

            resultGrade = (resultGrade * 100) / 100;
            resultGrade = Double.parseDouble(String.format("%.2f", resultGrade));
            Log.d("resultnnnnnnn", "" + resultGrade);
            txtPercentage.setText(resultGrade + "%");
            percentFinal=resultGrade;
            txtGetPercentage.setText(resultGrade+"%");

        }
    }

    boolean isDoingWeight;
    double totalWeight = 1.0;
    List<ClassesDetailModel.DBean.ResultsBean> assignmentList;
    List<GradeBookDetailSummary.DBean.ResultsBean> tempBeans=new ArrayList<>();

    private static class ScorePair {
        public double first = 0;
        public double second = 0;

    }

    private void callGetClass(String Url,JSONObject jsonObject) {
        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
            circularProgressBar.setVisibility(View.VISIBLE); }
        String cook = UserPreference.getInstance(ClassesDetailActivity.this).getCookie();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        AndroidNetworking.post(Url)
                .setOkHttpClient(okHttpClient)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Cookie", "ASP.NET_SessionId="+cook).build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resss", "" + response);
                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                            circularProgressBar.setVisibility(View.GONE);
                        }


                        if (!response.equals("")) {
                            Gson gson = new Gson();
                            ClassesDetailModel classes = gson.fromJson(response, ClassesDetailModel.class);
                            resultsBeans.addAll(classes.getD().getResults());
                            assignmentList = new ArrayList<>(classes.getD().getResults());
                            classesDetailsAdapter.notifyDataSetChanged();
                        }
                        String Url = UserPreference.getInstance(ClassesDetailActivity.this).getBaseUrl();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("gradebookNumber", gradebookNumber);
                            jsonObject.put("term", term);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //new GetGrades(Url.trim() + "/m/api/MobileWebAPI.asmx/GetGradebookDetailedSummaryData", jsonObject).execute();

                        callGetGrades(Url.trim() + "/m/api/MobileWebAPI.asmx/GetGradebookDetailedSummaryData", jsonObject);

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

    private void callGetGrades(String Url,JSONObject jsonObject) {
        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
            circularProgressBar.setVisibility(View.VISIBLE);
        }

        String cook = UserPreference.getInstance(ClassesDetailActivity.this).getCookie();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        AndroidNetworking.post(Url)
                .setOkHttpClient(okHttpClient)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Cookie", "ASP.NET_SessionId="+cook).build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resss", "" + response);
                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                            circularProgressBar.setVisibility(View.GONE);
                        }
                        if (!response.equals("")) {
                            Gson gson = new Gson();
                            GradeBookDetailSummary classes = gson.fromJson(response, GradeBookDetailSummary.class);
                            List<GradeBookDetailSummary.DBean.ResultsBean> resultsBeans = new ArrayList<>(classes.getD().getResults());
                            //tempBeans = new ArrayList<>(classes.getD().getResults());
                            for (int i = 0; i < resultsBeans.size(); i++) {
                                if (!resultsBeans.get(i).getType().equals("TOTAL")) {
                                    GradeBookDetailSummary.DBean.ResultsBean resultsBean = resultsBeans.get(i);
                                    tempBeans.add(resultsBean);
                                    if (!resultsBeans.get(i).isIsDoingWeight()) {
                                        isDoingWeight = true;
                                    }


                                }
                            }
                            calculateGrade();
                            txtMaxPercentage.setText(percentFinal+"%");


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







