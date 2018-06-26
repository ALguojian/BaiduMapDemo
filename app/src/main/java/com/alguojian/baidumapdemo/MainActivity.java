package com.alguojian.baidumapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alguojian.maplibrary.activity.CityActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        CityActivity.start(this);
        finish();
    }
}
