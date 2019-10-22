package com.socra.socra.killunicorn.ui;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.socra.socra.killunicorn.R;

public class ranking_activity extends AppCompatActivity {
   private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_activity);
        music = MediaPlayer.create(this,R.raw.musicamenu);
        music.start();
        music.setLooping(true);





      getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentcontainer,new userFragment())
                .commit();










    }

    @Override
    protected void onStop() {
        music.stop();
        super.onStop();
    }
}
