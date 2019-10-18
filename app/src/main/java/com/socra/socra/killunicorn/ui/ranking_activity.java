package com.socra.socra.killunicorn.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.socra.socra.killunicorn.R;

public class ranking_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_activity);





      getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentcontainer,new userFragment())
                .commit();










    }



}
