package com.socra.socra.killunicorn.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.socra.socra.killunicorn.R;

public class pantallainicio extends AppCompatActivity {

    private Button btnplay;
    private Button btnranking;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private String uid, jugadaId = "";

    MediaPlayer click, musicamenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainicio);
        MobileAds.initialize(this, "ca-app-pub-5375202444824175~4535471184");

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        click = MediaPlayer.create(this,R.raw.click2);
        musicamenu = MediaPlayer.create(this,R.raw.musicamenu);
        musicamenu.start();
        musicamenu.setLooping(true);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();

        btnplay = findViewById(R.id.btnplay);
        btnranking = findViewById(R.id.btnranking);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click.start();
                Intent i = new Intent(pantallainicio.this,juegoactivity.class);
                startActivity(i);
            }
        });

        btnranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click.start();
                Intent i = new Intent(pantallainicio.this,ranking_activity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStop() {
        musicamenu.stop();
        super.onStop();
    }
}
