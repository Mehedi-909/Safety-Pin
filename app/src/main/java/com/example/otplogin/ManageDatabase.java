package com.example.otplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ManageDatabase extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText age;
    //UserDB userdb;
    RadioButton maleB, femaleB, othersB;
    Spinner spinner;
    Button reviewSubmit,nextButton;

    EditText etPlace;
    Button buttonPicker;
    TextView tvAddress;
    String location;

    //UserDB userdb;
    DatabaseReference databaseReference,databaseReference2;
    long maxid = 0;
    long maxid2 = 0;

    DatePicker datePicker;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_database);

        //name = findViewById(R.id.reviewName);
        age = findViewById(R.id.reviewAge);

        maleB = findViewById(R.id.reviewMale);
        femaleB = findViewById(R.id.reviewFemale);
        othersB = findViewById(R.id.reviewOthers);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        etPlace = findViewById(R.id.et_place);
        buttonPicker = findViewById(R.id.bt_picker);
        tvAddress = findViewById(R.id.text_view);

        databaseReference = FirebaseDatabase.getInstance().getReference("Review");

        databaseReference2 = FirebaseDatabase.getInstance().getReference("Locations");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = snapshot.getChildrenCount();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        spinner = findViewById(R.id.crimeCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.crime, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        nextButton = findViewById(R.id.next);

        buttonPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = etPlace.getText().toString();
                GeoLocation geoLocation = new GeoLocation();
                geoLocation.getAddress(address, getApplicationContext(), new GeoHandler());
            }

        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                //Toast.makeText(getApplicationContext(),spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(ManageDatabase.this, "Review added successfully.", Toast.LENGTH_LONG).show();
                Intent send2 = new Intent(ManageDatabase.this, MapsActivity.class);
                //send2.putExtra("arg", location);
                startActivity(send2);
            }
        });

    }

    public void saveData() {

        //String nameS = name.getText().toString().trim();
        int ageN = Integer.parseInt(age.getText().toString());
        String categoryC = spinner.getSelectedItem().toString();
        int hour = timePicker.getCurrentHour();

        int date = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year = datePicker.getYear();
        Date dateObject = new Date(date,month,year);

        //String passW = password.getText().toString().trim();
        //String m1 = maleB.getText().toString();
        //String m2 = femaleB.getText().toString();
        //String m3 = othersB.getText().toString();
        String genderCheck;

        if (maleB.isChecked()) {

            genderCheck = maleB.getText().toString();
            //userdb.setGenderU(m1);
            //genderCheck = userdb.getGenderU();
        } else if (femaleB.isChecked()) {
            genderCheck = femaleB.getText().toString();
            //userdb.setGenderU(m2);
            //genderCheck = userdb.getGenderU();
        } else {
            genderCheck = othersB.getText().toString();
            //userdb.setGenderU(m3);
            //genderCheck = userdb.getGenderU();
        }
/*
        String key = databaseReference.push().getKey();

        //UserDB userDB = new UserDB(nameS,ageN);
        databaseReference.child(key).setValue("Name");
        Intent send2 = new Intent(ManageDatabase.this, Welcome.class);
        startActivity(send2);

 */


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
                String uid = user.getUid();
                SignUp su = new SignUp();
                Review review = new Review(ageN,genderCheck,hour,categoryC,dateObject,location);

                final int random = new Random().nextInt(15);
                String index = Integer.toString(random);

                databaseReference.child(uid).child(index).setValue(review );

                location = location + "," + categoryC;
                databaseReference2.child(String.valueOf(maxid+1)).setValue(location);

                String [] values2;
                values2 = location.split(",");
                LatLng test = new LatLng(Double.parseDouble(values2[0]), Double.parseDouble(values2[1]));


        }
        else{
            Toast.makeText(ManageDatabase.this, "Invalid User.", Toast.LENGTH_LONG).show();
        }
    }


    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String address;
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    address = bundle.getString("address");
                    break;
                default:
                    address = null;
            }
            location = address;
            tvAddress.setText(address);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}