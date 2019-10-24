package com.socra.socra.killunicorn.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.socra.socra.killunicorn.R;

import java.util.Random;
import java.util.prefs.Preferences;

public class juegoactivity extends AppCompatActivity {

    TextView contador,tiempo,tvnick;
    ImageView imageViewpato,imageView2;
    int counter =0;
    int anchopantalla;
    int altopantalla;
    Random aleatorio;
    boolean gameover = false;
    String uid,name;
    FirebaseFirestore bd;
    private FirebaseUser firebaseUser;
    int maxpuntuacion ;
    MediaPlayer clickunicorn,musicabase,musicafin;
    ImageView imageView;
    private InterstitialAd mInterstitialAd;
    FirebaseAuth firebaseAuth;
    Button btncomp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegoactivity);

        bd = FirebaseFirestore.getInstance();

        inicializaComponentes();
        eventos();
        initPantalla();
        moverPato();
        moverPato2();
        initCuentaatras();


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5375202444824175/3901433403");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        uid = firebaseUser.getUid();
        getPlayerNames();
        maxpuntuacion = getSharedPreferences("maxpun",MODE_PRIVATE).getInt("counter",0);
        musicabase.start();
        musicabase.setVolume(5,5);
        musicabase.setLooping(true);
        //Bundle extras = getIntent().getExtras();
        //uid = extras.getString(Constantes.EXTRA_JUGADA_ID);
    }

    private void initCuentaatras() {
        new CountDownTimer(40000,1000){
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

    private void getPlayerNames() {
        // Obtener el nombre del player
        bd.collection("Usuarios")
                .document(uid)
                .get()
                .addOnSuccessListener(juegoactivity.this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        name = documentSnapshot.get("nick").toString();
                        tvnick.setText(name);


                    }
                });

    }

    private void saveResultFirestore() {
        if (counter > maxpuntuacion){
            getSharedPreferences("maxpun", MODE_PRIVATE).edit().putInt("counter", counter).commit();

            bd.collection("Usuarios")
                    .document(uid)
                    .update(
                            "patos",counter
                    );


        }



    }

    private void mostrardialogofin() {

        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.gameover, null);



        builder.setMessage("Has conseguido matar " +counter+ " unicornios")
                .setTitle("Game over")
                .setCancelable(false)
                .setView(v);

        imageView = v.findViewById(R.id.unicorn);



        builder.setPositiveButton("Reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();

                } else {


                    counter = 0;
                    contador.setText("0");
                    gameover = false;
                    initCuentaatras();
                    moverPato2();
                    moverPato();
                }

                mInterstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {

                        counter = 0;
                        contador.setText("0");
                        gameover = false;
                        initCuentaatras();
                        moverPato();
                        moverPato2();
                        super.onAdClosed();
                    }
                });





            }
        });


        builder.setNegativeButton("salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();

                } else {

                }

                dialogInterface.dismiss();
                finish();
                //Intent in = new Intent(gameActivity.this,rankingActivityn.class);
                //startActivity(in);

            }
        });
        btncomp = v.findViewById(R.id.btncompartir);
        btncomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "He conseguido aplastar " +counter+ " unicornios.  https://play.google.com/store/apps/details?id=com.pixin&gl=ES");
                startActivity(Intent.createChooser(intent, "Compartir en"));
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
                    clickunicorn.start();
                    counter++;
                    contador.setText(String.valueOf(counter));

                    imageViewpato.setImageResource(R.drawable.sangrerosa);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageViewpato.setImageResource(R.drawable.newunicorn);
                            moverPato();
                        }
                    },300);
                }


            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameover){
                    clickunicorn.start();
                    counter++;
                    contador.setText(String.valueOf(counter));

                    imageView2.setImageResource(R.drawable.sangrerosa);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView2.setImageResource(R.drawable.unicorn2);
                            moverPato2();
                        }
                    },400);
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

    private void moverPato2(){
        int min = 0;
        int maxX = anchopantalla-imageView2.getWidth();
        int maxY = altopantalla - imageView2.getHeight();

        int randomx = aleatorio.nextInt(((maxX-min)+1)+min);
        int randomy = aleatorio.nextInt(((maxY-min)+1)+min);

        imageView2.setX(randomx);
        imageView2.setY(randomy);


    }

    private void inicializaComponentes() {
        clickunicorn = MediaPlayer.create(this,R.raw.clickunic);
        musicabase = MediaPlayer.create(this,R.raw.musicaprincipal);
        contador =findViewById(R.id.tvcontador);
        tiempo =findViewById(R.id.tvtimer);
        tvnick =findViewById(R.id.nombrefinal);
        imageViewpato =findViewById(R.id.fotopato1);
        imageView2 =findViewById(R.id.fotopato2);


       /* Typeface tipeface = Typeface.createFromAsset(getAssets(),"pixel.ttf");
        contador.setTypeface(tipeface);
        tiempo.setTypeface(tipeface);
        tvnick.setTypeface(tipeface);

        */






    }

    @Override
    protected void onStart() {
        musicabase.start();
        super.onStart();
    }

    @Override
    protected void onStop() {
        musicabase.stop();
        super.onStop();
    }
}
