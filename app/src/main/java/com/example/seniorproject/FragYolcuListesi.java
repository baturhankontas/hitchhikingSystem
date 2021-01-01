package com.example.seniorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FragYolcuListesi  extends Fragment
{

    FirebaseDatabase db;
    DatabaseReference ref;

    ArrayList<YolcuIlani> al = new ArrayList<>();

    View root;
    ListView lv;
    BaseAdapter ba;
    LayoutInflater li;

    Oy currOy;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_yolcu_listesi, container, false);


        db = FirebaseDatabase.getInstance();
        ref = db.getReference("yolcu_ilanlari");

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

                YolcuIlani yi = al.get(i);

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
                    YolcuIlani yi = ds.getValue(YolcuIlani.class);
                    yi.id = ds.getKey();
                    al.add(yi);
                }

                ba.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                final String secilenKullaniciFBID = al.get(position).kullaniciFBID;
                Log.e("x","SECILEN USER FB ID : "+secilenKullaniciFBID);

                final DatabaseReference userRef = db.getReference("Users/"+secilenKullaniciFBID);

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final HashMap<String,String> hm = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, String>>() {
                        });
                        Log.e("x","CurrUser : "+hm.toString());

                        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                        adb.setTitle("Oylama");
                        adb.setMessage("Bu Kullanıcı Ile Daha Once Yolculuk Yaptıysaniz, Yorumunuz Ne Yonde ?");
                        adb.setPositiveButton("Olumlu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                int oldIyi = new Integer(hm.get("iyi"));
                                oldIyi++;
                                hm.put("iyi", ""+oldIyi);
                                userRef.setValue(hm);
                                Toast.makeText(getActivity(),
                                        "Oyunuz Kaydedildi",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                        adb.setNegativeButton("Olumsuz", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                int oldIyi = new Integer(hm.get("kotu"));
                                oldIyi++;
                                hm.put("kotu", ""+oldIyi);
                                userRef.setValue(hm);
                                Toast.makeText(getActivity(),
                                        "Oyunuz Kaydedildi",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                return true;
            }
        });

        return root;
    }
}
