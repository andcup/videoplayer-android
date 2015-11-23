package com.nd.hy.android.video.app;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, StartActivity.class);
        startActivity(intent);
    }
}
