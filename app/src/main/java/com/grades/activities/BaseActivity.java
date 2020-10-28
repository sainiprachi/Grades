package com.grades.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.grades.R;
import com.grades.utils.UserPreference;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String currentTheme= UserPreference.getInstance(this).getTheme();
        switch (currentTheme){
            case "Red":
                setTheme(R.style.red);
                break;

            case "darkRed":
                setTheme(R.style.darkRed);

                break;


            case "orange":
                setTheme(R.style.orange);

                break;

            case "darkOrange":
                setTheme(R.style.darkOrange);
                break;


            case "yellow":
                setTheme(R.style.yellow);

                break;

            case "darkYellow":
                setTheme(R.style.darkYellow);
                break;

            case "green":
                setTheme(R.style.green);

                break;


            case "darkGreen":
                setTheme(R.style.darkGreen);
                break;

            case "lightGreen":
                setTheme(R.style.lightGreen);
                break;

            case "newGreen":
                setTheme(R.style.newGreen);
                break;


            case "blue":
                setTheme(R.style.blue);
                break;

            case "darkBlueNew":
                setTheme(R.style.darkBlueNew);

                break;

            case "purple":
                setTheme(R.style.purple);
                break;

            case "darkPurple":
                setTheme(R.style.darkPurple);

                break;
        }

        super.onCreate(savedInstanceState);

    }
}