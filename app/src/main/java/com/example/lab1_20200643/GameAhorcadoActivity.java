package com.example.lab1_20200643;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Random;

public class GameAhorcadoActivity extends AppCompatActivity {

    private String [] palabras;
    private Random random;
    private String actualPalabra;
    private TextView[] letraView;
    private LinearLayout palabraLayout;
    private LetterAdapter adapter;
    private GridView gridView;
    private int numCorr;
    private int numChars;
    private ImageView[] parts;
    private int sizeParts =6;
    private  int currPart;
    private int startTime;
    private int endTime;
    private Button button;
    public static ArrayList<Integer> tiemposJuegos = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ahorcado);

        //obtener el arreglo de palabras que usaremos
        palabras=getResources().getStringArray(R.array.arreglo_palabras);
        //Layout de las letras de la palabra
        palabraLayout=findViewById(R.id.palabraLayout);
        //
        gridView=findViewById(R.id.letrasGridView);
        random= new Random();
        parts = new ImageView[sizeParts];
        parts[0] = findViewById(R.id.head);
        parts[1] = findViewById(R.id.torso);
        parts[2] = findViewById(R.id.brazoIzquierdo);
        parts[3] = findViewById(R.id.brazoDerecho);
        parts[4] = findViewById(R.id.piernaIzquierda);
        parts[5] = findViewById(R.id.piernaDerecha);

        //jugar de nuevo
        button = findViewById(R.id.button_jugarOtraVez);
        button.setOnClickListener(view -> {
            obtenerPalabra();
        });


        obtenerPalabra();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflar
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.statistics){
            Intent intent = new Intent(GameAhorcadoActivity.this, Estadisiticas_Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void obtenerPalabra (){
        // Registrar el tiempo de inicio
        startTime = (int) System.currentTimeMillis();
        String newPalabra = palabras[random.nextInt(palabras.length)];
        //Para no repetir la palabra seleccionada
        while (newPalabra.equals(actualPalabra))newPalabra = palabras[random.nextInt(palabras.length)];
        actualPalabra=newPalabra;

        letraView = new TextView[actualPalabra.length()];
        palabraLayout.removeAllViews();


        for(int i=0; i<actualPalabra.length();i++){
            letraView[i] = new TextView(this);
            letraView[i].setText(""+actualPalabra.charAt(i));
            //Estableces los parametros de los laout que tendra cada letra
            letraView[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            letraView[i].setGravity(Gravity.CENTER);
            letraView[i].setTextColor(Color.WHITE);
            letraView[i].setTextSize(20);
            palabraLayout.addView(letraView[i]);
        }

        adapter= new LetterAdapter(this);
        gridView.setAdapter(adapter);
        numCorr=0;
        currPart=0;
        numChars=actualPalabra.length();

        for(int i =0; i<sizeParts;i++){
            parts[i].setVisibility(View.INVISIBLE);
        }
    }

    public void letterPressed(View view){
        String letter = ((TextView)view).getText().toString();
        char letterChar = letter.charAt(0);
        view.setEnabled(false);
        boolean correct = false;
        for(int i=0;i<actualPalabra.length();i++){
            if(actualPalabra.charAt(i)==letterChar){
                correct=true;
                numCorr ++;
                 letraView[i].setTextColor(Color.BLACK);
            }
        }

        if(correct){

            if(numCorr==numChars){
                disableButtons();

                // Registra el tiempo de finalización
                endTime = (int) System.currentTimeMillis();
                // Calcula el tiempo transcurrido
                int elapsedTimeSeconds = (endTime - startTime) / 1000;
                tiemposJuegos.add(elapsedTimeSeconds);


                MaterialAlertDialogBuilder builder= new MaterialAlertDialogBuilder(this);
                builder.setTitle("Ganaste");
                builder.setMessage("Felicidades\n\nLa respuesta era\n\n" + actualPalabra +
                        "\n\nTiempo: " + elapsedTimeSeconds + " segundos");
                builder.setPositiveButton("Jugar de Nuevo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GameAhorcadoActivity.this.obtenerPalabra();
                    }
                });
                builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GameAhorcadoActivity.this.finish();
                    }
                });
                builder.show();
            }

        } else if (currPart<sizeParts) {
            parts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        }

        if (currPart >= sizeParts) {

            disableButtons();
            // Registra el tiempo de finalización
            endTime = (int) System.currentTimeMillis();
            // Calcula el tiempo transcurrido
            int elapsedTimeSeconds = (endTime - startTime) / 1000;
            tiemposJuegos.add(elapsedTimeSeconds);

            MaterialAlertDialogBuilder builder= new MaterialAlertDialogBuilder(this);
            builder.setTitle("Perdiste");
            builder.setMessage("Has perdido\n\nLa respuesta era\n\n" + actualPalabra +
                    "\n\nTiempo: " + elapsedTimeSeconds + " segundos");
            builder.setPositiveButton("Jugar de Nuevo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    GameAhorcadoActivity.this.obtenerPalabra();
                }
            });
            builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    GameAhorcadoActivity.this.finish();
                }
            });
            builder.show();
        }
    }
    public void disableButtons(){
        for (int i=0; i<gridView.getChildCount();i++){
            gridView.getChildAt(i).setEnabled(false);
        }
    }
}