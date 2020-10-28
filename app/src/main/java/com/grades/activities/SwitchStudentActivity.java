package com.grades.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.app.grades.R;
import com.grades.roomdatabase.AppDataBase;
import com.grades.roomdatabase.model.StudentsOfCurrentAccountModelDb;
import com.grades.utils.UserPreference;

public class SwitchStudentActivity extends AppCompatActivity {
    private AppDataBase db;
    private Handler handler=new Handler(Looper.myLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_student);
        initView();
    }

    private void initView(){
        TextView txtSave=findViewById(R.id.txtSave);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "gradedDb").build();

        TextView txtStudentNAme=findViewById(R.id.txtStudentNAme);
        TextView txtUniversity=findViewById(R.id.txtUniversity);
        new Thread(() -> {
            StudentsOfCurrentAccountModelDb studentOFCurrentAccountModel=db.saveDataDao().studentOfCurrent();
            handler.post(() -> {
                 txtStudentNAme.setText(studentOFCurrentAccountModel.getFirstName()+" "+studentOFCurrentAccountModel.getMiddleName()+" "+studentOFCurrentAccountModel.getLastName());
                 txtUniversity.setText("");
            });



        }).start();

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Toolbar ToolBar=findViewById(R.id.ToolBar);
        String theme = UserPreference.getInstance(SwitchStudentActivity.this).getTheme();
        switch (theme) {
            case "Red":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.red));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.red));
                }

                txtUniversity.setTextColor(getResources().getColor(R.color.red));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.red));


                break;

            case "darkRed":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkRed));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkRed));
                }

                txtUniversity.setTextColor(getResources().getColor(R.color.darkRed));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.darkRed));

                break;


            case "orange":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.orange));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.orange));
                }



                break;

            case "darkOrange":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkOrange));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkOrange));
                }

                txtUniversity.setTextColor(getResources().getColor(R.color.darkOrange));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.darkOrange));

                break;


            case "yellow":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.yellow));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.yellow));
                }

                txtUniversity.setTextColor(getResources().getColor(R.color.yellow));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.yellow));


                break;

            case "darkYellow":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkYellow));
                }

                txtUniversity.setTextColor(getResources().getColor(R.color.darkYellow));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.darkYellow));

                break;

            case "green":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.green));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.green));
                }


                break;


            case "darkGreen":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkGreen));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkGreen));
                }

                txtUniversity.setTextColor(getResources().getColor(R.color.darkGreen));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.darkGreen));

                break;

            case "lightGreen":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.lightGreen));
                }
                txtUniversity.setTextColor(getResources().getColor(R.color.lightGreen));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.lightGreen));

                break;

            case "newGreen":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.newGreen));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.newGreen));
                }
                txtUniversity.setTextColor(getResources().getColor(R.color.newGreen));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.newGreen));

                break;


            case "blue":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.blue));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
                }
                txtUniversity.setTextColor(getResources().getColor(R.color.blue));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.blue));

                break;

            case "darkBlueNew":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkBlueNew));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlueNew));
                }
                txtUniversity.setTextColor(getResources().getColor(R.color.darkBlueNew));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.darkBlueNew));


                break;

            case "purple":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.purple));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.purple));
                }
                txtUniversity.setTextColor(getResources().getColor(R.color.purple));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.purple));

                break;

            case "darkPurple":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkPurple));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkPurple));
                }
                txtUniversity.setTextColor(getResources().getColor(R.color.darkPurple));
                txtStudentNAme.setTextColor(getResources().getColor(R.color.darkPurple));


                break;
        }




    }

}