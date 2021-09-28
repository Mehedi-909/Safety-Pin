package com.example.otplogin;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceive";
    Vibrator vibrator;
    protected static ProcessBuilder mp;



    @Override
    public void onReceive(Context context, Intent intent) {

        final MediaPlayer mp = MediaPlayer.create(context, R.raw.alert);

        NotificationHelper notificationHelper = new NotificationHelper(context);
        //Log.d("tag", "What's wrong");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList) {
            //Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }

        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_LONG).show();

                Location location = geofencingEvent.getTriggeringLocation();
                //double latitude = location.getLatitude();
                //double longitude = location.getLongitude();
                long[] pattern = {0, 1000, 2000, 1000, 2000, 1000, 2000};
                Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
                vibrator.vibrate(pattern, -1); // does not repeat
                //vibrator.vibrate(pattern,  0); // repeats forever
                mp.start();

                break;

            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_LONG).show();
                Location location2 = geofencingEvent.getTriggeringLocation();
                //double latitude = location.getLatitude();
                //double longitude = location.getLongitude();
                long[] pattern2 = {0, 1000, 2000, 1000, 2000, 1000, 2000};
                Vibrator vibrator2 = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
                vibrator2.vibrate(pattern2, -1); // does not repeat
                //vibrator.vibrate(pattern,  0); // repeats forever
                mp.start();
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(context, "GEOFENCE_TRANSITION_NOT_WORKING", Toast.LENGTH_LONG).show();
        }

    }



}