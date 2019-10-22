package com.socra.socra.killunicorn.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.socra.socra.killunicorn.R;

public class loginactivity extends AppCompatActivity {

    private EditText etemail,etpassword;
    private Button btnInicio,btnRegistro;
    private ScrollView formlogin;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String email,pass,name;
    boolean logintry = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etemail = findViewById(R.id.etNombre);
        etpassword = findViewById(R.id.etpassword);
        btnInicio = findViewById(R.id.btnPlay);
        progressBar = findViewById(R.id.progressBarlogin);
        btnRegistro = findViewById(R.id.btnIrAregistrar);

        MobileAds.initialize(this, "ca-app-pub-5375202444824175~4535471184");

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        firebaseAuth = FirebaseAuth.getInstance();
        cambiarvisibilidadlogin(true);
        eventos();

       // Typeface tipeface = Typeface.createFromAsset(getAssets(),"pixel.ttf");

       // etemail.setTypeface(tipeface);
       // btnInicio.setTypeface(tipeface);
       // btnRegistro.setTypeface(tipeface);
    }

    private void eventos() {
        btnInicio.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                email = etemail.getText().toString();
                pass = etpassword.getText().toString();

                if (email.isEmpty()){
                    etemail.setError("El Email es obligatorio");
                }else if(pass.isEmpty()){
                    etpassword.setError("La contraseña es obligatoria");
                }else{
                    cambiarvisibilidadlogin(false);
                    loginuser();
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(loginactivity.this,registroactivity.class);
                startActivity(i);
            }
        });
    }

    private void loginuser() {

        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        logintry = true;
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateui(user);
                        }else{
                            Log.w("TAG","singingInError: ",task.getException());
                            Toast.makeText(loginactivity.this, "ha ocurrido un error", Toast.LENGTH_SHORT).show();
                            updateui(null);
                        }
                    }
                });
    }

    private void updateui(FirebaseUser user) {
        if (user!=null){
            //almacenar la informacion del usuario en firestore

            Intent i = new Intent(loginactivity.this,pantallainicio.class);
            startActivity(i);




        }else{
            cambiarvisibilidadlogin(true);
            if (logintry) {
                etpassword.setError("Nombre, Email y/o contraseña incorrectos");
                etpassword.requestFocus();
            }

        }
    }

    private void cambiarvisibilidadlogin(boolean showform) {

        progressBar.setVisibility(showform ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser  currentuser = firebaseAuth.getCurrentUser();
        updateui(currentuser);
    }
}
