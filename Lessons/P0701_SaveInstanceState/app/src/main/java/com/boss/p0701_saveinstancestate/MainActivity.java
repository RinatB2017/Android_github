package com.boss.p0701_saveinstancestate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

// https://startandroid.ru/ru/uroki/vse-uroki-spiskom/133-urok-70-onsaveinstancestate-sohranenie-dannyh-activity-pri-povorote-ekrana.html

public class MainActivity extends Activity {

    final String LOG_TAG = "myLogs";
    int cnt = 0;

    TextView logView;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "onCreate");

        logView = (TextView)findViewById(R.id.logView);
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    //---
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", cnt);
        outState.putString("log", logView.getText().toString());
        Log.i(LOG_TAG, "onSaveInstanceState");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cnt = savedInstanceState.getInt("count");
        logView.setText(savedInstanceState.getString("log"));
        Log.i(LOG_TAG, "onRestoreInstanceState");
    }
    //---

    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume ");
    }

    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    public void onclick(View v) {
        Toast.makeText(this, "Count = " + ++cnt, Toast.LENGTH_SHORT).show();
        logView.append("count = " + cnt + "\n");
    }
}
