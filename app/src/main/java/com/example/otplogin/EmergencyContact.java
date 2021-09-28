package com.example.otplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EmergencyContact extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        listView=(ListView)findViewById(R.id.listview);

        final ArrayList<String> arrayList=new ArrayList<>();

//Add elements to arraylist
        arrayList.add("Dhaka");
        arrayList.add("Chittagong");
        arrayList.add("Rajshahi");
        arrayList.add("Khulna");
        arrayList.add("Barishal");
        arrayList.add("Sylhet");
        arrayList.add("Rangpur");
        arrayList.add("Call to Nearest Police Station");
        arrayList.add("Add Trusted Contact");
        arrayList.add("Send SMS to Trusted Contact");


        //Create Adapter
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this,"clicked item:"+i+" "+arrayList.get(i).toString(),Toast.LENGTH_SHORT).show();
                String divisionName = arrayList.get(i);
                Intent send = new Intent(EmergencyContact.this, PoliceContactNumber.class);
                send.putExtra("name",divisionName);
                startActivity(send);

            }
        });

    }
}