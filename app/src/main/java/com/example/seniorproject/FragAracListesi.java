package com.example.seniorproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class FragAracListesi  extends Fragment
{

    FirebaseDatabase db;
    DatabaseReference ref;

    ArrayList<AracIlani> al = new ArrayList<>();

    View root;
    ListView lv;
    BaseAdapter ba;
    LayoutInflater li;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_yolcu_listesi, container, false);


        db = FirebaseDatabase.getInstance();
        ref = db.getReference("arac_ilanlari");

        li = inflater;

        lv = root.findViewById(R.id.lv);
        ba = new BaseAdapter() {
            @Override
            public int getCount() {
                return al.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int i, View v, ViewGroup parent) {
                if (v == null)
                    v = li.inflate(R.layout.yolcu_item, null);

                TextView tvIlanSahibi = v.findViewById(R.id.tvIlanSahibi);
                TextView tvIlanTarihi = v.findViewById(R.id.tvIlanTarihi);
                TextView tvIlanMetni = v.findViewById(R.id.tvIlanMetni);

                AracIlani yi = al.get(i);

                tvIlanSahibi.setText( yi.kullaniciAdi );
                tvIlanMetni.setText( yi.aciklama );
                tvIlanTarihi.setText( yi.tarih );

                return v;

            }
        };

        lv.setAdapter(ba);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                al.clear();
                Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();

                while (it.hasNext())
                {
                    DataSnapshot ds = it.next();
                    AracIlani yi = ds.getValue(AracIlani.class);
                    yi.id = ds.getKey();
                    al.add(yi);
                }

                ba.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }
}
