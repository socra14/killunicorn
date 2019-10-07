package com.socra.socra.killunicorn.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.socra.socra.killunicorn.R;

public class pantallainicio extends AppCompatActivity {

    private Button btnplay;
    private Button btnranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainicio);

        btnplay = findViewById(R.id.btnplay);
        btnranking = findViewById(R.id.btnranking);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pantallainicio.this,juegoactivity.class);
                startActivity(i);
            }
        });

    }
}
