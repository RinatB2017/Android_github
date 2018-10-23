package com.jmlb0003.samplear;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;


/**
 * This is a sample use of AndARActivity.
 * Here we initialize some objects for traking their markers and painting boxes over them.
 * <p>
 * In Manifest the orientation has to be set to landscape, as needed by AndAR. If you don't include
 * it in manifest, use setOrientation() in all AndARActivities
 *
 * @see edu.dhbw.andar.AndARActivity
 */
public class MainActivity extends AndARActivity {

    private ARToolkit mArtoolkit;
    private ARObject mObject;

    private void requestSmsPermission(String permission) {
        //String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestSmsPermission(Manifest.permission.CAMERA);

        /**
         * In CustomRender we have set up lights and anything else nonAR-stuff
         */
        CustomRenderer renderer = new CustomRenderer();
        /**
         * Set a renderer that draws non AR stuff. Optional, may be set to null or omited.
         * and sets up lighting stuff.
         */
        setNonARRenderer(renderer);

        try {
            mArtoolkit = getArtoolkit();

            mObject = new CustomObject1("test", "marker_at16.patt", 80.0, new double[]{0, 0});
            mArtoolkit.registerARObject(mObject);

            mObject = new CustomObject2("test", "marker_peace16.patt", 80.0, new double[]{0, 0});
            mArtoolkit.registerARObject(mObject);

            mObject = new CustomObject3("test", "marker_rupee16.patt", 80.0, new double[]{0, 0});
            mArtoolkit.registerARObject(mObject);

            mObject = new CustomObject4("test", "marker_hand16.patt", 80.0, new double[]{0, 0});
            mArtoolkit.registerARObject(mObject);

        } catch (AndARException ignored) {
        }

        /**
         * Open the camera and start detecting markers.
         * note: You must assure that the preview surface already exists!
         */
        startPreview();
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("AndAR EXCEPTION", ex.getMessage());
        finish();
    }
}