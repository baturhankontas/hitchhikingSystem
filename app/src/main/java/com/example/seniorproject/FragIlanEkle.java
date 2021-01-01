package com.example.seniorproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class FragIlanEkle extends Fragment
{
    View root;

    EditText et;
    Button btn;
    String tur = "";
    RadioGroup rg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_ilan_ekle, container, false);


        rg = root.findViewById(R.id.rg);
        et = root.findViewById(R.id.et);
        btn = root.findViewById(R.id.btnEkle);

        btn.setOnClickListener(new View.OnClickListener()
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ref;

            @Override
            public void onClick(View v)
            {
                if (rg.getCheckedRadioButtonId() == R.id.rb1)
                {
                    YolcuIlani yi = new YolcuIlani(new Date().toString(), user.getEmail(), et.getText().toString());
                    yi.kullaniciFBID = user.getUid();
                    ref = db.getReference("yolcu_ilanlari");
                    ref.push().setValue(yi);
                }
                if (rg.getCheckedRadioButtonId() == R.id.rb2)
                {
                    AracIlani ai = new AracIlani(new Date().toString(), user.getEmail(), et.getText().toString());
                    ai.kullaniciFBID = user.getUid();
                    ref = db.getReference("arac_ilanlari");
                    ref.push().setValue(ai);
                }

                Toast.makeText(getActivity(), "Ilaniniz Kaydedildi, Geri Tuşuna Basın", Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }
}
