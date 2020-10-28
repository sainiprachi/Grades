package com.grades;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.app.grades.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.grades.model.ClassesDetailModel;
import com.grades.model.GradeBookDetailSummary;
import com.grades.utils.UserPreference;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import okhttp3.OkHttpClient;

public class TrendsActivity extends AppCompatActivity {
    private static final Random RANDOM = new Random();
    private static final String TAG = "TrendsActivity";
    private LineGraphSeries<DataPoint> series;
    ProgressBar circularProgressBar;
    int gradebookNumber;
    String term;
    String classname;
    List<ClassesDetailModel.DBean.ResultsBean> resultsBeans = new ArrayList<>();
    List<ClassesDetailModel.DBean.ResultsBean> assignmentList;

    List<Double>linePlotData=new ArrayList<>();
    List<Double>dotPlotData=new ArrayList<>();




    private int lastX = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
        initView();



    }

    private List<Entry> getIncomeEntries() {
        ArrayList<Entry> incomeEntries = new ArrayList<>();

        incomeEntries.add(new Entry(1, 11300));
        incomeEntries.add(new Entry(2, 1390));
        incomeEntries.add(new Entry(3, 1190));
        incomeEntries.add(new Entry(4, 7200));
        incomeEntries.add(new Entry(5, 4790));
        incomeEntries.add(new Entry(6, 4500));
        incomeEntries.add(new Entry(7, 8000));
        incomeEntries.add(new Entry(8, 7034));
        incomeEntries.add(new Entry(9, 4307));
        incomeEntries.add(new Entry(10, 8762));
        incomeEntries.add(new Entry(11, 4355));
        incomeEntries.add(new Entry(12, 6000));
        return incomeEntries.subList(0, 12);
    }


    private void initView(){
        circularProgressBar=findViewById(R.id.circularProgressBar);
        RelativeLayout rlParent=findViewById(R.id.rlParent);
        TextView txtSubject=findViewById(R.id.txtSubject);
        String currentTheme= UserPreference.getInstance(TrendsActivity.this).getTheme();
        switch (currentTheme){
            case "Red":
                rlParent.setBackgroundColor(getResources().getColor(R.color.red));
                break;

            case "darkRed":
                rlParent.setBackgroundColor(getResources().getColor(R.color.darkRed));

                break;


            case "orange":
                rlParent.setBackgroundColor(getResources().getColor(R.color.orange));

                break;

            case "darkOrange":
                rlParent.setBackgroundColor(getResources().getColor(R.color.darkOrange));
                break;


            case "yellow":
                rlParent.setBackgroundColor(getResources().getColor(R.color.yellow));

                break;

            case "darkYellow":
                rlParent.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                break;

            case "green":
                rlParent.setBackgroundColor(getResources().getColor(R.color.green));

                break;


            case "darkGreen":
                rlParent.setBackgroundColor(getResources().getColor(R.color.darkGreen));
                break;

            case "lightGreen":
                rlParent.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                break;

            case "newGreen":
                rlParent.setBackgroundColor(getResources().getColor(R.color.newGreen));
                break;


            case "blue":
                rlParent.setBackgroundColor(getResources().getColor(R.color.blue));
                break;

            case "purple":
                rlParent.setBackgroundColor(getResources().getColor(R.color.purple));
                break;

            case "darkPurple":
                rlParent.setBackgroundColor(getResources().getColor(R.color.darkPurple));

                break;

            case "darkBlueNew":

            default:
                rlParent.setBackgroundColor(getResources().getColor(R.color.darkBlueNew));
                break;

        }

        if (getIntent() != null) {
            gradebookNumber = getIntent().getIntExtra("gradebookNumber", 0);
            term = getIntent().getStringExtra("term");
            classname = getIntent().getStringExtra("classname");
            String Url = UserPreference.getInstance(this).getBaseUrl();
            txtSubject.setText(classname);
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
        // data


    }


    private void callGetClass(String Url,JSONObject jsonObject) {
        if (circularProgressBar != null && circularProgressBar.getVisibility() == View.GONE) {
            circularProgressBar.setVisibility(View.VISIBLE); }
        String cook = UserPreference.getInstance(TrendsActivity.this).getCookie();
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
                        }
                        String Url = UserPreference.getInstance(TrendsActivity.this).getBaseUrl();
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

        String cook = UserPreference.getInstance(TrendsActivity.this).getCookie();
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
                    @RequiresApi(api = Build.VERSION_CODES.O)
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
                                GradeBookDetailSummary.DBean.ResultsBean resultsBean = resultsBeans.get(i);
                                if (!resultsBeans.get(i).getType().equals("TOTAL")) {

                                    tempBeans.add(resultsBean);
                                    if (!resultsBeans.get(i).isIsDoingWeight()) {
                                        isDoingWeight = true;
                                    }


                                }

                                categories.put(resultsBean.getCategory(),resultsBean.getPercentOfGrade());
                            }
                            //calculateGrade();
                            setUpGraph();




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

    private boolean isDoingWeight;

    HashMap<String,Double>categories=new HashMap<>();


    private double calculateGrade(List<ClassesDetailModel.DBean.ResultsBean> currentAssignments, HashMap<String, Double> categoriesn) {
         double finalResult;
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
            finalResult=percent;
           // return percent;
            // Round it to two decimal places first
        } else {

            double resultGrade = 0.0;
            double totalWeight = 1.0;
            HashMap<String,ScorePair>categoryTotals=new HashMap<>();
            for(String keys: categoriesn.keySet()){
                ScorePair scorePair=new ScorePair();
                scorePair.first=0.0;
                scorePair.second=0.0;
                categoryTotals.put(keys,scorePair);
            }


            for (ClassesDetailModel.DBean.ResultsBean resultsBean:currentAssignments){
                boolean isGraded=resultsBean.isIsGraded();
                double score=resultsBean.getScore();
                double maxScore=resultsBean.getMaxScore();
                String type=resultsBean.getType();
                if (isGraded){
                    ScorePair total=categoryTotals.get(type);
                    if (total!=null){
                        total.first+=score;
                        total.second+=maxScore;
                    }

                }

            }

            HashMap<String, ScorePair> tempTotals = new HashMap<>(categoryTotals);
            for(String key:tempTotals.keySet()){
                if (Objects.requireNonNull(tempTotals.get(key)).second==0.0){
                   totalWeight-= this.categories.get(key)/100;
                   this.categories.remove(key);
                }
            }


           double percentFinal;

            for (String key:categoryTotals.keySet()){
                if (categoriesn.containsKey(key)){
                    double weight= categoriesn.get(key)/totalWeight;
                    ScorePair totalUnweightedScore=categoryTotals.get(key);
                    double unweightedPercent=(totalUnweightedScore.first)/(totalUnweightedScore.second);
                    resultGrade+=weight*unweightedPercent;
                }
            }

            percentFinal  =(resultGrade*100)/100;


            percentFinal = Double.parseDouble(String.format("%.2f", percentFinal));
            //Log.d("resultnnnnnnn", "" + percentFinal);
            finalResult=percentFinal;


        }



        return finalResult;
    }



    List<GradeBookDetailSummary.DBean.ResultsBean> tempBeans=new ArrayList<>();

    private static class ScorePair {
        public double first = 0;
        public double second = 0;

    }

    public  Double findMin(List<Double> list)
    {

        // check list is empty or not
        if (list == null || list.size() == 0) {
            return Double.MAX_VALUE;
        }

        // create a new list to avoid modification
        // in the original list
        List<Double> sortedlist = new ArrayList<>(list);

        // sort list in natural order
        Collections.sort(sortedlist);

        // first element in the sorted list
        // would be minimum
        return sortedlist.get(0);
    }

    /*func getMinimumOfGraph() -> Double  {
        let minLine = linePlotData.min()!
                let minDot = dotPlotData.min()!
                let minAbs = min(minLine, minDot)
        let roundedMin = floor(minAbs/10)*10
        return roundedMin
    }*/


    private float[] getReferenceLinePositions(){
        double difference=100-getMinimumOfGraph();
        float[]doubles;
        if (difference<=20){
          doubles  =new float[]{100f,95f,90f,85f,80f};

        }else {
            doubles  =new float[]{100f,90f,80f,70f,60f,50f,40f,30f,20f,10f,0f};
        }

        return doubles;
    }

    private double getMinimumOfGraph(){
        List<Double>getList=new ArrayList<>();
        getList.addAll(linePlotData);
        getList.addAll(dotPlotData);
        double minAbs = findMin(getList);
        return (minAbs/10)*10;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpGraph(){
        for (int i = 0; i < assignmentList.size(); i++) {
            List<ClassesDetailModel.DBean.ResultsBean> currentAssignments=new ArrayList<>();
            ClassesDetailModel.DBean.ResultsBean resultsBean=assignmentList.get(i);
            currentAssignments.add(resultsBean);
            double res=calculateGrade(currentAssignments,categories);

            Log.d("grade",""+res);
        }
        try {
            ArrayList<ILineDataSet> dataSets;
            Collections.reverse(assignmentList);
           if (assignmentList.size()<=1) {
                return;
            }
            for (int i = 0; i <assignmentList.size()-1 ; i++) {
                List<ClassesDetailModel.DBean.ResultsBean> currentAssignments=new ArrayList<>();

                for (int y = 0;y<assignmentList.size(); y++) {
                    ClassesDetailModel.DBean.ResultsBean resultsBean=assignmentList.get(y);
                    currentAssignments.add(resultsBean);
                }
                double res=calculateGrade(currentAssignments,categories);
                Log.d("grade",""+res);
                if (!Double.isNaN(res)){
                    linePlotData.add(res);
                    dotPlotData.add(currentAssignments.get(currentAssignments.size()-1).getPercent());
                }



            }







            List<String> xAxisValues = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June","July", "August", "September", "October", "November", "Decemeber"));
            float[] incomeEntries = getReferenceLinePositions();
            List<Entry>entries=new ArrayList<>();
            int i=0;
            for (float incomeEntry : incomeEntries) {
                i=i+1;
                entries.add(new Entry(i, incomeEntry));
                entries.subList(0, i);

            }

            List<Entry>entriesOther=new ArrayList<>();
            for (int j = 0; j < dotPlotData.size(); j++) {
               entriesOther.add(new Entry(j,dotPlotData.get(j).floatValue()))  ;
               entriesOther.subList(0,j);
            }
            dataSets = new ArrayList<>();
           /* LineDataSet set1;

            set1 = new LineDataSet(entries, "Income");
            set1.setColor(Color.WHITE);
            set1.setValueTextColor(Color.WHITE);
            set1.setValueTextSize(10f);
            set1.setLineWidth(3);
            set1.setHighLightColor(Color.WHITE);
            set1.setCircleHoleColor(Color.WHITE);

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSets.add(set1);*/

            //customization
            LineChart mLineGraph = findViewById(R.id.graph);

            mLineGraph.setTouchEnabled(true);
            mLineGraph.setDragEnabled(true);
            mLineGraph.setScaleEnabled(false);
            mLineGraph.setPinchZoom(false);
            mLineGraph.setDrawGridBackground(false);


            mLineGraph.setExtraLeftOffset(15);
            mLineGraph.setExtraRightOffset(15);
            mLineGraph.setGridBackgroundColor(Color.WHITE);


            //to hide background lines
            mLineGraph.getXAxis().setDrawGridLines(true);
            mLineGraph.getAxisLeft().setDrawGridLines(true);
            mLineGraph.getAxisRight().setDrawGridLines(true);



            //to hide right Y and top X border
            YAxis rightYAxis = mLineGraph.getAxisRight();
            rightYAxis.setEnabled(false);
            YAxis leftYAxis = mLineGraph.getAxisLeft();
            leftYAxis.setTextColor(getResources().getColor(R.color.white));
            leftYAxis.setEnabled(true);
/*
            leftYAxis.setAxisMaxValue(100);
            float rangeMin= getMinimumOfGraph() >= 80 ? 80 : (float) getMinimumOfGraph();
            leftYAxis.setAxisMinValue(rangeMin);
*/
            XAxis topXAxis = mLineGraph.getXAxis();
            topXAxis.setEnabled(true);


            XAxis xAxis = mLineGraph.getXAxis();
            xAxis.setAxisLineColor(Color.WHITE);
            xAxis.setGranularity(1f);
            xAxis.setCenterAxisLabels(true);
            xAxis.setEnabled(false);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.TOP);


          /*  set1.setLineWidth(4f);
            set1.setCircleRadius(3f);
            set1.setDrawValues(false);*/

            //String setter in x-Axis

            LineData data = new LineData(dataSets);
            mLineGraph.setData(data);
            mLineGraph.animateX(2000);
            mLineGraph.invalidate();
            data.setValueTextColor(Color.WHITE);
            data.setValueTypeface(getResources().getFont(R.font.gotham_book));
            mLineGraph.getLegend().setEnabled(false);


            LineDataSet set2;

            set2 = new LineDataSet(entriesOther, "Income");
            set2.setColor(Color.WHITE);
            set2.setValueTextColor(Color.WHITE);
            set2.setValueTextSize(10f);
            set2.setLineWidth(3);
            set2.setHighLightColor(Color.WHITE);
            set2.setCircleHoleColor(Color.WHITE);

            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSets.add(set2);

            LineData data2 = new LineData(dataSets);
            mLineGraph.setData(data2);
            mLineGraph.animateX(2000);
            mLineGraph.invalidate();
            data2.setValueTextColor(Color.WHITE);
            data2.setValueTypeface(getResources().getFont(R.font.gotham_book));
            mLineGraph.getLegend().setEnabled(false);




            //  mLineGraph.getDescription().setEnabled(false);

        }catch (NegativeArraySizeException e){
            Log.d("Exception","ms"+e.getMessage());
            e.printStackTrace();
        }

    }
}