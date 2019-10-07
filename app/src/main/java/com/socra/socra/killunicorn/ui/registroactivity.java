package com.socra.socra.killunicorn.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.socra.socra.killunicorn.R;
import com.socra.socra.killunicorn.clases.usuarios;

public class registroactivity extends AppCompatActivity {

    private EditText etnombre,etemail,etpass,etconpas;
    private Button btnregistro;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String name,email,pass,conpass;
    private ProgressBar pbregistro;
    private ScrollView scregistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroactivity);

        etemail = findViewById(R.id.etEmail);
        etnombre = findViewById(R.id.etnombre);
        etpass = findViewById(R.id.etpassword);
        btnregistro = findViewById(R.id.btnregistro);
        pbregistro = findViewById(R.id.progressBarRegistro);
        scregistro = findViewById(R.id.formregistro);
        etconpas= findViewById(R.id.conpas);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        cambiarvisibilidadregistro(true);

        eventos();
    }

    private void eventos() {
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etnombre.getText().toString();
                email = etemail.getText().toString();
                pass = etpass.getText().toString();
                conpass = etconpas.getText().toString();

                if (name.isEmpty()){
                    etnombre.setError("El nombre es obligatorio");
                }else if (email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etemail.setError("El email es obligatorio");
                }else if (pass.isEmpty()){
                    etpass.setError("La contraseña es obligatoria");
                }else if(!pass.equals(conpass)){
                    etconpas.setError("Las contraseñas no coinciden");

                }else{
                    crearusuario();
                    //addNickAndStart();
                }

            }
        });

    }

    private void crearusuario() {
        cambiarvisibilidadregistro(false);
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            actualizarui(user);
                        }else{
                            Toast.makeText(registroactivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                            actualizarui(null);
                        }
                    }
                });

    }


    private void actualizarui(FirebaseUser user) {

        if (user!=null){
            //almacenar la informacion del usuario en firestore

            usuarios nuevousuario = new usuarios(name,0);
            db.collection("Usuarios")
                    .document(user.getUid())
                    .set(nuevousuario)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void ivoid) {

                            finish();
                            Intent intent = new Intent(registroactivity.this,pantallainicio.class);
                            startActivity(intent);
                        }
                    });


        }else{
            cambiarvisibilidadregistro(true);
            etpass.setError("Nombre, Email y/o contraseña incorrectos");
            etpass.requestFocus();
        }


    }

    private void cambiarvisibilidadregistro(boolean showform) {

        pbregistro.setVisibility(showform ? View.GONE : View.VISIBLE);
        scregistro.setVisibility(showform ? View.VISIBLE : View.GONE);
    }



}
