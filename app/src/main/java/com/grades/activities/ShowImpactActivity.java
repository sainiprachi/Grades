package com.grades.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.app.grades.R;
import com.grades.adapters.ClassesDetailsAdapter;
import com.grades.utils.UserPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.blackbox_vision.wheelview.view.WheelView;

public class ShowImpactActivity extends AppCompatActivity implements ClassesDetailsAdapter.OnClickParent, View.OnClickListener {
    RelativeLayout rlMock;
    View viewMock;
    LinearLayout llPercentage;
    TextView txtAddMock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_detail);
        initView();
    }

    RecyclerView recyclerClassesDetail;

    private void initView(){
        recyclerClassesDetail=findViewById(R.id.recyclerClassesDetail);
        recyclerClassesDetail.setLayoutManager(new LinearLayoutManager(this));
        viewMock=findViewById(R.id.viewMock);
        llPercentage=findViewById(R.id.llPercentage);
       // recyclerClassesDetail.setAdapter(new ClassesDetailsAdapter(ShowImpactActivity.this,this,false));
        rlMock=findViewById(R.id.rlMock);
        TextView txtPercentage=findViewById(R.id.txtPercentage);
        RelativeLayout llParent=findViewById(R.id.llParent);
        TextView txtSubject=findViewById(R.id.txtSubject);
        ImageView ivClose=findViewById(R.id.ivClose);
        TextView txtAddMock=findViewById(R.id.txtAddMock);
        ImageView ivCalculator=findViewById(R.id.ivCalculator);
        ImageView ivMenu=findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        ivCalculator.setOnClickListener(this);
        txtAddMock.setOnClickListener(this);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(this.getResources().getColor(android.R.color.transparent, this.getTheme()));
            } else {
                getWindow().setStatusBarColor(this.getResources().getColor(android.R.color.transparent));
            }
        }*/

        String theme = UserPreference.getInstance(ShowImpactActivity.this).getTheme();
        switch (theme) {
            case "Red":
                // setTheme(R.style.red);
                txtPercentage.setTextColor(getResources().getColor(R.color.red));
                txtSubject.setTextColor(getResources().getColor(R.color.gray));
                llParent.setBackgroundColor(getResources().getColor(R.color.white));
                DrawableCompat.setTint(
                        DrawableCompat.wrap(ivClose.getDrawable()),
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkRed)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkRed)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkRed)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkOrange)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkOrange)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkOrange)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkYellow)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkYellow)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkYellow)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkGreen)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkGreen)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkGreen)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.newGreen)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.newGreen)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.newGreen)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkgray)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkPurple)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkPurple)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkPurple)
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
                        ContextCompat.getColor(ShowImpactActivity.this, R.color.darkBlueNew)
                );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivCalculator.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkBlueNew)
            );DrawableCompat.setTint(
                    DrawableCompat.wrap(ivMenu.getDrawable()),
                    ContextCompat.getColor(ShowImpactActivity.this, R.color.darkBlueNew)
            );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlueNew));
                }


        }

    }




    @Override
    public void onClickItem(int position) {

    }

    @Override
    public void onSwipeSeekBar(int position) {

    }

    public interface OnClickCalculator{
        void OnClick();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                String theme = UserPreference.getInstance(ShowImpactActivity.this).getTheme();
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
                //recyclerClassesDetail.setAdapter(new ClassesDetailsAdapter(ShowImpactActivity.this,this,true));


                break;

        }
    }


    public void populateViewForDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        String theme = UserPreference.getInstance(ShowImpactActivity.this).getTheme();
        View popupView = inflater.inflate(R.layout.popup_menu_layout, null);
        PopupWindow pw = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        CardView cardParent=popupView.findViewById(R.id.cardParent);
        TextView txtBreakDown=popupView.findViewById(R.id.txtBreakDown);
        txtBreakDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowImpactActivity.this,BreakDownActivity.class));
            }
        });
        pw.showAtLocation(this.findViewById(R.id.ivMenu), Gravity.TOP|Gravity.RIGHT, 20, 120);
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

    private void addMockAssignment(){
        List<String> options=new ArrayList<>();
        final Dialog dialog = new Dialog(this,R.style.PopupDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_mock_dialog);
        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height =WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        WheelView picker_ui_view=dialog.findViewById(R.id.loop_view);
        Button btnAddAssignment=dialog.findViewById(R.id.btnAddAssignment);
        TextView txtHeading=dialog.findViewById(R.id.txtHeading);
        ImageView ivCloseDialog=dialog.findViewById(R.id.ivCloseDialog);
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        picker_ui_view.setInitPosition(2);
        options.add("Homework/classesWork/projects/20%");
        options.add("Homework/classesWork/projects/20%");
        options.add("Homework/classesWork/projects/20%");
        options.add("Homework/classesWork/projects/20%");
        options.add("Homework/classesWork/projects/20%");
        picker_ui_view.setCanLoop(false);
        picker_ui_view.setLoopListener(item -> {

        });
        picker_ui_view.setTextSize(14);
        picker_ui_view.setItems(options);
        String theme = UserPreference.getInstance(ShowImpactActivity.this).getTheme();
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
                btnAddAssignment.setBackgroundResource(R.drawable.shape_dark_blue_new                   );


        }


        dialog.show();



    }



}