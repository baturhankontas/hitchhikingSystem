package com.example.seniorproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity
{
    ViewPager vp;

    FragYolcuListesi fyl;
    FragAracListesi fal;
    FragIlanEkle fie;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = findViewById(R.id.vp);

        fyl = new FragYolcuListesi();
        fal = new FragAracListesi();
        fie = new FragIlanEkle();

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            public Fragment getItem(int i)
            {
                if (i == 0) return fyl;
                else if (i == 1) return fal;
                else return fie;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position)
            {
                if (position == 0) return "Araç Arayanlar";
                else if (position == 1) return "Yol Arkadaşı Arayanlar";
                else return "Ilan Ver";
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Hesabım")
                .setIcon(R.drawable.ic_user)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String ad = item.getTitle().toString();
        if (ad.equals("Hesabım"))
        {
            startActivity(new Intent(MainActivity.this, ProfilActivity.class));
            
        }
        return super.onOptionsItemSelected(item);
    }
}
