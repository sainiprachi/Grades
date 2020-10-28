package com.grades.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.app.grades.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.grades.adapters.ClassesAdapter;
import com.grades.adapters.DrawerAdapter;
import com.grades.adapters.ThemeAdapter;
import com.grades.model.Classes;
import com.grades.model.DrawerItemModel;
import com.grades.model.StudentOFCurrentAccountModel;
import com.grades.model.ThemeModel;
import com.grades.roomdatabase.AppDataBase;
import com.grades.roomdatabase.model.ClassModel;
import com.grades.roomdatabase.model.StudentsOfCurrentAccountModelDb;
import com.grades.utils.Admob;
import com.grades.utils.RecyclerTouchListener;
import com.grades.utils.UserPreference;
import com.grades.utils.WebkitCookieManagerProxy;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import okhttp3.OkHttpClient;

public class HomeActivity extends BaseActivity implements DrawerAdapter.EventListener, ClassesAdapter.OnClickParent {
    private static final String TAG = "HomeActivity";
    List<DrawerItemModel> stList = new ArrayList<>();
    NavigationView navMain;
    FrameLayout frame;
    RecyclerTouchListener touchListener;
    RecyclerView recyclerClasses;
    ImageView btn_menu;
    TextView txtClasses, txtUserName, txtGrade;
    ClassesAdapter classesAdapter;
    private ProgressBar circularProgressBar;
    List<ClassModel> getClassesList = new ArrayList<>();
    private AppDataBase db;
    private AdView mAdView;
    AdRequest adRequest;
    public View view;
    private Handler handler = new Handler(Looper.myLooper());
    // SAMPLE APP CONSTANTS
    private static final String ACTIVITY_NUMBER = "activity_num";
    private static final String LOG_TAG = "iabv3";

    // PRODUCT & SUBSCRIPTION IDS
    private static final String PRODUCT_ID = "com.anjlab.test.iab.s2.p5";
    private static final String SUBSCRIPTION_ID = "com.anjlab.test.iab.subs1";
    private static final String LICENSE_KEY = null; // PUT YOUR MERCHANT KEY HERE;
    // put your Google merchant id here (as stated in public profile of your Payments Merchant Center)
    // if filled library will provide protection against Freedom alike Play Market simulators
    private static final String MERCHANT_ID=null;

    private BillingProcessor bp;
    private boolean readyToPurchase = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        initView();
    }


    DrawerLayout drawer_layout;

    private void initView() {
        if(!BillingProcessor.isIabServiceAvailable(this)) {
            Toast.makeText(this, "In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16", Toast.LENGTH_SHORT).show();
        }

        bp = new BillingProcessor(this, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                Toast.makeText(HomeActivity.this, "onProductPurchased: ", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
                Toast.makeText(HomeActivity.this, "onBillingError: : ", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onBillingInitialized() {
                readyToPurchase = true;
                Toast.makeText(HomeActivity.this, "onBillingInitialized: : ", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onPurchaseHistoryRestored() {
                Toast.makeText(HomeActivity.this, "onPurchaseHistoryRestored: : ", Toast.LENGTH_SHORT).show();

                for(String sku : bp.listOwnedProducts())
                    Log.d(LOG_TAG, "Owned Managed Product: " + sku);
                for(String sku : bp.listOwnedSubscriptions())
                    Log.d(LOG_TAG, "Owned Subscription: " + sku);
            }
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "gradedDb").build();

        circularProgressBar = findViewById(R.id.circularProgressBar);
        navMain = findViewById(R.id.navMain);
        drawer_layout = findViewById(R.id.drawer_layout);
        frame = findViewById(R.id.frame);
        ActionBarDrawerToggle t = new ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(t);
        t.syncState();
        RecyclerView recyclerDrawer = findViewById(R.id.recyclerDrawer);
        txtClasses = findViewById(R.id.txtClasses);
        txtUserName = findViewById(R.id.txtUserName);
        txtGrade = findViewById(R.id.txtGrade);

       /* MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
*/
        MobileAds.initialize(this, "ca-app-pub-3079053216880734~5549087454");

        //   --- Admob ---
        view=getWindow().getDecorView().getRootView();

        Admob.createLoadBanner(getApplicationContext(), view);
        Admob.createLoadInterstitial(getApplicationContext(),null);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        recyclerClasses = findViewById(R.id.recyclerClasses);
        btn_menu = findViewById(R.id.btn_menu);
        String theme = UserPreference.getInstance(HomeActivity.this).getTheme();
        switch (theme) {
            case "Red":
                // setTheme(R.style.red);
                frame.setBackgroundColor(getResources().getColor(R.color.white));
                navMain.setBackgroundColor(getResources().getColor(R.color.darkRed));
                txtClasses.setTextColor(getResources().getColor(R.color.red));
                txtUserName.setTextColor(getResources().getColor(R.color.gray));
                txtGrade.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.gray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }


                break;

            case "darkRed":
                frame.setBackgroundColor(getResources().getColor(R.color.red));
                navMain.setBackgroundColor(getResources().getColor(R.color.darkRed));
                txtClasses.setTextColor(getResources().getColor(R.color.white));
                txtUserName.setTextColor(getResources().getColor(R.color.white));
                txtGrade.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.darkRed)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkRed));
                }
                //setTheme(R.style.darkRed);

                break;


            case "orange":
                frame.setBackgroundColor(getResources().getColor(R.color.white));
                navMain.setBackgroundColor(getResources().getColor(R.color.orange));
                txtClasses.setTextColor(getResources().getColor(R.color.orange));
                txtUserName.setTextColor(getResources().getColor(R.color.gray));
                txtGrade.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.gray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }

                break;

            case "darkOrange":
                frame.setBackgroundColor(getResources().getColor(R.color.orange));
                navMain.setBackgroundColor(getResources().getColor(R.color.darkOrange));
                txtClasses.setTextColor(getResources().getColor(R.color.white));
                txtUserName.setTextColor(getResources().getColor(R.color.white));
                txtGrade.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.darkOrange)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkOrange));
                }
                break;


            case "yellow":
                frame.setBackgroundColor(getResources().getColor(R.color.white));
                navMain.setBackgroundColor(getResources().getColor(R.color.yellow));
                txtClasses.setTextColor(getResources().getColor(R.color.yellow));
                txtUserName.setTextColor(getResources().getColor(R.color.gray));
                txtGrade.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.gray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }

                break;

            case "darkYellow":
                frame.setBackgroundColor(getResources().getColor(R.color.yellow));
                navMain.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                txtClasses.setTextColor(getResources().getColor(R.color.white));
                txtUserName.setTextColor(getResources().getColor(R.color.white));
                txtGrade.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.darkYellow)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkYellow));
                }
                break;

            case "green":
                frame.setBackgroundColor(getResources().getColor(R.color.white));
                navMain.setBackgroundColor(getResources().getColor(R.color.green));
                txtClasses.setTextColor(getResources().getColor(R.color.green));
                txtUserName.setTextColor(getResources().getColor(R.color.gray));
                txtGrade.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.gray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }


                break;


            case "darkGreen":
                frame.setBackgroundColor(getResources().getColor(R.color.green));
                navMain.setBackgroundColor(getResources().getColor(R.color.darkGreen));
                txtClasses.setTextColor(getResources().getColor(R.color.white));
                txtUserName.setTextColor(getResources().getColor(R.color.white));
                txtGrade.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.darkGreen)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkGreen));
                }
                break;

            case "lightGreen":
                frame.setBackgroundColor(getResources().getColor(R.color.white));
                navMain.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                txtClasses.setTextColor(getResources().getColor(R.color.lightGreen));
                txtUserName.setTextColor(getResources().getColor(R.color.gray));
                txtGrade.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.gray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
                break;

            case "newGreen":
                frame.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                navMain.setBackgroundColor(getResources().getColor(R.color.newGreen));
                txtClasses.setTextColor(getResources().getColor(R.color.white));
                txtUserName.setTextColor(getResources().getColor(R.color.white));
                txtGrade.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.newGreen)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.newGreen));
                }
                break;


            case "blue":
                frame.setBackgroundColor(getResources().getColor(R.color.white));
                navMain.setBackgroundColor(getResources().getColor(R.color.blue));
                txtClasses.setTextColor(getResources().getColor(R.color.blue));
                txtUserName.setTextColor(getResources().getColor(R.color.gray));
                txtGrade.setTextColor(getResources().getColor(R.color.gray));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.gray)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
                break;

            case "purple":
                frame.setBackgroundColor(getResources().getColor(R.color.white));
                navMain.setBackgroundColor(getResources().getColor(R.color.purple));
                txtClasses.setTextColor(getResources().getColor(R.color.purple));
                txtUserName.setTextColor(getResources().getColor(R.color.gray));
                txtGrade.setTextColor(getResources().getColor(R.color.gray));

                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.gray)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
                break;

            case "darkPurple":
                frame.setBackgroundColor(getResources().getColor(R.color.purple));
                navMain.setBackgroundColor(getResources().getColor(R.color.darkPurple));
                txtClasses.setTextColor(getResources().getColor(R.color.white));
                txtUserName.setTextColor(getResources().getColor(R.color.white));
                txtGrade.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.darkPurple)
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkPurple));
                }

                break;

            case "darkBlueNew":

            default:
                frame.setBackgroundColor(getResources().getColor(R.color.blue));
                navMain.setBackgroundColor(getResources().getColor(R.color.darkBlueNew));
                txtClasses.setTextColor(getResources().getColor(R.color.white));
                txtUserName.setTextColor(getResources().getColor(R.color.white));
                txtGrade.setTextColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(btn_menu.getDrawable()),
                        ContextCompat.getColor(HomeActivity.this, R.color.darkBlueNew)
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlueNew));
                }

                break;
        }
        recyclerClasses.setLayoutManager(new LinearLayoutManager(this));
        classesAdapter = new ClassesAdapter(HomeActivity.this, this, getClassesList);
        recyclerClasses.setAdapter(classesAdapter);

        android.webkit.CookieSyncManager.createInstance(this);
        android.webkit.CookieManager.getInstance().setAcceptCookie(true);
        WebkitCookieManagerProxy coreCookieManager = new WebkitCookieManagerProxy(null, java.net.CookiePolicy.ACCEPT_ALL);
        java.net.CookieHandler.setDefault(coreCookieManager);


        DrawerItemModel drawerItemMode = new DrawerItemModel();
        drawerItemMode.setIcon(getResources().getDrawable(R.drawable.painter_pallete));
        drawerItemMode.setSecondTexr("");
        drawerItemMode.setTitle("Theme");
        stList.add(drawerItemMode);

        DrawerItemModel drawerItemModel = new DrawerItemModel();
        drawerItemModel.setIcon(getResources().getDrawable(R.drawable.users));
        drawerItemModel.setSecondTexr("");
        drawerItemModel.setTitle("Switch Account");
        stList.add(drawerItemModel);


        DrawerItemModel drawerItemMode4 = new DrawerItemModel();
        drawerItemMode4.setSecondTexr(" ");
        drawerItemMode4.setIcon(getResources().getDrawable(R.drawable.edit_icon));
        drawerItemMode4.setTitle("Edit Classes");
        drawerItemMode4.setSecondTexr("Place Order");
        stList.add(drawerItemMode4);


        DrawerItemModel drawerItemMode7 = new DrawerItemModel();
        drawerItemMode7.setSecondTexr("Change Phone Number");
        drawerItemMode7.setIcon(getResources().getDrawable(R.drawable.ic_hidden));
        drawerItemMode7.setTitle("Help");
        stList.add(drawerItemMode7);


        DrawerItemModel drawerItemMode8 = new DrawerItemModel();
        drawerItemMode8.setSecondTexr("Premium");
        drawerItemMode8.setIcon(getResources().getDrawable(R.drawable.crown));
        drawerItemMode8.setTitle("Premium");
        stList.add(drawerItemMode8);

        DrawerItemModel drawerItemMode9 = new DrawerItemModel();
        drawerItemMode9.setSecondTexr("");
        drawerItemMode9.setIcon(getResources().getDrawable(R.drawable.instagram));
        drawerItemMode9.setTitle("Follow us");
        stList.add(drawerItemMode9);

        DrawerItemModel drawerItemMode10 = new DrawerItemModel();
        drawerItemMode10.setSecondTexr("");
        drawerItemMode10.setIcon(getResources().getDrawable(R.drawable.star));
        drawerItemMode10.setTitle("Send Feedback");
        stList.add(drawerItemMode10);

        DrawerItemModel drawerItemMode11 = new DrawerItemModel();
        drawerItemMode11.setSecondTexr("");
        drawerItemMode11.setTitle("Log Out");
        stList.add(drawerItemMode11);
        DrawerAdapter drawerAdapter = new DrawerAdapter(stList, this, this);
        recyclerDrawer.setLayoutManager(new LinearLayoutManager(this));
        recyclerDrawer.setAdapter(drawerAdapter);
        String BASE_URL = UserPreference.getInstance(HomeActivity.this).getBaseUrl();

        HashMap<List<Custom>,String> hashMap=new HashMap<>();
        List<Custom>list_arr=new ArrayList<>();
        for(int a=0;a<15;a++)
        {
            list_arr.clear();
            Custom custom=new Custom();
            custom.MyKeys="hello";
            list_arr.add(custom);
            hashMap.put(list_arr,a+"");
        }

        MultiMap multiMap = new MultiValueMap();
        // put values into map for A
        multiMap.put("A", "Apple");
        multiMap.put("A", "Aeroplane");
        // put values into map for B
        multiMap.put("B", "Bat");
        multiMap.put("B", "Banana");
        // put values into map for C
        multiMap.put("C", "Cat");
        multiMap.put("C", "Car");
        // retrieve and display values
        System.out.println("Fetching Keys and corresponding [Multiple] Values n");
        // get all the set of keys
        Set<String> keys = multiMap.keySet();
        // iterate through the key set and display key and values
        for (String key : keys) {
            System.out.println("Key = " + key);
            System.out.println("Values = " + multiMap.get(key) + "n");

    }


        new Thread(() -> {
            if (getIntent().getSerializableExtra("getClass") != null) {
                List<ClassModel> classModelList = (ArrayList<ClassModel>) getIntent().getSerializableExtra("getClass");
                assert classModelList != null;
                Log.d("list", "" + classModelList.size());
                for (int i = 0; i < classModelList.size(); i++) {
                    if (classModelList.get(i).isSelectAlpha()) {
                        classModelList.remove(i);
                    }
                }
                getClassesList.addAll(classModelList);
                classesAdapter.notifyDataSetChanged();


            } else {
                StudentsOfCurrentAccountModelDb studentOFCurrentAccountModel = db.saveDataDao().studentOfCurrent();
                List<ClassModel> classModelList = new ArrayList<>();
                if (studentOFCurrentAccountModel != null) {

                    classModelList = db.saveDataDao().getClasses(studentOFCurrentAccountModel.getStudentNumber());

                }
                List<ClassModel> finalClassModelList = classModelList;
                handler.post(() -> {
                    if (studentOFCurrentAccountModel != null) {
                        getClassesList.clear();
                        getClassesList.addAll(finalClassModelList);
                        txtUserName.setText(studentOFCurrentAccountModel.getFirstName() + " " + studentOFCurrentAccountModel.getMiddleName() + " " + studentOFCurrentAccountModel.getLastName());
                        classesAdapter.notifyDataSetChanged();
                    } else {

                        if (BASE_URL != null && !BASE_URL.isEmpty()) {
                            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                                circularProgressBar.setVisibility(View.GONE);
                            }
                            callGetStudent(BASE_URL.trim() + "m/api/MobileWebAPI.asmx/GetStudentDemographicsData");
                        }

                    }
                });


            }


        }).start();


        ImageView btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer_layout.openDrawer(Gravity.LEFT);
            }
        });


        touchListener = new RecyclerTouchListener(this, recyclerClasses);
        touchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        // Toast.makeText(getApplicationContext(), preparationList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                    }
                })
                .setSwipeOptionViews(R.id.llSwipeView)
                .setSwipeable(R.id.rlMainView, R.id.llSwipeView, (viewID, position) -> {
                    switch (viewID) {
                        case R.id.llSwipeView:
                            showDialogForRename(getClassesList.get(position).getClassName(),
                                    getClassesList.get(position).getGradebookNumber(), getClassesList.get(position).getClassespreviousName());


                    }
                });
    }

    private void callGetStudent(String Url) {
        String cook = UserPreference.getInstance(HomeActivity.this).getCookie();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
                 AndroidNetworking.get(Url)
                .setOkHttpClient(okHttpClient)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Cookie", "ASP.NET_SessionId="+cook).build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resss", "" + response);
                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                            circularProgressBar.setVisibility(View.GONE);
                        }

                        String BASE_URL = UserPreference.getInstance(HomeActivity.this).getBaseUrl();
                        if (!response.equals("")) {
                            Gson gson = new Gson();
                            studentOFCurrentAccountModel = gson.fromJson(response, StudentOFCurrentAccountModel.class);
                            new Thread(() -> {
                                StudentsOfCurrentAccountModelDb studentsOfCurrentAccountModelDb = new StudentsOfCurrentAccountModelDb();
                                StudentOFCurrentAccountModel.DBean.ResultsBean resultsBean = studentOFCurrentAccountModel.getD().getResults();
                                studentsOfCurrentAccountModelDb.setAddressVerified(resultsBean.isIsAddressVerified());
                                studentsOfCurrentAccountModelDb.setAge(resultsBean.getAge());
                                studentsOfCurrentAccountModelDb.setBirthdate(resultsBean.getBirthdate());
                                studentsOfCurrentAccountModelDb.setIdNumber(resultsBean.getIdNumber());
                                studentsOfCurrentAccountModelDb.setStudentNumber(resultsBean.getStudentNumber());
                                studentsOfCurrentAccountModelDb.setCorrespondenceLanguage(resultsBean.getCorrespondenceLanguage());
                                studentsOfCurrentAccountModelDb.setDateOfNameAndAddressVerification(resultsBean.getDateOfNameAndAddressVerification());
                                studentsOfCurrentAccountModelDb.setElementarySchool(resultsBean.isIsElementarySchool());
                                studentsOfCurrentAccountModelDb.setEthnicityCode(resultsBean.getEthnicityCode());
                                studentsOfCurrentAccountModelDb.setFatherWorkPhone(resultsBean.getFatherWorkPhone());
                                studentsOfCurrentAccountModelDb.setEthnicityDescription(resultsBean.getEthnicityDescription());
                                studentsOfCurrentAccountModelDb.setAddressVerified(resultsBean.isIsAddressVerified());
                                studentsOfCurrentAccountModelDb.setFatherWorkPhoneExtension(resultsBean.getFatherWorkPhoneExtension());
                                studentsOfCurrentAccountModelDb.setDateOfNameAndAddressVerification(resultsBean.getDateOfNameAndAddressVerification());
                                studentsOfCurrentAccountModelDb.setFirstName(resultsBean.getFirstName());
                                studentsOfCurrentAccountModelDb.setLastName(resultsBean.getLastName());
                                studentsOfCurrentAccountModelDb.setStudentNumber(resultsBean.getStudentNumber());
                                studentsOfCurrentAccountModelDb.setFirstNameAlias(resultsBean.getFirstNameAlias());
                                studentsOfCurrentAccountModelDb.setFluencyLanguage(resultsBean.getFluencyLanguage());
                                studentsOfCurrentAccountModelDb.setGender(resultsBean.getGender());
                                studentsOfCurrentAccountModelDb.setHomeLanguage(resultsBean.getHomeLanguage());
                                studentsOfCurrentAccountModelDb.setLockerID(resultsBean.getLockerID());
                                studentsOfCurrentAccountModelDb.setMailingCity(resultsBean.getMailingCity());
                                studentsOfCurrentAccountModelDb.setMailingAddress(resultsBean.getMailingAddress());
                                studentsOfCurrentAccountModelDb.setFatherWorkPhone(resultsBean.getFatherWorkPhone());
                                studentsOfCurrentAccountModelDb.setMailingZipCode(resultsBean.getMailingZipCode());
                                studentsOfCurrentAccountModelDb.setMotherWorkPhoneExtension(resultsBean.getMotherWorkPhoneExtension());
                                studentsOfCurrentAccountModelDb.setFirstNameAlias(resultsBean.getFirstNameAlias());
                                studentsOfCurrentAccountModelDb.setAge(resultsBean.getAge());
                                studentsOfCurrentAccountModelDb.setMiddleName(resultsBean.getMiddleName());
                                studentsOfCurrentAccountModelDb.setPhoto(resultsBean.getPhoto());
                                studentsOfCurrentAccountModelDb.setNameOfFather(resultsBean.getNameOfFather());
                                studentsOfCurrentAccountModelDb.setNameOfMother(resultsBean.getNameOfMother());
                                studentsOfCurrentAccountModelDb.setUserCode1(resultsBean.getUserCode1());
                                studentsOfCurrentAccountModelDb.setUserCode1Description(resultsBean.getUserCode1Description());
                                studentsOfCurrentAccountModelDb.setUserCode2(resultsBean.getUserCode2());
                                studentsOfCurrentAccountModelDb.setUserCode1Title(resultsBean.getUserCode1Title());
                                studentsOfCurrentAccountModelDb.setUserCode1Description(resultsBean.getUserCode1Description());
                                studentsOfCurrentAccountModelDb.setUserCode2(resultsBean.getUserCode2());
                                studentsOfCurrentAccountModelDb.setUserCode3(resultsBean.getUserCode3());
                                studentsOfCurrentAccountModelDb.setUserCode4(resultsBean.getUserCode4());
                                studentsOfCurrentAccountModelDb.setUserCode5(resultsBean.getUserCode5());
                                studentsOfCurrentAccountModelDb.setUserCode6(resultsBean.getUserCode6());
                                db.saveDataDao().insertStudent(studentsOfCurrentAccountModelDb);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (BASE_URL != null && !BASE_URL.isEmpty()) {
                                            //new RequestTask().execute("https://my.iusd.org/m/api/MobileWebAPI.asmx/GetGradebookSummaryData");

                                            getClassList(BASE_URL.trim()+"m/api/MobileWebAPI.asmx/GetGradebookSummaryData");
                                        }
                                    }
                                });
                            }).start();


                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                            circularProgressBar.setVisibility(View.GONE);
                        }
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


    private void openThemeDialog() {
        final Dialog dialog = new Dialog(this, R.style.PopupDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.theme_dialog);
        RecyclerView recyclerColor = dialog.findViewById(R.id.recyclerColor);
        Window window = dialog.getWindow();
        assert window != null;
        //int newcolor;
        WindowManager.LayoutParams wlp = window.getAttributes();
        final List<ThemeModel> themeModels = new ArrayList<>();

        //String colorHex;
        ThemeModel themeModel = new ThemeModel();
        themeModel.setThemeColor(R.color.red);
        themeModel.setThemeName("Red");
        int newcolor1 = getResources().getColor(R.color.red);
        String colorHexn1 = "#" + Integer.toHexString(newcolor1);
        themeModel.setColorString(colorHexn1);
        themeModels.add(themeModel);

        ThemeModel themeModel1 = new ThemeModel();
        themeModel1.setThemeColor(R.color.darkRed);
        themeModel1.setThemeName("darkRed");
        int newcolor2 = getResources().getColor(R.color.darkRed);
        String colorHex2 = "#" + Integer.toHexString(newcolor2);
        themeModel1.setColorString(colorHex2);


        themeModels.add(themeModel1);

        ThemeModel themeModel2 = new ThemeModel();
        themeModel2.setThemeColor(R.color.orange);
        themeModel2.setThemeName("orange");
        int newcolor3 = getResources().getColor(R.color.orange);
        String colorHex3 = "#" + Integer.toHexString(newcolor3);
        themeModel2.setColorString(colorHex3);
        themeModels.add(themeModel2);

        ThemeModel themeModel3 = new ThemeModel();
        themeModel3.setThemeColor(R.color.darkOrange);
        themeModel3.setThemeName("darkOrange");
        int newcolor4 = getResources().getColor(R.color.darkOrange);
        String colorHex4 = "#" + Integer.toHexString(newcolor4);
        themeModel3.setColorString(colorHex4);

        themeModels.add(themeModel3);

        ThemeModel themeModel4 = new ThemeModel();
        themeModel4.setThemeColor(R.color.yellow);
        themeModel4.setThemeName("yellow");
        int newcolor5 = getResources().getColor(R.color.yellow);
        String colorHex5 = "#" + Integer.toHexString(newcolor5);
        themeModel4.setColorString(colorHex5);

        themeModels.add(themeModel4);

        ThemeModel themeModel5 = new ThemeModel();
        themeModel5.setThemeColor(R.color.darkYellow);
        themeModel5.setThemeName("darkYellow");
        int newcolor6 = getResources().getColor(R.color.darkYellow);
        String colorHex6 = "#" + Integer.toHexString(newcolor6);
        themeModel5.setColorString(colorHex6);


        themeModels.add(themeModel5);


        ThemeModel themeModel6 = new ThemeModel();
        themeModel6.setThemeColor(R.color.green);
        themeModel6.setThemeName("green");
        int newcolor7 = getResources().getColor(R.color.green);
        String colorHex7 = "#" + Integer.toHexString(newcolor7);
        themeModel6.setColorString(colorHex7);


        themeModels.add(themeModel6);


        ThemeModel themeModel7 = new ThemeModel();
        themeModel7.setThemeColor(R.color.darkGreen);
        themeModel7.setThemeName("darkGreen");
        int newcolor8 = getResources().getColor(R.color.darkGreen);
        String colorHex8 = "#" + Integer.toHexString(newcolor8);
        themeModel7.setColorString(colorHex8);


        themeModels.add(themeModel7);

        ThemeModel themeModel8 = new ThemeModel();
        themeModel8.setThemeColor(R.color.lightGreen);
        themeModel8.setThemeName("lightGreen");
        int newcolor9 = getResources().getColor(R.color.lightGreen);
        String colorHex9 = "#" + Integer.toHexString(newcolor9);
        themeModel8.setColorString(colorHex9);
        themeModels.add(themeModel8);


        ThemeModel themeModel9 = new ThemeModel();
        themeModel9.setThemeColor(R.color.newGreen);
        themeModel9.setThemeName("newGreen");
        int newcolor10 = getResources().getColor(R.color.newGreen);
        String colorHex10 = "#" + Integer.toHexString(newcolor10);
        themeModel9.setColorString(colorHex10);
        themeModels.add(themeModel9);

        ThemeModel themeModel10 = new ThemeModel();
        themeModel10.setThemeColor(R.color.blue);
        themeModel10.setThemeName("blue");
        int newcolor11 = getResources().getColor(R.color.blue);
        String colorHex11 = "#" + Integer.toHexString(newcolor11);
        themeModel10.setColorString(colorHex11);
        themeModels.add(themeModel10);


        ThemeModel themeModel11 = new ThemeModel();
        themeModel11.setThemeColor(R.color.darkBlueNew);
        themeModel11.setThemeName("darkBlueNew");
        int newcolor12 = getResources().getColor(R.color.darkBlueNew);
        String colorHex12 = "#" + Integer.toHexString(newcolor12);
        themeModel11.setColorString(colorHex12);
        themeModels.add(themeModel11);

        ThemeModel themeModel12 = new ThemeModel();
        themeModel12.setThemeColor(R.color.purple);
        themeModel12.setThemeName("purple");
        int newcolor13 = getResources().getColor(R.color.purple);
        String colorHex13 = "#" + Integer.toHexString(newcolor13);
        themeModel12.setColorString(colorHex13);
        themeModels.add(themeModel12);


        ThemeModel themeModel13 = new ThemeModel();
        themeModel13.setThemeColor(R.color.darkPurple);
        themeModel13.setThemeName("darkPurple");
        int newcolor14 = getResources().getColor(R.color.darkPurple);
        String colorHex14 = "#" + Integer.toHexString(newcolor14);
        themeModel13.setColorString(colorHex14);
        themeModels.add(themeModel13);


        recyclerColor.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));
        recyclerColor.setAdapter(new ThemeAdapter(themeModels, HomeActivity.this, new ThemeAdapter.OnClickThemeListener() {
            @Override
            public void onClickPos(int position) {
                dialog.dismiss();
                UserPreference.getInstance(HomeActivity.this).setTheme(themeModels.get(position).getThemeName());
                switch (themeModels.get(position).getThemeName()) {
                    case "Red":
                        // setTheme(R.style.red);
                        frame.setBackgroundColor(getResources().getColor(R.color.white));
                        navMain.setBackgroundColor(getResources().getColor(R.color.darkRed));
                        txtClasses.setTextColor(getResources().getColor(R.color.red));
                        txtUserName.setTextColor(getResources().getColor(R.color.gray));
                        txtGrade.setTextColor(getResources().getColor(R.color.gray));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.gray)
                        );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                        }


                        break;

                    case "darkRed":
                        frame.setBackgroundColor(getResources().getColor(R.color.red));
                        navMain.setBackgroundColor(getResources().getColor(R.color.darkRed));
                        txtClasses.setTextColor(getResources().getColor(R.color.white));
                        txtUserName.setTextColor(getResources().getColor(R.color.white));
                        txtGrade.setTextColor(getResources().getColor(R.color.white));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.darkRed)
                        );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.darkRed));
                        }
                        //setTheme(R.style.darkRed);

                        break;


                    case "orange":
                        frame.setBackgroundColor(getResources().getColor(R.color.white));
                        navMain.setBackgroundColor(getResources().getColor(R.color.orange));
                        txtClasses.setTextColor(getResources().getColor(R.color.orange));
                        txtUserName.setTextColor(getResources().getColor(R.color.gray));
                        txtGrade.setTextColor(getResources().getColor(R.color.gray));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.gray)
                        );

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                        }

                        break;

                    case "darkOrange":
                        frame.setBackgroundColor(getResources().getColor(R.color.orange));
                        navMain.setBackgroundColor(getResources().getColor(R.color.darkOrange));
                        txtClasses.setTextColor(getResources().getColor(R.color.white));
                        txtUserName.setTextColor(getResources().getColor(R.color.white));
                        txtGrade.setTextColor(getResources().getColor(R.color.white));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.darkOrange)
                        );

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.darkOrange));
                        }
                        break;


                    case "yellow":
                        frame.setBackgroundColor(getResources().getColor(R.color.white));
                        navMain.setBackgroundColor(getResources().getColor(R.color.yellow));
                        txtClasses.setTextColor(getResources().getColor(R.color.yellow));
                        txtUserName.setTextColor(getResources().getColor(R.color.gray));
                        txtGrade.setTextColor(getResources().getColor(R.color.gray));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.gray)
                        );

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                        }

                        break;

                    case "darkYellow":
                        frame.setBackgroundColor(getResources().getColor(R.color.yellow));
                        navMain.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                        txtClasses.setTextColor(getResources().getColor(R.color.white));
                        txtUserName.setTextColor(getResources().getColor(R.color.white));
                        txtGrade.setTextColor(getResources().getColor(R.color.white));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.darkYellow)
                        );

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.darkYellow));
                        }
                        break;

                    case "green":
                        frame.setBackgroundColor(getResources().getColor(R.color.white));
                        navMain.setBackgroundColor(getResources().getColor(R.color.green));
                        txtClasses.setTextColor(getResources().getColor(R.color.green));
                        txtUserName.setTextColor(getResources().getColor(R.color.gray));
                        txtGrade.setTextColor(getResources().getColor(R.color.gray));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.gray)
                        );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                        }


                        break;


                    case "darkGreen":
                        frame.setBackgroundColor(getResources().getColor(R.color.green));
                        navMain.setBackgroundColor(getResources().getColor(R.color.darkGreen));
                        txtClasses.setTextColor(getResources().getColor(R.color.white));
                        txtUserName.setTextColor(getResources().getColor(R.color.white));
                        txtGrade.setTextColor(getResources().getColor(R.color.white));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.darkGreen)
                        );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.darkGreen));
                        }
                        break;

                    case "lightGreen":
                        frame.setBackgroundColor(getResources().getColor(R.color.white));
                        navMain.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        txtClasses.setTextColor(getResources().getColor(R.color.lightGreen));
                        txtUserName.setTextColor(getResources().getColor(R.color.gray));
                        txtGrade.setTextColor(getResources().getColor(R.color.gray));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.gray)
                        );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                        }
                        break;

                    case "newGreen":
                        frame.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        navMain.setBackgroundColor(getResources().getColor(R.color.newGreen));
                        txtClasses.setTextColor(getResources().getColor(R.color.white));
                        txtUserName.setTextColor(getResources().getColor(R.color.white));
                        txtGrade.setTextColor(getResources().getColor(R.color.white));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.newGreen)
                        );

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.newGreen));
                        }
                        break;


                    case "blue":
                        frame.setBackgroundColor(getResources().getColor(R.color.white));
                        navMain.setBackgroundColor(getResources().getColor(R.color.blue));
                        txtClasses.setTextColor(getResources().getColor(R.color.blue));
                        txtUserName.setTextColor(getResources().getColor(R.color.gray));
                        txtGrade.setTextColor(getResources().getColor(R.color.gray));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.gray)
                        );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                        }
                        break;

                    case "darkBlueNew":
                        frame.setBackgroundColor(getResources().getColor(R.color.blue));
                        navMain.setBackgroundColor(getResources().getColor(R.color.darkBlueNew));
                        txtClasses.setTextColor(getResources().getColor(R.color.white));
                        txtUserName.setTextColor(getResources().getColor(R.color.white));
                        txtGrade.setTextColor(getResources().getColor(R.color.white));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.darkBlueNew)
                        );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlueNew));
                        }

                        break;

                    case "purple":
                        frame.setBackgroundColor(getResources().getColor(R.color.white));
                        navMain.setBackgroundColor(getResources().getColor(R.color.purple));
                        txtClasses.setTextColor(getResources().getColor(R.color.purple));
                        txtUserName.setTextColor(getResources().getColor(R.color.gray));
                        txtGrade.setTextColor(getResources().getColor(R.color.gray));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                        }
                        break;

                    case "darkPurple":
                        frame.setBackgroundColor(getResources().getColor(R.color.purple));
                        navMain.setBackgroundColor(getResources().getColor(R.color.darkPurple));
                        txtClasses.setTextColor(getResources().getColor(R.color.white));
                        txtUserName.setTextColor(getResources().getColor(R.color.white));
                        txtGrade.setTextColor(getResources().getColor(R.color.white));
                        DrawableCompat.setTint(
                                DrawableCompat.wrap(btn_menu.getDrawable()),
                                ContextCompat.getColor(HomeActivity.this, R.color.darkPurple)
                        );

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.darkPurple));
                        }

                        break;
                }

                classesAdapter.notifyDataSetChanged();
                //recreate();

            }
        }));

        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

    }

    @Override
    public void onItemViewClicked(int position) {
        switch (stList.get(position).getTitle()) {
            case "Theme":
                drawer_layout.closeDrawers();
                openThemeDialog();
                break;
            case "Log Out":
                UserPreference.getInstance(this).clearSession();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            case "Edit Classes":
                drawer_layout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, EditClassesActivity.class));
                break;

            case "Switch Account":
                drawer_layout.closeDrawers();
                startActivity(new Intent(HomeActivity.this, SwitchStudentActivity.class));
                break;

            case "Help":
                drawer_layout.closeDrawers();
                openHelpDialog();
                break;

            case "Premium":
                drawer_layout.closeDrawers();
                openPremiumDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void openPremiumDialog() {
        final Dialog dialog = new Dialog(this, R.style.PopupDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.premium_dialog_layout);
        Button btnPurchaseNow=dialog.findViewById(R.id.btnPurchaseNow);
        btnPurchaseNow.setOnClickListener(view -> {
            if (!readyToPurchase) {
                Toast.makeText(this, "Billing not initialized.", Toast.LENGTH_SHORT).show();
                return;
            }
            bp.purchase(this,PRODUCT_ID);

        });
        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        ImageView ivCloseDialog = dialog.findViewById(R.id.ivCloseDialog);
        ivCloseDialog.setOnClickListener(view -> dialog.dismiss());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    private void openHelpDialog() {
        final Dialog dialog = new Dialog(this, R.style.PopupDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.help_dialog_layout);
        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        ImageView ivCloseDialog = dialog.findViewById(R.id.ivCloseDialog);
        ivCloseDialog.setOnClickListener(view -> dialog.dismiss());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }


    @Override
    public void OnClickPosition(int position) {
        Intent intent = new Intent(HomeActivity.this, DetailsofClass.class);
        intent.putExtra("gradebookNumber", getClassesList.get(position).getGradebookNumber());
        intent.putExtra("classname", getClassesList.get(position).getClassName());
        intent.putExtra("term", getClassesList.get(position).getTerm());
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        recyclerClasses.addOnItemTouchListener(touchListener);
        if (mAdView != null) {
            mAdView.resume();
        }
    }


    private void showDialogForRename(String className, int gradebookNumber, String classespreviousName) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.rename_dialog_layout);
        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        dialog.show();
        TextView txtRename = dialog.findViewById(R.id.txtRename);
        TextView txtEnterName = dialog.findViewById(R.id.txtEnterName);
        EditText edtClassName = dialog.findViewById(R.id.edtClassName);
        Button btnReset = dialog.findViewById(R.id.btnReset);
        Button btnRename = dialog.findViewById(R.id.btnRename);
        Button btnClose = dialog.findViewById(R.id.btnClose);
        String theme = UserPreference.getInstance(HomeActivity.this).getTheme();
        edtClassName.setText(className);
        btnClose.setOnClickListener(view -> dialog.dismiss());
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(() -> {
                    db.saveDataDao().update(classespreviousName, gradebookNumber);
                    StudentsOfCurrentAccountModelDb studentOFCurrentAccountModel = db.saveDataDao().studentOfCurrent();
                    List<ClassModel> classModelList = new ArrayList<>();
                    if (studentOFCurrentAccountModel != null) {
                        classModelList = db.saveDataDao().getClasses(studentOFCurrentAccountModel.getStudentNumber());

                    }
                    List<ClassModel> finalClassModelList = classModelList;
                    handler.post(() -> {
                        dialog.dismiss();
                        if (studentOFCurrentAccountModel != null) {
                            getClassesList.clear();
                            getClassesList.addAll(finalClassModelList);
                            classesAdapter.notifyDataSetChanged();
                        }
                    });
                }).start();
            }
        });
        btnRename.setOnClickListener(view -> {
            new Thread(() -> {
                db.saveDataDao().update(edtClassName.getText().toString(), gradebookNumber);
                StudentsOfCurrentAccountModelDb studentOFCurrentAccountModel = db.saveDataDao().studentOfCurrent();
                List<ClassModel> classModelList = new ArrayList<>();
                if (studentOFCurrentAccountModel != null) {
                    classModelList = db.saveDataDao().getClasses(studentOFCurrentAccountModel.getStudentNumber());

                }
                List<ClassModel> finalClassModelList = classModelList;
                handler.post(() -> {
                    dialog.dismiss();
                    if (studentOFCurrentAccountModel != null) {
                        getClassesList.clear();
                        getClassesList.addAll(finalClassModelList);
                        classesAdapter.notifyDataSetChanged();
                    }
                });


            }).start();
        });

        switch (theme) {
            case "Red":
                txtRename.setTextColor(getResources().getColor(R.color.red));
                txtEnterName.setTextColor(getResources().getColor(R.color.red));
                edtClassName.setTextColor(getResources().getColor(R.color.red));
                edtClassName.setHintTextColor(getResources().getColor(R.color.red));
                btnReset.setBackgroundResource(R.drawable.shape_red);
                edtClassName.setBackgroundResource(R.drawable.edt_red);
                btnRename.setBackgroundResource(R.drawable.shape_red);
                btnClose.setBackgroundResource(R.drawable.shape_red);


                break;

            case "darkRed":
                txtRename.setTextColor(getResources().getColor(R.color.darkRed));
                txtEnterName.setTextColor(getResources().getColor(R.color.darkRed));
                edtClassName.setTextColor(getResources().getColor(R.color.darkRed));
                edtClassName.setHintTextColor(getResources().getColor(R.color.darkRed));
                btnReset.setBackgroundResource(R.drawable.shape_darkred);
                btnRename.setBackgroundResource(R.drawable.shape_darkred);
                btnClose.setBackgroundResource(R.drawable.shape_darkred);
                edtClassName.setBackgroundResource(R.drawable.edt_dark_red);

                //setTheme(R.style.darkRed);

                break;


            case "orange":

                txtRename.setTextColor(getResources().getColor(R.color.orange));
                txtEnterName.setTextColor(getResources().getColor(R.color.orange));
                edtClassName.setTextColor(getResources().getColor(R.color.orange));
                edtClassName.setHintTextColor(getResources().getColor(R.color.orange));
                btnReset.setBackgroundResource(R.drawable.shape_orange);
                btnRename.setBackgroundResource(R.drawable.shape_orange);
                btnClose.setBackgroundResource(R.drawable.shape_orange);
                edtClassName.setBackgroundResource(R.drawable.edt_orange);

                break;

            case "darkOrange":
                txtRename.setTextColor(getResources().getColor(R.color.darkOrange));
                txtEnterName.setTextColor(getResources().getColor(R.color.darkOrange));
                edtClassName.setTextColor(getResources().getColor(R.color.darkOrange));
                edtClassName.setHintTextColor(getResources().getColor(R.color.darkOrange));
                btnReset.setBackgroundResource(R.drawable.shape_dark_orange);
                btnRename.setBackgroundResource(R.drawable.shape_dark_orange);
                btnClose.setBackgroundResource(R.drawable.shape_dark_orange);
                edtClassName.setBackgroundResource(R.drawable.edt_dark_orange);
                break;


            case "yellow":
                txtRename.setTextColor(getResources().getColor(R.color.yellow));
                txtEnterName.setTextColor(getResources().getColor(R.color.yellow));
                edtClassName.setTextColor(getResources().getColor(R.color.yellow));
                edtClassName.setHintTextColor(getResources().getColor(R.color.yellow));
                btnReset.setBackgroundResource(R.drawable.shape_yellow);
                btnRename.setBackgroundResource(R.drawable.shape_yellow);
                btnClose.setBackgroundResource(R.drawable.shape_yellow);
                edtClassName.setBackgroundResource(R.drawable.edt_yellow);


                break;

            case "darkYellow":
                txtRename.setTextColor(getResources().getColor(R.color.darkYellow));
                txtEnterName.setTextColor(getResources().getColor(R.color.darkYellow));
                edtClassName.setTextColor(getResources().getColor(R.color.darkYellow));
                edtClassName.setHintTextColor(getResources().getColor(R.color.darkYellow));
                btnReset.setBackgroundResource(R.drawable.shape_dark_yellow);
                btnRename.setBackgroundResource(R.drawable.shape_dark_yellow);
                btnClose.setBackgroundResource(R.drawable.shape_dark_yellow);
                edtClassName.setBackgroundResource(R.drawable.edt_dark_yellow);

                break;

            case "green":
                txtRename.setTextColor(getResources().getColor(R.color.green));
                txtEnterName.setTextColor(getResources().getColor(R.color.green));
                edtClassName.setTextColor(getResources().getColor(R.color.green));
                edtClassName.setHintTextColor(getResources().getColor(R.color.green));
                btnReset.setBackgroundResource(R.drawable.shape_green);
                btnRename.setBackgroundResource(R.drawable.shape_green);
                btnClose.setBackgroundResource(R.drawable.shape_green);
                edtClassName.setBackgroundResource(R.drawable.edt_green);


                break;


            case "darkGreen":
                txtRename.setTextColor(getResources().getColor(R.color.darkGreen));
                txtEnterName.setTextColor(getResources().getColor(R.color.darkGreen));
                edtClassName.setTextColor(getResources().getColor(R.color.darkGreen));
                edtClassName.setHintTextColor(getResources().getColor(R.color.darkGreen));
                btnReset.setBackgroundResource(R.drawable.shape_dark_green);
                btnRename.setBackgroundResource(R.drawable.shape_dark_green);
                btnClose.setBackgroundResource(R.drawable.shape_dark_green);
                edtClassName.setBackgroundResource(R.drawable.edt_dark_green);
                break;

            case "lightGreen":
                txtRename.setTextColor(getResources().getColor(R.color.lightGreen));
                txtEnterName.setTextColor(getResources().getColor(R.color.lightGreen));
                edtClassName.setTextColor(getResources().getColor(R.color.lightGreen));
                edtClassName.setHintTextColor(getResources().getColor(R.color.lightGreen));
                btnReset.setBackgroundResource(R.drawable.shape_light_green);
                btnRename.setBackgroundResource(R.drawable.shape_light_green);
                btnClose.setBackgroundResource(R.drawable.shape_light_green);
                edtClassName.setBackgroundResource(R.drawable.edtt_light_green);
                break;

            case "newGreen":
                txtRename.setTextColor(getResources().getColor(R.color.newGreen));
                txtEnterName.setTextColor(getResources().getColor(R.color.newGreen));
                edtClassName.setTextColor(getResources().getColor(R.color.newGreen));
                edtClassName.setHintTextColor(getResources().getColor(R.color.newGreen));
                btnReset.setBackgroundResource(R.drawable.shape_new_green);
                btnRename.setBackgroundResource(R.drawable.shape_new_green);
                btnClose.setBackgroundResource(R.drawable.shape_new_green);
                edtClassName.setBackgroundResource(R.drawable.edt_newgreen);
                break;


            case "purple":
                txtRename.setTextColor(getResources().getColor(R.color.purple));
                txtEnterName.setTextColor(getResources().getColor(R.color.purple));
                edtClassName.setTextColor(getResources().getColor(R.color.purple));
                edtClassName.setHintTextColor(getResources().getColor(R.color.purple));
                btnReset.setBackgroundResource(R.drawable.shape_purple);
                btnRename.setBackgroundResource(R.drawable.shape_purple);
                btnClose.setBackgroundResource(R.drawable.shape_purple);
                edtClassName.setBackgroundResource(R.drawable.edt_purple);
                break;

            case "darkPurple":
                txtRename.setTextColor(getResources().getColor(R.color.darkPurple));
                txtEnterName.setTextColor(getResources().getColor(R.color.darkPurple));
                edtClassName.setTextColor(getResources().getColor(R.color.darkPurple));
                edtClassName.setHintTextColor(getResources().getColor(R.color.darkPurple));
                btnReset.setBackgroundResource(R.drawable.shape_dark_purple);
                btnRename.setBackgroundResource(R.drawable.shape_dark_purple);
                btnClose.setBackgroundResource(R.drawable.shape_dark_purple);
                edtClassName.setBackgroundResource(R.drawable.edt_dark_purple);
                break;


            case "blue":
                txtRename.setTextColor(getResources().getColor(R.color.blue));
                txtEnterName.setTextColor(getResources().getColor(R.color.blue));
                edtClassName.setTextColor(getResources().getColor(R.color.blue));
                edtClassName.setHintTextColor(getResources().getColor(R.color.blue));
                btnReset.setBackgroundResource(R.drawable.shape_blue);
                btnRename.setBackgroundResource(R.drawable.shape_blue);
                btnClose.setBackgroundResource(R.drawable.shape_blue);
                edtClassName.setBackgroundResource(R.drawable.edt_back);
                break;

            case "darkBlueNew":
            default:

                txtRename.setTextColor(getResources().getColor(R.color.darkBlueNew));
                txtEnterName.setTextColor(getResources().getColor(R.color.darkBlueNew));
                edtClassName.setTextColor(getResources().getColor(R.color.darkBlueNew));
                edtClassName.setHintTextColor(getResources().getColor(R.color.darkBlueNew));
                btnReset.setBackgroundResource(R.drawable.shape_dark_blue_new);
                btnRename.setBackgroundResource(R.drawable.shape_dark_blue_new);
                btnClose.setBackgroundResource(R.drawable.shape_dark_blue_new);
                edtClassName.setBackgroundResource(R.drawable.edt_dark_blue_new);


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    /*Call Api for get class*/

    StudentOFCurrentAccountModel studentOFCurrentAccountModel;


    private void getClassList(String Url) {
        String cookies= UserPreference.getInstance(HomeActivity.this).getCookie();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // .cookieJar(cookieJar)
                .followRedirects(false)
                .followSslRedirects(false)

                .build();
        AndroidNetworking.get(Url)
                .setOkHttpClient(okHttpClient)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Cookie","ASP.NET_SessionId="+cookies)

                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resss", "" + response);
                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                            circularProgressBar.setVisibility(View.GONE);
                        }
                        Log.i("dataOnPost", "data" + response);
                        classModelList.clear();
                        if (!response.equals("")) {
                            Gson gson = new Gson();
                            Classes classes = gson.fromJson(response, Classes.class);
                            List<Classes.DBean.ResultsBean> resultsBeans = classes.getD().getResults();
                            new Thread(() -> {
                                for (int i = 0; i < resultsBeans.size(); i++) {
                                    Classes.DBean.ResultsBean resultsBean = resultsBeans.get(i);
                                    ClassModel classModel = new ClassModel();
                                    classModel.setClassName(resultsBean.getClassName());
                                    classModel.setCode(resultsBean.getCode());
                                    classModel.setComment(resultsBean.getComment());
                                    classModel.setDoingRubric(resultsBean.isDoingRubric());
                                    classModel.setGradebookNumber(resultsBean.getGradebookNumber());
                                    classModel.setGradebookNumberTerm(resultsBean.getGradebookNumberTerm());
                                    classModel.setMark(resultsBean.getMark());
                                    classModel.setPercentGrade(resultsBean.getPercentGrade());
                                    classModel.setHideOverallScore(resultsBean.isHideOverallScore());
                                    classModel.setMissingAssignments(resultsBean.getMissingAssignments());
                                    classModel.setShowFinalMark(resultsBean.isShowFinalMark());
                                    classModel.setUsingCheckMarks(resultsBean.isIsUsingCheckMarks());
                                    classModel.setHideOverallScore(resultsBean.isHideOverallScore());
                                    classModel.setTerm(resultsBean.getTerm());
                                    classModel.setClassespreviousName(resultsBean.getClassName());
                                    classModel.setStudentNumber(studentOFCurrentAccountModel.getD().getResults().getStudentNumber());
                                    classModel.setPeriod(resultsBean.getPeriod());
                                    classModel.setUpdated(resultsBean.getUpdated());
                                    classModel.setCode(resultsBean.getCode());
                                    db.saveDataDao().insertClass(classModel);
                                    List<ClassModel> classModelList = db.saveDataDao().getClasses(studentOFCurrentAccountModel.getD().getResults().getStudentNumber());
                                    Log.d("listtttttt", "" + classModelList.size());
                                    handler.post(() -> {
                                        getClassesList.clear();
                                        getClassesList.addAll(classModelList);
                                        classesAdapter.notifyDataSetChanged();
                                    });

                                }

                            }).start();


                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                            circularProgressBar.setVisibility(View.GONE);
                        }
                        Log.d("Error", "" + anError.getResponse());
                        if (anError.getErrorCode() != 0) {
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            Toast.makeText(HomeActivity.this, "Session Expire Login Again", Toast.LENGTH_SHORT).show();
                            UserPreference.getInstance(HomeActivity.this).clearSession();
                            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                            finish();
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                    }
                });

    }



    class RequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
                circularProgressBar.setVisibility(View.VISIBLE);
            }
        }

        String line = "";

        @Override
        protected String doInBackground(String... uri) {

            try {

                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(uri[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    String cookie = UserPreference.getInstance(HomeActivity.this).getCookie();
                    urlConnection.setRequestProperty("Cookie", "ASP.NET_SessionId=uyq5vd4guki5rjkhaeqhzwlb");
                    urlConnection.setRequestProperty("Content-type", "application/json");
                    Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                    Set<String> headerFieldsSet = headerFields.keySet();
                    Log.e("=====header", headerFieldsSet.size() + "");
                    int code = urlConnection.getResponseCode();
                    if (code != 200) {
                        throw new IOException("Invalid response from server: " + code);
                    }

                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));
                    line = rd.readLine();
                    Log.i("data", line);


                  /*  while ((line = rd.readLine()) != null) {
                        Log.i("data", line);

                    }*/
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

        List<ClassModel> classModelList = new ArrayList<>();

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (circularProgressBar != null && circularProgressBar.getVisibility() == View.VISIBLE) {
                circularProgressBar.setVisibility(View.GONE);
            }
            Log.i("dataOnPost", "data" + s);
            classModelList.clear();
            if (!s.equals("")) {
                Gson gson = new Gson();
                Classes classes = gson.fromJson(s, Classes.class);
                List<Classes.DBean.ResultsBean> resultsBeans = classes.getD().getResults();
                new Thread(() -> {
                    for (int i = 0; i < resultsBeans.size(); i++) {
                        Classes.DBean.ResultsBean resultsBean = resultsBeans.get(i);
                        ClassModel classModel = new ClassModel();
                        classModel.setClassName(resultsBean.getClassName());
                        classModel.setCode(resultsBean.getCode());
                        classModel.setComment(resultsBean.getComment());
                        classModel.setDoingRubric(resultsBean.isDoingRubric());
                        classModel.setGradebookNumber(resultsBean.getGradebookNumber());
                        classModel.setGradebookNumberTerm(resultsBean.getGradebookNumberTerm());
                        classModel.setMark(resultsBean.getMark());
                        classModel.setPercentGrade(resultsBean.getPercentGrade());
                        classModel.setHideOverallScore(resultsBean.isHideOverallScore());
                        classModel.setMissingAssignments(resultsBean.getMissingAssignments());
                        classModel.setShowFinalMark(resultsBean.isShowFinalMark());
                        classModel.setUsingCheckMarks(resultsBean.isIsUsingCheckMarks());
                        classModel.setHideOverallScore(resultsBean.isHideOverallScore());
                        classModel.setTerm(resultsBean.getTerm());
                        classModel.setClassespreviousName(resultsBean.getClassName());
                        classModel.setStudentNumber(studentOFCurrentAccountModel.getD().getResults().getStudentNumber());
                        classModel.setPeriod(resultsBean.getPeriod());
                        classModel.setUpdated(resultsBean.getUpdated());
                        classModel.setCode(resultsBean.getCode());
                        db.saveDataDao().insertClass(classModel);
                        List<ClassModel> classModelList = db.saveDataDao().getClasses(studentOFCurrentAccountModel.getD().getResults().getStudentNumber());
                        Log.d("listtttttt", "" + classModelList.size());
                        handler.post(() -> {
                            getClassesList.clear();
                            getClassesList.addAll(classModelList);
                            classesAdapter.notifyDataSetChanged();
                        });

                    }

                }).start();


            }
        }
    }

    List<ClassModel> classModelList = new ArrayList<>();

    class Custom{
       public String MyKeys;
    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }



    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();

        if (bp != null)
            bp.release();
        super.onDestroy();
    }

}


