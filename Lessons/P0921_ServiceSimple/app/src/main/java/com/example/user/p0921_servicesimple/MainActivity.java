package com.example.user.p0921_servicesimple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

// https://startandroid.ru/ru/uroki/vse-uroki-spiskom/157-urok-92-service-prostoj-primer.html

public class MainActivity extends Activity {

    final String LOG_TAG = "States";
    TextView logView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logView = (TextView) findViewById(R.id.logView);
    }

    public void onClickStart(View v) {
        logView.append("onClickStart" + "\n");
        startService(new Intent(this, MyService.class));
    }

    public void onClickStop(View v) {
        logView.append("onClickStop" + "\n");
        stopService(new Intent(this, MyService.class));
    }
}
