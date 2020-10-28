package com.grades.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.app.grades.R
import com.google.gson.Gson
import com.grades.adapters.ClassesDetailsAdapter
import com.grades.model.ClassesDetailModel
import com.grades.model.GradeBookDetailSummary
import com.grades.utils.UserPreference
import io.blackbox_vision.wheelview.view.WheelView
import okhttp3.OkHttpClient
import org.apache.http.NameValuePair
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class DetailsofClass : AppCompatActivity(), ClassesDetailsAdapter.OnClickParent, View.OnClickListener {
    var rlMock: RelativeLayout? = null
    var viewMock: View? = null
    var llPercentage: LinearLayout? = null
    var txtSubject: TextView? = null
    var circularProgressBar: ProgressBar? = null
    var gradebookNumber = 0
    var term: String? = null
    var classesDetailsAdapter: ClassesDetailsAdapter? = null
    var classname: String? = null
    var llParent: RelativeLayout? = null
    var resultsBeans: MutableList<ClassesDetailModel.DBean.ResultsBean> = ArrayList()
    var txtPercentage: TextView? = null
    var txtGetPercentage: TextView? = null
    var txtMaxPercentage: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classes_detail)
        initView()
    }

    var recyclerClassesDetail: RecyclerView? = null
    private val updateData: List<NameValuePair> = ArrayList()
    private fun initView() {
        recyclerClassesDetail = findViewById(R.id.recyclerClassesDetail)
        recyclerClassesDetail?.setLayoutManager(LinearLayoutManager(this))
        viewMock = findViewById(R.id.viewMock)
        llPercentage = findViewById(R.id.llPercentage)
        txtMaxPercentage = findViewById(R.id.txtMaxPercentage)
        txtGetPercentage = findViewById(R.id.txtGetPercentage)
        txtSubject=findViewById(R.id.txtSubject)
        classesDetailsAdapter = ClassesDetailsAdapter(resultsBeans, this@DetailsofClass, this, false)
        recyclerClassesDetail!!.setAdapter(classesDetailsAdapter)
        rlMock = findViewById(R.id.rlMock)
        txtPercentage = findViewById(R.id.txtPercentage)
        llParent = findViewById(R.id.llParent)
        val txtSubject = findViewById<TextView>(R.id.txtSubject)
        val ivClose = findViewById<ImageView>(R.id.ivClose)
        val txtAddMock = findViewById<TextView>(R.id.txtAddMock)
        val ivCalculator = findViewById<ImageView>(R.id.ivCalculator)
        val ivMenu = findViewById<ImageView>(R.id.ivMenu)
        ivMenu.setOnClickListener(this)
        ivClose.setOnClickListener(this)
        ivCalculator.setOnClickListener(this)
        circularProgressBar = findViewById(R.id.circularProgressBar)
        txtAddMock.setOnClickListener(this)
        if (intent != null) {
            gradebookNumber = intent.getIntExtra("gradebookNumber", 0)
            term = intent.getStringExtra("term")
            classname = intent.getStringExtra("classname")
            txtSubject.text = classname
            val Url = UserPreference.getInstance(this).baseUrl
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
        val theme = UserPreference.getInstance(this@DetailsofClass).theme
        when (theme) {
            "Red" -> {
                // setTheme(R.style.red);
                txtPercentage?.setTextColor(resources.getColor(R.color.red))
                txtSubject.setTextColor(resources.getColor(R.color.gray))
                llParent!!.setBackgroundColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.white)
                }
            }
            "darkRed" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.red))
                txtPercentage!!.setTextColor(resources.getColor(R.color.white))
                txtSubject.setTextColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkRed)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkRed)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkRed)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkRed)
                }
            }
            "orange" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.white))
                txtPercentage!!.setTextColor(resources.getColor(R.color.orange))
                txtSubject.setTextColor(resources.getColor(R.color.gray))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.white)
                }
            }
            "darkOrange" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.orange))
                txtPercentage!!.setTextColor(resources.getColor(R.color.white))
                txtSubject.setTextColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkOrange)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkOrange)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkOrange)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkOrange)
                }
            }
            "yellow" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.white))
                txtPercentage?.setTextColor(resources.getColor(R.color.yellow))
                txtSubject.setTextColor(resources.getColor(R.color.gray))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.white)
                }
            }
            "darkYellow" -> {
                llParent?.setBackgroundColor(resources.getColor(R.color.yellow))
                txtPercentage!!.setTextColor(resources.getColor(R.color.white))
                txtSubject.setTextColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkYellow)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkYellow)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkYellow)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkYellow)
                }
            }
            "green" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.white))
                txtPercentage!!.setTextColor(resources.getColor(R.color.green))
                txtSubject.setTextColor(resources.getColor(R.color.gray))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.green)
                }
            }
            "darkGreen" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.green))
                txtPercentage!!.setTextColor(resources.getColor(R.color.white))
                txtSubject.setTextColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkGreen)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkGreen)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkGreen)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkGreen)
                }
            }
            "lightGreen" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.white))
                txtPercentage!!.setTextColor(resources.getColor(R.color.lightGreen))
                txtSubject.setTextColor(resources.getColor(R.color.gray))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.white)
                }
            }
            "newGreen" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.lightGreen))
                txtPercentage!!.setTextColor(resources.getColor(R.color.white))
                txtSubject.setTextColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.newGreen)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.newGreen)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.newGreen)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.newGreen)
                }
            }
            "blue" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.white))
                txtPercentage!!.setTextColor(resources.getColor(R.color.blue))
                txtSubject.setTextColor(resources.getColor(R.color.gray))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.white)
                }
            }
            "purple" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.white))
                txtPercentage!!.setTextColor(resources.getColor(R.color.purple))
                txtSubject.setTextColor(resources.getColor(R.color.gray))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkgray)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.white)
                }
            }
            "darkPurple" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.purple))
                txtPercentage!!.setTextColor(resources.getColor(R.color.white))
                txtSubject.setTextColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkPurple)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkPurple)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkPurple)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkPurple)
                }
            }
            "darkBlueNew" -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.blue))
                txtPercentage!!.setTextColor(resources.getColor(R.color.white))
                txtSubject.setTextColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkBlueNew)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkBlueNew)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkBlueNew)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkBlueNew)
                }
            }
            else -> {
                llParent!!.setBackgroundColor(resources.getColor(R.color.blue))
                txtPercentage!!.setTextColor(resources.getColor(R.color.white))
                txtSubject.setTextColor(resources.getColor(R.color.white))
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkBlueNew)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivCalculator.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkBlueNew)
                )
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivMenu.drawable),
                        ContextCompat.getColor(this@DetailsofClass, R.color.darkBlueNew)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(R.color.darkBlueNew)
                }
            }
        }
    }

    override fun onClickItem(position: Int) {
        resultsBeans[position].isSeekBarVisible = true
        classesDetailsAdapter!!.notifyDataSetChanged()
    }

    override fun onSwipeSeekBar(position: Int) {
        calculateGrade()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivMenu -> populateViewForDialog()
            R.id.ivClose -> onBackPressed()
            R.id.txtAddMock -> addMockAssignment()
            R.id.ivCalculator -> if (rlMock!!.visibility == View.VISIBLE) {
                rlMock!!.visibility = View.GONE
            } else {
                val theme = UserPreference.getInstance(this@DetailsofClass).theme
                when (theme) {
                    "Red" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkRed))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.red))
                    }
                    "darkRed" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkRed))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkRed))
                    }
                    "orange" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.orange))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkOrange))
                    }
                    "darkOrange" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkOrange))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkOrange))
                    }
                    "yellow" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.yellow))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkYellow))
                    }
                    "darkYellow" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkYellow))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkYellow))
                    }
                    "green" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.green))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkGreen))
                    }
                    "darkGreen" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkGreen))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkGreen))
                    }
                    "lightGreen" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.newGreen))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.lightGreen))
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.newGreen))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.newGreen))
                    }
                    "newGreen" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.newGreen))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.newGreen))
                    }
                    "blue" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkBlueNew))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.blue))
                    }
                    "purple" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.purple))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkPurple))
                    }
                    "darkPurple" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkPurple))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkPurple))
                    }
                    "darkBlueNew" -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkBlueNew))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkBlueNew))
                    }
                    else -> {
                        viewMock!!.setBackgroundColor(resources.getColor(R.color.darkBlueNew))
                        rlMock!!.setBackgroundColor(resources.getColor(R.color.darkBlueNew))
                    }
                }
                rlMock!!.visibility = View.VISIBLE
                if (resultsBeans.size > 0) {
                    resultsBeans[0].isSeekBarVisible = true
                    classesDetailsAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    fun populateViewForDialog() {
        val inflater = (this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val theme = UserPreference.getInstance(this@DetailsofClass).theme
        val popupView = inflater.inflate(R.layout.popup_menu_layout, null)
        val pw = PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
        val cardParent: CardView = popupView.findViewById(R.id.cardParent)
        val txtBreakDown = popupView.findViewById<TextView>(R.id.txtBreakDown)
        val txtTrends = popupView.findViewById<TextView>(R.id.txtTrends)
        val txtShowImpact = popupView.findViewById<TextView>(R.id.txtShowImpact)
        txtShowImpact.text = "Show Impacts"
        txtTrends.setOnClickListener {
            val intent=Intent(this@DetailsofClass, TrendNewActivity::class.java)
            intent.putExtra("gradebookNumber", gradebookNumber)
            intent.putExtra("term", term)
            intent.putExtra("classname", classname)
            startActivity(intent)
        }
        txtShowImpact.setOnClickListener {
            if (txtShowImpact.text.toString() == "Show Impacts") {
                for (i in resultsBeans.indices) {
                    resultsBeans[i].isShowImpact = true
                }
                classesDetailsAdapter!!.notifyDataSetChanged()
                txtShowImpact.text = "Hide Impacts"
            } else {
                for (i in resultsBeans.indices) {
                    resultsBeans[i].isShowImpact = false
                }
                classesDetailsAdapter!!.notifyDataSetChanged()
                pw.dismiss()
            }
        }
        txtBreakDown.setOnClickListener {
            val intent = Intent(this@DetailsofClass, BreakDownActivity::class.java)
            intent.putExtra("term", term)
            intent.putExtra("classname", classname)
            intent.putExtra("gradebookNumber", gradebookNumber)
            startActivity(intent)
        }
        pw.showAtLocation(findViewById(R.id.ivMenu), Gravity.TOP or Gravity.RIGHT, 20, 120)
        when (theme) {
            "Red" ->                 // setTheme(R.style.red);
                cardParent.setCardBackgroundColor(resources.getColor(R.color.red))
            "darkRed" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.darkRed))
            "orange" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.orange))
            "darkOrange" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.darkOrange))
            "yellow" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.yellow))
            "darkYellow" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.darkYellow))
            "green" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.green))
            "darkGreen" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.darkGreen))
            "lightGreen" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.lightGreen))
            "newGreen" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.newGreen))
            "blue" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.blue))
            "purple" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.purple))
            "darkPurple" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.darkPurple))
            "darkBlueNew" -> cardParent.setCardBackgroundColor(resources.getColor(R.color.darkBlueNew))
            else -> cardParent.setCardBackgroundColor(resources.getColor(R.color.darkBlueNew))
        }
    }

    private fun addMockAssignment() {
        val type = arrayOf("")
        val options: MutableList<String> = ArrayList()
        val dialog = Dialog(this, R.style.PopupDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_mock_dialog)
        val params = Objects.requireNonNull(dialog.window)!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = params
        val picker_ui_view: WheelView = dialog.findViewById(R.id.loop_view)
        val btnAddAssignment = dialog.findViewById<Button>(R.id.btnAddAssignment)
        val txtHeading = dialog.findViewById<TextView>(R.id.txtHeading)
        val edtAssignmentName = dialog.findViewById<EditText>(R.id.edtAssignmentName)
        val edtTotalMarks = dialog.findViewById<EditText>(R.id.edtTotalMarks)
        val edtMarks = dialog.findViewById<EditText>(R.id.edtMarks)
        edtMarks.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (edtMarks.text.length == 2) {
                    edtTotalMarks.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        llParent!!.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            val contentView = findViewById<View>(R.id.llParent) as RelativeLayout
            contentView.getWindowVisibleDisplayFrame(r)
            val screenHeight = contentView.rootView.height

            // r.bottom is the position above soft keypad or device button.
            // if keypad is shown, the r.bottom is smaller than that before.
            val keypadHeight = screenHeight - r.bottom
            Log.d("Nifras", "keypadHeight = $keypadHeight")
            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                // lytMaster.setTop(0);
                picker_ui_view.visibility = View.GONE
            } else {
                picker_ui_view.visibility = View.VISIBLE

                // lytMaster.setBaselineAligned(true);
                // keyboard is closed
            }
        }
        btnAddAssignment.setOnClickListener { view: View? ->
            if (TextUtils.isEmpty(edtAssignmentName.text.toString())) {
                Toast.makeText(this@DetailsofClass, "Enter Assignment", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(edtMarks.text.toString())) {
                Toast.makeText(this@DetailsofClass, "Enter Marks", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(edtTotalMarks.text.toString())) {
                Toast.makeText(this@DetailsofClass, "Enter Total Marks", Toast.LENGTH_SHORT).show()
            } else if (edtMarks.text.toString().toDouble() > edtTotalMarks.text.toString().toDouble()) {
                Toast.makeText(this, "Marks Should not be Greater than total marks", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
                var isGraded = false
                for (i in resultsBeans.indices) {
                    if (resultsBeans[i].isIsGraded) {
                        isGraded = true
                    }
                }
                val resultsBean = ClassesDetailModel.DBean.ResultsBean()
                resultsBean.type = type[0]
                resultsBean.description = edtAssignmentName.text.toString()
                resultsBean.maxScore = edtTotalMarks.text.toString().toDouble()
                resultsBean.score = edtMarks.text.toString().toDouble()
                val percent = resultsBean.score / resultsBean.maxScore * 100.00
                resultsBean.percent = percent
                resultsBean.isSeekBarVisible = true
                resultsBean.isIsGraded = isGraded
                resultsBean.isAddedMock = true
                resultsBeans[0] = resultsBean
                classesDetailsAdapter!!.notifyDataSetChanged()
                calculateGrade()
            }
        }
        val isShow = false

        /*  if (edtAssignmentName.hasFocusable()){
            picker_ui_view.setVisibility(View.GONE);
            edtAssignmentName.lo
        }

        if (isShow){
            picker_ui_view.setVisibility(View.VISIBLE);
        }*/
        val ivCloseDialog = dialog.findViewById<ImageView>(R.id.ivCloseDialog)
        ivCloseDialog.setOnClickListener { view: View? -> dialog.dismiss() }
        for (i in resultsBeans.indices) {
            if (!options.contains(resultsBeans[i].type)) {
                options.add(resultsBeans[i].type)
            }
        }
        picker_ui_view.setCanLoop(false)
        picker_ui_view.setLoopListener { item: Int -> type[0] = options[item] }
        picker_ui_view.setTextSize(14f)
        if (options.size > 0) {
            picker_ui_view.setItems(options)
        }
        val theme = UserPreference.getInstance(this@DetailsofClass).theme
        when (theme) {
            "Red" -> {
                txtHeading.setTextColor(resources.getColor(R.color.red))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_red)
            }
            "darkRed" -> {
                txtHeading.setTextColor(resources.getColor(R.color.darkRed))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_darkred)
            }
            "orange" -> {
                txtHeading.setTextColor(resources.getColor(R.color.orange))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_orange)
            }
            "darkOrange" -> {
                txtHeading.setTextColor(resources.getColor(R.color.darkOrange))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_orange)
            }
            "yellow" -> {
                txtHeading.setTextColor(resources.getColor(R.color.yellow))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_yellow)
            }
            "darkYellow" -> {
                txtHeading.setTextColor(resources.getColor(R.color.darkYellow))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_yellow)
            }
            "green" -> {
                txtHeading.setTextColor(resources.getColor(R.color.green))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_green)
            }
            "darkGreen" -> {
                txtHeading.setTextColor(resources.getColor(R.color.darkGreen))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_green)
            }
            "lightGreen" -> {
                txtHeading.setTextColor(resources.getColor(R.color.lightGreen))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_light_green)
            }
            "newGreen" -> {
                txtHeading.setTextColor(resources.getColor(R.color.newGreen))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_new_green)
            }
            "blue" -> {
                txtHeading.setTextColor(resources.getColor(R.color.blue))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_blue)
            }
            "purple" -> {
                txtHeading.setTextColor(resources.getColor(R.color.purple))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_purple)
            }
            "darkPurple" -> {
                txtHeading.setTextColor(resources.getColor(R.color.darkPurple))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_purple)
            }
            "darkBlueNew" -> {
                txtHeading.setTextColor(resources.getColor(R.color.darkBlueNew))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_blue_new)
            }
            else -> {
                txtHeading.setTextColor(resources.getColor(R.color.darkBlueNew))
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_blue_new)
            }
        }
        // picker_ui_view.setVisibility(View.VISIBLE);
        dialog.show()
    }


    var percentFinal = 0.0
    val categories:HashMap<String, Double> = HashMap()
    private fun calculateGrade() {

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
            txtPercentage!!.text = "$percent%"
            percentFinal = percent
            txtGetPercentage!!.text = "$percent%"
            // Round it to two decimal places first
        } else {
            var resultGrade = 0.0
            var totalWeight = 1.0
            val categoryTotals:HashMap<String, ScorePair> = HashMap()
            for(key in categories.keys){
                val scorePair = ScorePair()
                scorePair.first=0.0
                scorePair.second=0.0
                categoryTotals[key]=scorePair
            }



            for (resultsBean in resultsBeans) {
                val isGraded = resultsBean.isIsGraded
                val score = resultsBean.score
                val maxScore = resultsBean.maxScore
                val  types = resultsBean.type
                if (isGraded) {
                    val total = categoryTotals[types]
                    total!!.first+=score
                    total!!.second+=maxScore
                }
            }

            val tempTotals:HashMap<String, ScorePair> = HashMap()
            tempTotals.putAll(categoryTotals)


            for(key in tempTotals.entries){
                if (tempTotals[key.key]!!.second == 0.0){
                    totalWeight-= categories.getValue(key.key) /100
                    categories.remove(key.key)
                }
            }


            var final: Double
            for(key in categoryTotals.keys){
                if (categories.containsKey(key)){
                    val weight=categories.getValue(key)/totalWeight
                    val totalUnweightedScore=categoryTotals[key]
                    val unweightedPercent= (totalUnweightedScore!!.first)/(totalUnweightedScore.second)
                    resultGrade+=weight*unweightedPercent
                }


            }
            final=(resultGrade*100)/100


            final = String.format("%.2f", final).toDouble()
            Log.d("resultnnnnnnn", "" + final)
            txtPercentage!!.text = "$final%"
            percentFinal = final
            txtGetPercentage!!.text = "$final%"


        }
    }

    var isDoingWeight = false

    var assignmentList: List<ClassesDetailModel.DBean.ResultsBean>? = null
    var tempBeans: MutableList<GradeBookDetailSummary.DBean.ResultsBean> = ArrayList()

    class ScorePair {
        var first = 0.0
        var second = 0.0
    }

    class Custom{
        var myKeys=""
    }

    private fun callGetClass(Url: String, jsonObject: JSONObject) {
        if (circularProgressBar != null && circularProgressBar!!.visibility == View.GONE) {
            circularProgressBar!!.visibility = View.VISIBLE
        }
        val cook = UserPreference.getInstance(this@DetailsofClass).cookie
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
                            classesDetailsAdapter!!.notifyDataSetChanged()
                        }
                        val Url = UserPreference.getInstance(this@DetailsofClass).baseUrl
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
                            if (circularProgressBar != null && circularProgressBar!!.visibility == View.VISIBLE) {
                                circularProgressBar!!.visibility = View.GONE
                            }
                            // received ANError from server
                            // error.getErrorCode() - the ANError code from server
                            // error.getErrorBody() - the ANError body from server
                            // error.getErrorDetail() - just a ANError detail
                            Log.d(TAG, "onError errorCode : " + anError.errorCode)
                            Toast.makeText(this@DetailsofClass, "Session Expire Login Again", Toast.LENGTH_SHORT).show()
                            UserPreference.getInstance(this@DetailsofClass).clearSession()
                            startActivity(Intent(this@DetailsofClass, LoginActivity::class.java))
                            finish()

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
        val cook = UserPreference.getInstance(this@DetailsofClass).cookie
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
                            calculateGrade()
                            txtMaxPercentage!!.text = "$percentFinal%"
                        }
                    }

                    override fun onError(anError: ANError) {
                        Log.d("Error", "" + anError.response)
                        if (anError.errorCode != 0) {
                            if (circularProgressBar != null && circularProgressBar!!.visibility == View.VISIBLE) {
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

    companion object {
        private const val TAG = "ClassesDetailActivity"

    }




}