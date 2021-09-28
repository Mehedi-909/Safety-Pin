package com.example.otplogin;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;

    Button review, emergencyDial, signOut,loc;
    ListView listView;
    DatabaseReference databaseReference;


    private float GEOFENCE_RADIUS = 100;
    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;


    ArrayList<Coordinate>[] colorMap;
    ArrayList<Coordinate> locations = new ArrayList<>();
    ArrayList<Coordinate> tempLocations = new ArrayList<>();
    ArrayList<Coordinate> tempLocationsToRemove = new ArrayList<>();

    ArrayList<LatLng> testLatLng = new ArrayList<>();
    ArrayList<String> arrayListm;

    private LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;

    private LocationRequest locationRequest;
    DatabaseReference reference;
    String categoryC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Trusted Contact");

        arrayListm=new ArrayList<>();

        readData(new Test.MyCallback() {
            @Override
            public void onCallback(String value) {

                String total=null;
                String [] values2;
                for(String s : arrayListm){

                    values2 = s.split(",");
                    LatLng test = new LatLng(Double.parseDouble(values2[0]), Double.parseDouble(values2[1]));
                    Coordinate co = new Coordinate(Double.parseDouble(values2[0]), Double.parseDouble(values2[1]),values2[2]);
                    locations.add(co);
                    //String categoryOfCrime = values2[2];
                    testLatLng.add(test);
                }
                int a=0;

                Coordinate store = null;
                for(Coordinate temp : locations){

                    for (Coordinate tempInner : locations){
                        double distance = distance(temp,tempInner);
                        if( (distance != 0) && distance < 0.0011551 ){
                            double averageLat = (temp.getLatitude() + tempInner.getLatitude()) / 2.00;
                            double averageLng = (temp.getLongitude() + tempInner.getLongitude()) / 2.00;
                            String category = temp.getCategory() + tempInner.getCategory();
                            Coordinate tempCoordinate = new Coordinate(averageLat,averageLng,category);
                            tempLocations.add(tempCoordinate);
                            tempLocationsToRemove.add(tempInner);
                            tempLocationsToRemove.add(temp);


                        }
                    }

                }

//                for(Coordinate temp : tempLocationsToRemove){
//                    locations.remove(temp);
//
//                }

                for(Coordinate temp : tempLocations){
                    //locations.add(temp);
                    String ct = temp.getCategory();
                    categoryC = ct;
                    LatLng lt = new LatLng(temp.getLatitude(),temp.getLongitude());
                    addMultipleGeofenceForMergedLocation(lt,categoryC);

                }

                for(Coordinate temp : locations){

                    String ct = temp.getCategory();
                    categoryC = ct;
                    LatLng lt = new LatLng(temp.getLatitude(),temp.getLongitude());
                    addMultipleGeofence(lt,categoryC);

                }


            }
        });

        loc = findViewById(R.id.call999);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneN = "tel:" + "999";
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse(phoneN));
                startActivity(call);

            }
        });





        review = findViewById(R.id.reviewButton);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(MapsActivity.this, ManageDatabase.class);
                startActivity(send);

            }
        });

        emergencyDial = findViewById(R.id.emergency);
        emergencyDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(MapsActivity.this, EmergencyContact.class);
                startActivity(send);

            }
        });

        signOut = findViewById(R.id.logOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent logout = new Intent(MapsActivity.this, SignUp.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
                finish();

                Intent send = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(send);

            }
        });


    }

    public double distance (Coordinate c1, Coordinate c2)
    {
        return Math.sqrt(
                Math.pow(c1.getLatitude() - c2.getLatitude(),2)+
                        Math.pow(c1.getLongitude() - c2.getLongitude(),2));
    }





    private void readData(Test.MyCallback firebaseCallBack){

        reference = FirebaseDatabase.getInstance().getReference().child("Locations");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] values2;
                String m = null;
                for(DataSnapshot dataSnapShot : snapshot.getChildren() ){
                    //C value = dataSnapShot.getValue();
                    //String value = String.valueOf(dataSnapShot);
                    //count = dataSnapShot.getChildrenCount();

                    String value = dataSnapShot.getValue().toString();
                    m= value;
;
                    arrayListm.add(value);

                }

                firebaseCallBack.onCallback(m);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public interface MyCallback {
        void onCallback(String value);
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }
    LatLng locationR;
    private void getCurrentLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(MapsActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(MapsActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        //AddressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
                                        String print = String.valueOf(latitude) + " "+ String.valueOf(longitude);
                                        locationR = new LatLng(latitude, longitude);

                                        Toast.makeText(MapsActivity.this,print , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


    }

    private void turnOnGPS() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(MapsActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MapsActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(MapsActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(MapsActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                       LatLng current = new LatLng(latitude,longitude);
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 16));

                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }



        enableUserLocation();

        mMap.setOnMapLongClickListener(this);

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);






    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }






    @Override
    public void onMapLongClick(LatLng latLng) {

    }


    private void handleMapLongClick(LatLng latLng) {

    }





    private void addGeofence(LatLng latLng, float radius) {

        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Geofence Added...");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: " + errorMessage);
                    }
                });
    }


    private void addMarker(LatLng latLng,String category) {

        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        if( (category.equals("Eve-teasing")) || category.equals("Harassment") ){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        }
        else if( (category.equals("Snatching")) || category.equals("Plunder") ){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        }
        else{
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }


        mMap.addMarker(markerOptions);
    }



    private void addCircle(LatLng latLng, float radius,String category) {

        double lat = latLng.latitude;
        double lng = latLng.longitude;
        int intVal1 = (int) Math.floor(lat);
        int intVal2 = (int) Math.floor(lng);

        if( (category.equals("Eve-teasing")) || category.equals("Harassment") ){
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(latLng);
            circleOptions.radius(radius);
            circleOptions.strokeColor(Color.argb(255, 227, 252,3));
            circleOptions.fillColor(Color.argb(64, 227, 252,3));
            circleOptions.strokeWidth(4);
            mMap.addCircle(circleOptions);
        }
        else if( (category.equals("Snatching")) || category.equals("Plunder") ){
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(latLng);
            circleOptions.radius(radius);
            circleOptions.strokeColor(Color.argb(255, 252, 157,3));
            circleOptions.fillColor(Color.argb(64, 252, 157,3));
            circleOptions.strokeWidth(4);
            mMap.addCircle(circleOptions);

        }
        else{
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(latLng);
            circleOptions.radius(radius);
            circleOptions.strokeColor(Color.argb(255, 255, 0,0));
            circleOptions.fillColor(Color.argb(64, 255, 0,0));
            //circleOptions.fillColor(Color.argb(64, 255, 0,0));
            circleOptions.strokeWidth(4);
            mMap.addCircle(circleOptions);

        }


    }

    public void addMultipleGeofence(LatLng test,String category){

        addMarker(test,category);
        addCircle(test, GEOFENCE_RADIUS,category);
        addGeofence(test, GEOFENCE_RADIUS);

    }

    public void addMultipleGeofenceForMergedLocation(LatLng test,String category){

        addMarker(test,category);
        addCircle(test, GEOFENCE_RADIUS+50,category);
        addGeofence(test, GEOFENCE_RADIUS+50);

    }


}

