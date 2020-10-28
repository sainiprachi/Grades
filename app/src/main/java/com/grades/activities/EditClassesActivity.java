package com.grades.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.app.grades.R;
import com.grades.adapters.ClassesAdapter;
import com.grades.adapters.EditClassesAdapter;
import com.grades.interfaces.CallbackItemTouch;
import com.grades.roomdatabase.AppDataBase;
import com.grades.roomdatabase.model.ClassModel;
import com.grades.roomdatabase.model.StudentsOfCurrentAccountModelDb;
import com.grades.utils.MyItemTouchHelperCallback;
import com.grades.utils.UserPreference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditClassesActivity extends AppCompatActivity implements CallbackItemTouch,ClassesAdapter.OnClickParent,View.OnClickListener,EditClassesAdapter.onCLickView{
    Handler handler=new Handler(Looper.myLooper());
    List<ClassModel> getClassesList = new ArrayList<>();
    EditClassesAdapter classesAdapter;
    private AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_classes_activtiy);
        initView();
    }

    private void initView(){
        TextView txtSave=findViewById(R.id.txtSave);
        txtSave.setOnClickListener(this);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "gradedDb").build();
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Toolbar ToolBar=findViewById(R.id.ToolBar);
        String theme = UserPreference.getInstance(EditClassesActivity.this).getTheme();
        classesAdapter=new EditClassesAdapter(this,getClassesList,this);
        RecyclerView recyclerClasses=findViewById(R.id.recyclerClasses);
        recyclerClasses.setAdapter(classesAdapter);
        recyclerClasses.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);// create MyItemTouchHelperCallback
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback); // Create ItemTouchHelper and pass with parameter the MyItemTouchHelperCallback
        touchHelper.attachToRecyclerView(recyclerClasses); // Attach ItemTouchHelper to RecyclerView
        switch (theme) {
            case "Red":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.red));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.red));
                }


                break;

            case "darkRed":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkRed));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkRed));
                }
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
                break;


            case "yellow":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.yellow));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.yellow));
                }

                break;

            case "darkYellow":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkYellow));
                }
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
                break;

            case "lightGreen":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.lightGreen));
                }
                break;

            case "newGreen":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.newGreen));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.newGreen));
                }
                break;


            case "blue":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.blue));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
                }
                break;

            case "darkBlueNew":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkBlueNew));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlueNew));
                }

                break;

            case "purple":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.purple));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.purple));
                }                       break;

            case "darkPurple":
                ToolBar.setBackgroundColor(getResources().getColor(R.color.darkPurple));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.darkPurple));
                }

                break;
        }
        new Thread(() -> {
            StudentsOfCurrentAccountModelDb studentOFCurrentAccountModel=db.saveDataDao().studentOfCurrent();
            List<ClassModel> classModelList = db.saveDataDao().getClasses(studentOFCurrentAccountModel.getStudentNumber());
            handler.post(() -> {
                if (classModelList.size()>0){
                    getClassesList.clear();
                    getClassesList.addAll(classModelList);
                    classesAdapter.notifyDataSetChanged();

                }
            });



        }).start();



    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        getClassesList.add(newPosition,getClassesList.remove(oldPosition));// change position
        classesAdapter.notifyItemMoved(oldPosition, newPosition); //notifies changes in adapter, in this case use the notifyItemMoved

    }

    @Override
    public void OnClickPosition(int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtSave:
                Intent intent=new Intent(EditClassesActivity.this,HomeActivity.class);
                intent.putExtra("getClass", (Serializable) getClassesList);
                startActivity(intent);

                break;
        }
    }

    @Override
    public void getOnCLickPos(int position) {
        getClassesList.get(position).setSelectAlpha(true);
    }
}