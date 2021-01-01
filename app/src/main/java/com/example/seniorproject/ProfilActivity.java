package com.example.seniorproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    FirebaseUser user;
    EditText et;
    TextView tvP, tvN;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        getSupportActionBar().setTitle("Profil Ekranı");

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();


        user = auth.getCurrentUser();

        ref = db.getReference("Users/"+user.getUid());

        et = findViewById(R.id.etUn);

        tvP = findViewById(R.id.tvP);
        tvN = findViewById(R.id.tvN);



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> hm = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, String>>() {
                });

                et.setText(user.getEmail());
                tvP.setText(hm.get("iyi"));
                tvN.setText(hm.get("kotu"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void doExit(View view)
    {
        auth.signOut();
        Intent i = new Intent(ProfilActivity.this, StartActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }
}
