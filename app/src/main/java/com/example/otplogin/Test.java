package com.example.otplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Test extends AppCompatActivity {

    ListView listView;
    TextView textView;
    DatabaseReference reference;
    long count = 0;
    ArrayList<LatLng> testLatLng = new ArrayList<>();
    ArrayList<Coordinate> locations = new ArrayList<>();
    ArrayList<Location> locationList = new ArrayList<>();
    Location location;
    int count2=0;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayListm;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        arrayListm=new ArrayList<>();

        listView = findViewById(R.id.testList);
        textView =findViewById(R.id.textView5);

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayListm);

        listView.setAdapter(arrayAdapter);



        readData(new MyCallback() {
            @Override
            public void onCallback(String value) {
                //Log.d("mehedi", value);
                arrayAdapter.notifyDataSetChanged();
                String total=null;
                for(String s : arrayListm){
                    //total = total + s;
                    textView.setText(s);
                }
                //textView.setText(total);

            }
        });


//        for(Coordinate c : locations){
//            a++;
//            Toast.makeText(this,"Latitude "+ String.valueOf(c.getLatitude()), Toast.LENGTH_SHORT).show();
//        }
        //Toast.makeText(this, s, Toast.LENGTH_LONG).show();

    }

    private void readData(MyCallback firebaseCallBack){

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

                    //s = value;
                    arrayListm.add(value);


//                    values2 = value.split(",");
//                    arrayListm.add(values2[1]);
//
//                    double a = Double.parseDouble(values2[0]);
//                    double b = Double.parseDouble(values2[1]);
////                    location.setLatitude(a);
////                    location.setLongitude(b);
////                    locationList.add(location);
//
//                    LatLng test = new LatLng(Double.parseDouble(values2[0]), Double.parseDouble(values2[1]));
//                    testLatLng.add(test);
//                    locations.add(new Coordinate(a,b));

                }
               //arrayAdapter.notifyDataSetChanged();
//                int a=0;
//                for(LatLng l: testLatLng){
//                    count2++;
//                    //Toast.makeText(this,"Latitude "+ String.valueOf(l.latitude), Toast.LENGTH_SHORT).show();
//                }
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
}