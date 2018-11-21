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
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * The main activity for the handset application. When a watch device reconnects to the handset
 * app, the collected GPS data on the watch, if any, is synced up and user can see his/her track on
 * a map. This data is then saved into an internal database and the corresponding data items are
 * deleted.
 */
public class PhoneMainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "PhoneMainActivity";
    private GoogleMap gMap;
    private SupportMapFragment mMapFragment;

    //TODO тест
    private List<LatLng> places = new ArrayList<>();
    private String mapsApiKey;
    private int width;
    final int DEFAULT_ZOOM = 15;
    String path;
    String filename;
    //---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        path = "/storage/emulated/0/Android/data/com.mendhak.gpslogger/files/";

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        mapsApiKey = this.getResources().getString(R.string.map_v2_api_key);

        width = getResources().getDisplayMetrics().widthPixels;

        //---
        Intent intent = getIntent();
        if(intent != null) {
            filename = intent.getStringExtra("filename");
            if (filename != null) {
                if (!filename.isEmpty()) {
                    File gpxFile = new File(filename);

                    List<Location> gpxList = decodeGPX(gpxFile);

                    places.clear();
                    for (int i = 0; i < gpxList.size(); i++) {
                        places.add(new LatLng(gpxList.get(i).getLatitude(), gpxList.get(i).getLongitude()));
                    }
                }
            }
        }
        //---
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_file:
                Intent intent = new Intent(this, FileActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        send_log("type " + gMap.getMapType());

        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //TODO тест
        if(places.size() > 0) {
            MarkerOptions[] markers = new MarkerOptions[places.size()];
            for (int i = 0; i < places.size(); i++) {
                markers[i] = new MarkerOptions()
                        .position(places.get(i));
                gMap.addMarker(markers[i]);
            }

            if(filename != null) {
                File gpxFile = new File(filename);
                List<Location> gpxList = decodeGPX(gpxFile);
                if(gpxList != null) {
                    LatLng currentLatLng = new LatLng(gpxList.get(0).getLatitude(), gpxList.get(0).getLongitude());
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM);
                    googleMap.moveCamera(update);
                }
            }
        }
        //---
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

    public void test() {
        send_log("test");

        if(!isExternalStorageReadable()) {
            send_log("ERROR: external storage no readable");
            return;
        }

        String path = "/storage/emulated/0/Android/data/com.mendhak.gpslogger/files/20181011.gpx";
        File gpxFile = new File(path);

        List<Location> gpxList = decodeGPX(gpxFile);

        places.clear();
        for(int i = 0; i < gpxList.size(); i++){
            places.add(new LatLng(gpxList.get(i).getLatitude(), gpxList.get(i).getLongitude()));
        }
        MarkerOptions[] markers = new MarkerOptions[places.size()];
        for (int i = 0; i < places.size(); i++) {
            markers[i] = new MarkerOptions()
                    .position(places.get(i));
            gMap.addMarker(markers[i]);
        }

        LatLng currentLatLng = new LatLng(gpxList.get(0).getLatitude(), gpxList.get(0).getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM);
        gMap.moveCamera(update);
    }

    private List<Location> decodeGPX(File file){
        List<Location> list = new ArrayList<Location>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            FileInputStream fileInputStream = new FileInputStream(file);
            Document document = documentBuilder.parse(fileInputStream);
            Element elementRoot = document.getDocumentElement();

            NodeList nodelist_trkpt = elementRoot.getElementsByTagName("trkpt");

            for(int i = 0; i < nodelist_trkpt.getLength(); i++){

                Node node = nodelist_trkpt.item(i);
                NamedNodeMap attributes = node.getAttributes();

                String newLatitude = attributes.getNamedItem("lat").getTextContent();
                Double newLatitude_double = Double.parseDouble(newLatitude);

                String newLongitude = attributes.getNamedItem("lon").getTextContent();
                Double newLongitude_double = Double.parseDouble(newLongitude);

                String newLocationName = newLatitude + ":" + newLongitude;
                Location newLocation = new Location(newLocationName);
                newLocation.setLatitude(newLatitude_double);
                newLocation.setLongitude(newLongitude_double);

                list.add(newLocation);

            }
            fileInputStream.close();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public void send_log(String text) {
        Log.i("States", text);
    }
}
