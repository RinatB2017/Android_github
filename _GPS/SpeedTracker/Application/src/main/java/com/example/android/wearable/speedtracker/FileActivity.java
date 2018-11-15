package com.example.android.wearable.speedtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
    }

    public void go_main(View view) {
        Intent intent = new Intent(this, PhoneMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent != null) {
            startActivity(intent);
        }
    }
}
