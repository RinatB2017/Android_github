/*
 * Copyright (C) 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.wearable.speedtracker;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.android.wearable.speedtracker.common.LocationEntry;
import com.google.maps.GeoApiContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The main activity for the handset application. When a watch device reconnects to the handset
 * app, the collected GPS data on the watch, if any, is synced up and user can see his/her track on
 * a map. This data is then saved into an internal database and the corresponding data items are
 * deleted.
 */
public class PhoneMainActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, OnMapReadyCallback {

    private static final String TAG = "PhoneMainActivity";
    private static final int BOUNDING_BOX_PADDING_PX = 50;
    private TextView mSelectedDateText;
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;

    //TODO тест
    private static final int RECORD_REQUEST_CODE = 101;

    private List<LatLng> places = new ArrayList<>();
    private String mapsApiKey;
    private int width;
    final int DEFAULT_ZOOM = 12; //15;
    //---

    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mSelectedDateText = (TextView) findViewById(R.id.selected_date);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        //TODO тест
        places.add(new LatLng(55.754724, 37.621380));
        places.add(new LatLng(55.760133, 37.618697));
        places.add(new LatLng(55.764753, 37.591313));
        places.add(new LatLng(55.728466, 37.604155));

        mapsApiKey = this.getResources().getString(R.string.map_v2_api_key);

        width = getResources().getDisplayMetrics().widthPixels;

        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, RECORD_REQUEST_CODE);
        //---
    }

    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        new DatePickerDialog(PhoneMainActivity.this, PhoneMainActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // the following if-clause is to get around a bug that causes this callback to be called
        // twice
        if (view.isShown()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String date = DateUtils.formatDateTime(this, calendar.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE);
            mSelectedDateText.setText(getString(R.string.showing_for_date, date));
            showTrack(calendar);
        }
    }

    /**
     * An {@link android.os.AsyncTask} that is responsible for getting a list of {@link
     * com.example.android.wearable.speedtracker.common.LocationEntry} objects for a given day and
     * building a track from those points. In addition, it sets the smallest bounding box for the
     * map that covers all the points on the track.
     */
    private void showTrack(Calendar calendar) {
        new AsyncTask<Calendar, Void, Void>() {

            private List<LatLng> coordinates;
            private LatLngBounds bounds;

            @Override
            protected Void doInBackground(Calendar... params) {
                LocationDataManager dataManager = ((PhoneApplication) getApplicationContext())
                        .getDataManager();
                List<LocationEntry> entries = dataManager.getPoints(params[0]);
                if (entries != null && !entries.isEmpty()) {
                    coordinates = new ArrayList<LatLng>();
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LocationEntry entry : entries) {
                        LatLng latLng = new LatLng(entry.latitude, entry.longitude);
                        builder.include(latLng);
                        coordinates.add(latLng);
                    }
                    bounds = builder.build();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mMap.clear();
                if (coordinates == null || coordinates.isEmpty()) {
                    if (Log.isLoggable(TAG, Log.DEBUG)) {
                        Log.d(TAG, "No Entries found for that date");
                    }
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,
                            BOUNDING_BOX_PADDING_PX));
                    mMap.addPolyline(new PolylineOptions().geodesic(true).addAll(coordinates));
                }
            }

        }.execute(calendar);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //TODO тест
        MarkerOptions[] markers = new MarkerOptions[places.size()];
        for (int i = 0; i < places.size(); i++) {
            markers[i] = new MarkerOptions()
                    .position(places.get(i));
            googleMap.addMarker(markers[i]);
        }

        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(mapsApiKey)
                .build();

        /*
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, width, width, 25);
        googleMap.moveCamera(track);
        */

        LatLng currentLatLng = new LatLng(55.754724, 37.621380);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM);
        googleMap.moveCamera(update);
        //---
    }

    /* Проверяет, доступно ли external storage как минимум для чтения */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void onTest(View view) {
        send_log("test");

        if(!isExternalStorageReadable()) {
            send_log("ERROR: external storage no readable");
            return;
        }

        File file = new File("/storage/emulated/0/Android/data/com.mendhak.gpslogger/files");
        list_files(file);
    }

    public void send_log(String text) {
        Log.i("States", text);
    }

    private void list_files(File path) {
        if (path == null) {
            send_log("path == null");
            return;
        }

        File[] l_files = path.listFiles();
        if (l_files == null) {
            send_log("l_files == null");
            return;
        }
        for (int n = 0; n < l_files.length; n++) {
            if (l_files[n].isDirectory()) {
                list_files(l_files[n]);
            } else {
                send_log("   file: " + l_files[n].getName() + " size: " + l_files[n].length());
            }
        }
    }
}
