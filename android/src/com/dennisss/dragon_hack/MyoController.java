package com.example.hacker.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.scanner.ScanActivity;

/**
 * Created by hacker on 1/10/15.
 */

/*
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Hub hub = Hub.getInstance();
        if (!hub.init(this)) {
            Log.e("", "Could not initialize the Hub.");
            finish();
            return;
        }
        MyoController controller = new MyoController(getApplicationContext());
        controller.init();
        controller.getAngle();
 */



public class MyoController {
    private Context mContext;
    float currentPitch;
    public MyoController(Context context)
    {
        this.mContext = context;
    }

    private DeviceListener mListener = new AbstractDeviceListener() {
        @Override
        public void onConnect(Myo myo, long timestamp) {
            //Toast.makeText(getApplicationContext(), "Myo Connected!", Toast.LENGTH_SHORT).show();
            Log.e("POSE", "connected");
        }

        @Override
        public void onDisconnect(Myo myo, long timestamp) {
            // Toast.makeText(getApplicationContext(), "Myo Disconnected!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPose(Myo myo, long timestamp, Pose pose) {
            //Toast.makeText(getApplicationContext(), "Pose: " + pose, Toast.LENGTH_SHORT).show();
            //  Log.e("POSE",pose.toString());
            myo.unlock(Myo.UnlockType.HOLD);
            //TODO: Do something awesome.
        }

        @Override
        public void onUnlock(Myo myo, long timestamp) {
            Log.e("POSE","unlocked");
        }

        @Override
        public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {

            float pitch = (float) Math.toDegrees(Quaternion.pitch(rotation));
// Adjust roll and pitch for the orientation of the Myo on the arm.

            pitch = 2*(pitch/70);
            pitch+=1;

            if(pitch>1 && pitch <2)
            {
                pitch = 1;
            }
            else if(pitch <-1)
            {
                pitch = -1;
            }
            else if((pitch >-.3 && pitch<.3)|| pitch <-2 || pitch >2)
            {
                pitch = 0;
            }

            //Log.e("Pitch",Float.toString(pitch));
            currentPitch = pitch;
// Next, we apply a rotation to the text view using the roll, pitch, and yaw.

        }
        // onLock() is called whenever a synced Myo has been locked. Under the standard locking
// policy, that means poses will no longer be delivered to the listener.
        @Override
        public void onLock(Myo myo, long timestamp) {
            Log.e("POSE","locked");
            // myo.unlock(Myo.UnlockType.HOLD);
        }
    };

    public void init()
    {

        //Hub.getInstance().attachToAdjacentMyo();
        Intent intent = new Intent(mContext, ScanActivity.class);
        mContext.startActivity(intent);
        Hub.getInstance().addListener(mListener);
        Hub.getInstance().setSendUsageData(false);
    }

    public float getAngle()
    {
        return currentPitch;
    }

}
