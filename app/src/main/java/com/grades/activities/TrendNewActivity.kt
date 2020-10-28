package com.grades.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.app.grades.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.gson.Gson
import com.grades.model.ClassesDetailModel
import com.grades.model.GradeBookDetailSummary
import com.grades.utils.UserPreference
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TrendNewActivity : AppCompatActivity(), View.OnClickListener {
    var circularProgressBar: ProgressBar? = null
    var ivClose: ImageView? = null
    var gradebookNumber = 0
    var term: String? = null
    var classname: String? = null
    var resultsBeans: MutableList<ClassesDetailModel.DBean.ResultsBean> = ArrayList()
    var assignmentList: List<ClassesDetailModel.DBean.ResultsBean?>? = null
    var linePlotData: MutableList<Double> = ArrayList()
    var dotPlotData: MutableList<Double> = ArrayList()
    private val lastX = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trends)
        initView()
    }

    private val incomeEntries: List<Entry>
        private get() {
            val incomeEntries = ArrayList<Entry>()
            incomeEntries.add(Entry(1F, 11300F))
            incomeEntries.add(Entry(2F, 1390F))
            incomeEntries.add(Entry(3F, 1190F))
            incomeEntries.add(Entry(4F, 7200F))
            incomeEntries.add(Entry(5F, 4790F))
            incomeEntries.add(Entry(6F, 4500F))
            incomeEntries.add(Entry(7F, 8000F))
            incomeEntries.add(Entry(8F, 7034F))
            incomeEntries.add(Entry(9F, 4307F))
            incomeEntries.add(Entry(10F, 8762F))
            incomeEntries.add(Entry(11F, 4355F))
            incomeEntries.add(Entry(12F, 6000F))
            return incomeEntries.subList(0, 12)
        }

    private fun initView() {
        val ivClose = findViewById<ImageView>(R.id.ivClose)
       ivClose.setOnClickListener(this)
        circularProgressBar = findViewById(R.id.circularProgressBar)
        val rlParent = findViewById<RelativeLayout>(R.id.rlParent)
        val txtSubject = findViewById<TextView>(R.id.txtSubject)
        when (UserPreference.getInstance(this@TrendNewActivity).theme) {
            "Red" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.red))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.red)
                }
            }


            "darkRed" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.darkRed))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkRed)
                }
            }
            "orange" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.orange))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.orange)
                }
            }
            "darkOrange" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.darkOrange))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.orange)
                }
            }
            "yellow" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.yellow))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.yellow)
                }
            }
            "darkYellow" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.darkYellow))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkYellow)
                }
            }
            "green" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.green))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.green)
                }
            }
            "darkGreen" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.darkGreen))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkGreen)
                }
            }
            "lightGreen" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.lightGreen))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.lightGreen)
                }
            }
            "newGreen" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.newGreen))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.newGreen)
                }
            }
            "blue" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.blue))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.blue)
                }
            }
            "purple" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.purple))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.purple)
                }
            }
            "darkPurple" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.darkPurple))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkPurple)
                }
            }
            "darkBlueNew" -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.darkBlueNew))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkBlueNew)
                }
            }
            else -> {
                rlParent.setBackgroundColor(resources.getColor(R.color.darkBlueNew))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkBlueNew)
                }
            }
        }
        if (intent != null) {
            gradebookNumber = intent.getIntExtra("gradebookNumber", 0)
            term = intent.getStringExtra("term")
            classname = intent.getStringExtra("classname")
            val Url = UserPreference.getInstance(this).baseUrl
            txtSubject.text = classname
            val jsonObject = JSONObject()
            try {
                jsonObject.put("gradebookNumber", gradebookNumber)
                jsonObject.put("term", term)
                jsonObject.put("requestedPage", 1)
                jsonObject.put("pageSize", 200)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //new GetJsonTask(Url.trim() + "m/api/MobileWebAPI.asmx/GetGradebookDetailsData", jsonObject).execute();
            callGetClass(Url.trim { it <= ' ' } + "m/api/MobileWebAPI.asmx/GetGradebookDetailsData", jsonObject)
        }
        // data
    }

    private fun callGetClass(Url: String, jsonObject: JSONObject) {
        if (circularProgressBar != null && circularProgressBar!!.visibility == View.GONE) {
            circularProgressBar!!.visibility = View.VISIBLE
        }
        val cook = UserPreference.getInstance(this@TrendNewActivity).cookie
        val okHttpClient = OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build()
        AndroidNetworking.post(Url)
                .setOkHttpClient(okHttpClient)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Cookie", "ASP.NET_SessionId=$cook").build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        Log.d("Resss", "" + response)
                        if (circularProgressBar != null && circularProgressBar!!.visibility == View.VISIBLE) {
                            circularProgressBar!!.visibility = View.GONE
                        }
                        if (response != "") {
                            val gson = Gson()
                            val classes = gson.fromJson(response, ClassesDetailModel::class.java)
                            resultsBeans.addAll(classes.d.results)
                            assignmentList = ArrayList(classes.d.results)
                        }
                        val Url = UserPreference.getInstance(this@TrendNewActivity).baseUrl
                        val jsonObject = JSONObject()
                        try {
                            jsonObject.put("gradebookNumber", gradebookNumber)
                            jsonObject.put("term", term)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        //new GetGrades(Url.trim() + "/m/api/MobileWebAPI.asmx/GetGradebookDetailedSummaryData", jsonObject).execute();
                        callGetGrades(Url.trim { it <= ' ' } + "/m/api/MobileWebAPI.asmx/GetGradebookDetailedSummaryData", jsonObject)
                    }

                    override fun onError(anError: ANError) {
                        Log.d("Error", "" + anError.response)
                        if (anError.errorCode != 0) {
                            if (circularProgressBar != null && circularProgressBar!!.visibility == View.GONE) {
                                circularProgressBar!!.visibility = View.GONE
                            }
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Toast.makeText(this@TrendNewActivity, "Session Expire Login Again", Toast.LENGTH_SHORT).show()
                            UserPreference.getInstance(this@TrendNewActivity).clearSession()
                            startActivity(Intent(this@TrendNewActivity, LoginActivity::class.java))
                            finish()
                            Log.d(TAG, "onError errorCode : " + anError.errorCode)
                            Log.d(TAG, "onError errorBody : " + anError.errorBody)
                            Log.d(TAG, "onError errorDetail : " + anError.errorDetail)
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + anError.errorDetail)
                        }
                    }
                })
    }

    private fun callGetGrades(Url: String, jsonObject: JSONObject) {
        if (circularProgressBar != null && circularProgressBar!!.visibility == View.GONE) {
            circularProgressBar!!.visibility = View.VISIBLE
        }
        val cook = UserPreference.getInstance(this@TrendNewActivity).cookie
        val okHttpClient = OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build()
        AndroidNetworking.post(Url)
                .setOkHttpClient(okHttpClient)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Cookie", "ASP.NET_SessionId=$cook").build()
                .getAsString(object : StringRequestListener {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    override fun onResponse(response: String) {
                        Log.d("Resss", "" + response)
                        if (circularProgressBar != null && circularProgressBar!!.visibility == View.VISIBLE) {
                            circularProgressBar!!.visibility = View.GONE
                        }
                        if (response != "") {
                            val gson = Gson()
                            val classes = gson.fromJson(response, GradeBookDetailSummary::class.java)
                            val resultsBeans: List<GradeBookDetailSummary.DBean.ResultsBean> = ArrayList(classes.d.results)
                            //tempBeans = new ArrayList<>(classes.getD().getResults());
                            for (i in resultsBeans.indices) {
                                val resultsBean = resultsBeans[i]
                                if (resultsBeans[i].type != "TOTAL") {
                                    tempBeans.add(resultsBean)
                                    if (!resultsBeans[i].isIsDoingWeight) {
                                        isDoingWeight = true
                                    }
                                }
                                categories[resultsBean.category] = resultsBean.percentOfGrade
                            }
                            //calculateGrade();
                            setUpGraph()
                        }
                    }

                    override fun onError(anError: ANError) {
                        Log.d("Error", "" + anError.response)
                        if (anError.errorCode != 0) {
                            if (circularProgressBar != null && circularProgressBar!!.visibility == View.GONE) {
                                circularProgressBar!!.visibility = View.GONE
                            }
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Log.d(TAG, "onError errorCode : " + anError.errorCode)
                            Log.d(TAG, "onError errorBody : " + anError.errorBody)
                            Log.d(TAG, "onError errorDetail : " + anError.errorDetail)
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + anError.errorDetail)
                        }
                    }
                })
    }

    private var isDoingWeight = false
    var categories = HashMap<String, Double>()
    private fun calculateGrade(currentAssignments: List<ClassesDetailModel.DBean.ResultsBean?>, categoriesn: HashMap<String, Double>): Double {
        val finalResult: Double
        if (isDoingWeight) {
            val scorePair = ScorePair()
            for (resultsBean in resultsBeans) {
                val isGraded = resultsBean.isIsGraded
                val score = resultsBean.score
                val maxScore = resultsBean.maxScore
                if (isGraded) {
                    scorePair.first += score
                    scorePair.second += maxScore
                }
            }
            // Convert them to a percent and then return it
            val finalScore = scorePair.first / scorePair.second * 100.0
            @SuppressLint("DefaultLocale") val percentString = String.format("%.2f", finalScore)
            val percent = percentString.toDouble()
            Log.d("isgraded", "" + percent)
            finalResult = percent
            // return percent;
            // Round it to two decimal places first
        } else {
            var resultGrade = 0.0
            var totalWeight = 1.0
            val categoryTotals = HashMap<String, ScorePair>()
            val categoriesNew = HashMap<String, Double>()

            categoriesNew.putAll(categoriesn)

            for (keys in categoriesNew.keys) {
                val scorePair = ScorePair()
                scorePair.first = 0.0
                scorePair.second = 0.0
                categoryTotals[keys] = scorePair
            }
            for (resultsBean in currentAssignments) {
                val isGraded = resultsBean!!.isIsGraded
                val score = resultsBean.score
                val maxScore = resultsBean.maxScore
                val type = resultsBean.type
                if (isGraded) {
                    val total = categoryTotals[type]
                    if (total != null) {
                        total.first += score
                        total.second += maxScore
                    }
                }
            }
            val tempTotals = HashMap(categoryTotals)
            for (key in tempTotals.keys) {
                if (Objects.requireNonNull(tempTotals[key])!!.second == 0.0) {
                    totalWeight -= categoriesNew[key]!! / 100
                    categoriesNew.remove(key)
                }
            }
            var percentFinal: Double
            for (key in categoryTotals.keys) {
                if (categoriesNew.containsKey(key)) {
                    val weight = categoriesNew[key]!! / totalWeight
                    val totalUnweightedScore = categoryTotals[key]
                    val unweightedPercent = totalUnweightedScore!!.first / totalUnweightedScore.second
                    resultGrade += weight * unweightedPercent
                }
            }
            percentFinal = resultGrade * 100 / 100
            percentFinal = String.format("%.2f", percentFinal).toDouble()
            //Log.d("resultnnnnnnn", "" + percentFinal);
            finalResult = percentFinal
        }
        return finalResult
    }

    var tempBeans: MutableList<GradeBookDetailSummary.DBean.ResultsBean> = ArrayList()

    private class ScorePair {
        var first = 0.0
        var second = 0.0
    }

    fun findMin(list: List<Double>?): Double {

        // check list is empty or not
        if (list == null || list.isEmpty()) {
            return Double.MAX_VALUE
        }

        // create a new list to avoid modification
        // in the original list
        val sortedList: List<Double> = ArrayList(list)

        // sort list in natural order
        Collections.sort(sortedList)

        // first element in the sorted list
        // would be minimum
        return sortedList[0]
    }


    private val referenceLinePositions: FloatArray
        get() {
            val difference = 100 - minimumOfGraph
            val doubles: FloatArray
            doubles = if (difference <= 20) {
                floatArrayOf(100f, 95f, 90f, 85f, 80f)
            } else {
                floatArrayOf(100f, 90f, 80f, 70f, 60f, 50f, 40f, 30f, 20f, 10f, 0f)
            }
            return doubles
        }
    private val minimumOfGraph: Double
        get() {
            val getList: MutableList<Double> = ArrayList()
            getList.addAll(linePlotData)
            getList.addAll(dotPlotData)
            val minAbs = findMin(getList)
            return minAbs / 10 * 10
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setUpGraph() {

        try {
            Collections.reverse(assignmentList)
            if (assignmentList!!.size <= 1) {
                return
            }
            val currentAssignments: MutableList<ClassesDetailModel.DBean.ResultsBean?> = ArrayList()

            for (i in 0 until assignmentList!!.size - 1) {


                for (number  in 0..i) {
                    currentAssignments.add(assignmentList!![number])

                }

                val res = calculateGrade(currentAssignments, categories)
                Log.d("grade", "" + res)

                    linePlotData.add(res)
                    dotPlotData.add(currentAssignments[currentAssignments.size - 1]!!.percent)

            }
            val incomeEntries = referenceLinePositions
            val entries: MutableList<Entry> = ArrayList()
            var i = 0
            for (incomeEntry in incomeEntries) {
                i += 1
                entries.add(Entry(i.toFloat(), incomeEntry))
                entries.subList(0, i)
            }
            val entriesOther: MutableList<Entry> = ArrayList()
            for (j in linePlotData.indices) {
                entriesOther.add(Entry(j.toFloat(), linePlotData[j].toFloat()))
                entriesOther.subList(0, j)
            }
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
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
            val mLineGraph = findViewById<LineChart>(R.id.graph)
            mLineGraph.setTouchEnabled(true)
            mLineGraph.isDragEnabled = true
            mLineGraph.setScaleEnabled(false)
            mLineGraph.setPinchZoom(false)
            mLineGraph.extraLeftOffset = 15f
            mLineGraph.extraRightOffset = 15f
            mLineGraph.setDrawGridBackground(false)
            mLineGraph.setGridBackgroundColor(Color.WHITE)
            mLineGraph.xAxis.axisLineColor = resources.getColor(R.color.white);



            //to hide background lines
            mLineGraph.xAxis.setDrawGridLines(true)
            mLineGraph.axisLeft.setDrawGridLines(true)
            mLineGraph.axisRight.setDrawGridLines(true)


            //to hide right Y and top X border
            val rightYAxis = mLineGraph.axisRight


            rightYAxis.isEnabled = false
            val leftYAxis = mLineGraph.axisLeft
            leftYAxis.textColor = resources.getColor(R.color.white)
            leftYAxis.zeroLineColor=resources.getColor(R.color.white)
            leftYAxis.isEnabled = true
            mLineGraph.axisLeft.zeroLineColor=resources.getColor(R.color.white)
            mLineGraph.axisLeft.axisLineColor=resources.getColor(R.color.white)
            leftYAxis.gridColor=resources.getColor(R.color.white)

            leftYAxis.axisMinimum = 0f; // start at zero
            leftYAxis.axisMaximum = 100f; // the axis maximum is 100


            val topXAxis = mLineGraph.xAxis
            topXAxis.isEnabled = true
            val xAxis = mLineGraph.xAxis
            xAxis.axisLineColor = Color.WHITE
            xAxis.granularity = 1f
            xAxis.setCenterAxisLabels(true)
            xAxis.gridColor=resources.getColor(R.color.white)
            xAxis.isEnabled = false
            xAxis.setDrawGridLines(false)
            xAxis.spaceMax=0.10f

            xAxis.position = XAxis.XAxisPosition.TOP

            val ll2 = LimitLine(8f, "")
            ll2.lineWidth = 3f
            ll2.lineColor = ContextCompat.getColor(this, android.R.color.white)
            ll2.enableDashedLine(5f, 5f, 0f) //dashed line

            ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP //Set the position of the label display

            ll2.textColor = ContextCompat.getColor(this, android.R.color.white)
            ll2.textSize = 10f
            leftYAxis.addLimitLine(ll2)
            ll2.typeface=resources.getFont(R.font.gotham_book)
            leftYAxis.typeface=resources.getFont(R.font.gotham_book)



            xAxis.addLimitLine(ll2)



            val data = LineData(dataSets)
            mLineGraph.data = data
            mLineGraph.animateX(2000)
            mLineGraph.invalidate()
            data.setValueTextColor(Color.WHITE)
            data.setValueTypeface(resources.getFont(R.font.gotham_book))
            mLineGraph.legend.isEnabled = false
            val set2: LineDataSet
            set2 = LineDataSet(entriesOther, "Income")
            set2.color = Color.WHITE
            set2.valueTextColor = Color.WHITE
            set2.valueTextSize = 10f
            set2.lineWidth = 3f
            set2.color = Color.WHITE;
            set2.setCircleColor(Color.WHITE);

            set2.highLightColor = Color.WHITE
            set2.circleHoleColor = Color.WHITE
            set2.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSets.add(set2)
            val data2 = LineData(dataSets)
            mLineGraph.data = data2
            mLineGraph.animateX(2000)
            mLineGraph.invalidate()
            data2.setValueTextColor(Color.WHITE)
            data2.setValueTypeface(resources.getFont(R.font.gotham_book))
            mLineGraph.legend.isEnabled = false


            //  mLineGraph.getDescription().setEnabled(false);
        } catch (e: NegativeArraySizeException) {
            Log.d("Exception", "ms" + e.message)
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "TrendsActivity"
    }

    override fun onClick(p0: View?) {
        when (p0!!.id){
            R.id.ivClose -> {
                onBackPressed()
            }
        }
    }
}