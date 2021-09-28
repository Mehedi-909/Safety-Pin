package com.example.otplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTrustedContact extends AppCompatActivity {

    TextView textView;
    EditText trustedContact;
    Button submit;
    DatabaseReference databaseReferenceContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trusted_contact);

        databaseReferenceContact = FirebaseDatabase.getInstance().getReference("Trusted Contact");
        trustedContact = findViewById(R.id.trustedContact);
        submit = findViewById(R.id.buttonTrustedContact);
        submit.setOnClickListener(new View.OnClickListener() {
            //progressBar1.setVisibility(View.VISIBLE);
            @Override
            public void onClick(View v) {

                saveTrustedContact(trustedContact.getText().toString());

            }
        });

    }

    public void saveTrustedContact(String contact){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            databaseReferenceContact.child(uid).setValue(contact);
            Toast.makeText(AddTrustedContact.this, "Added Successfully", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(AddTrustedContact.this, "Invalid User.", Toast.LENGTH_LONG).show();
        }
    }
}