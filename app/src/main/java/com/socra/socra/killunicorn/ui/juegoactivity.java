package com.socra.socra.killunicorn.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.socra.socra.killunicorn.R;

import java.util.Random;

public class juegoactivity extends AppCompatActivity {

    TextView contador,tiempo,tvnick;
    ImageView imageViewpato;
    int counter =0;
    int anchopantalla;
    int altopantalla;
    Random aleatorio;
    boolean gameover = false;
    String id,name;
    FirebaseFirestore bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegoactivity);

        bd = FirebaseFirestore.getInstance();

        inicializaComponentes();
        eventos();
        initPantalla();
        moverPato();
        initCuentaatras();
    }

    private void initCuentaatras() {
        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long l) {
                long segundosrestantes = l /1000;
                tiempo.setText(segundosrestantes+"s");
            }

            @Override
            public void onFinish() {
                tiempo.setText("0s");
                gameover = true;
                mostrardialogofin();
                saveResultFirestore();
            }
        }.start();
    }

    private void saveResultFirestore() {
        bd.collection("Usuarios")
                .document(id)
                .update(
                        "patos",counter
                );
    }

    private void mostrardialogofin() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("Has conseguido cazar " +counter+ " patos").setTitle("Game over");
        builder.setCancelable(false);

        builder.setPositiveButton("Reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                counter = 0;
                contador.setText("0");
                gameover = false;
                initCuentaatras();
                moverPato();
            }
        });

        builder.setNegativeButton("Ver ranking", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
               //Intent in = new Intent(gameActivity.this,rankingActivityn.class);
                //startActivity(in);

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private void initPantalla() {
        Display display = getWindowManager().getDefaultDisplay();
        Point sice = new Point();
        display.getSize(sice);
        anchopantalla =sice.x;
        altopantalla =sice.y;

        aleatorio= new Random();

    }

    private void eventos() {
        imageViewpato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameover){
                    counter++;
                    contador.setText(String.valueOf(counter));

                    imageViewpato.setImageResource(R.drawable.duck_clicked);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageViewpato.setImageResource(R.drawable.duck);
                            moverPato();
                        }
                    },500);
                }


            }
        });

    }

    private void moverPato(){
        int min = 0;
        int maxX = anchopantalla-imageViewpato.getWidth();
        int maxY = altopantalla - imageViewpato.getHeight();

        int randomx = aleatorio.nextInt(((maxX-min)+1)+min);
        int randomy = aleatorio.nextInt(((maxY-min)+1)+min);

        imageViewpato.setX(randomx);
        imageViewpato.setY(randomy);


    }

    private void inicializaComponentes() {

        contador =findViewById(R.id.tvcontador);
        tiempo =findViewById(R.id.tvtimer);
        tvnick =findViewById(R.id.nombrefinal);
        imageViewpato =findViewById(R.id.fotopato1);

       /* Typeface tipeface = Typeface.createFromAsset(getAssets(),"pixel.ttf");
        contador.setTypeface(tipeface);
        tiempo.setTypeface(tipeface);
        tvnick.setTypeface(tipeface);

        */






    }

}
