package com.example.lab1_20200643;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Estadisiticas_Activity extends AppCompatActivity {
    GameAhorcadoActivity gameAhorcadoActivity = new GameAhorcadoActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisiticas);

        LinearLayout linearLayoutEstadisticas= findViewById(R.id.linearLayoutEstadistic);

        // Supongamos que tienes una lista de tiempos llamada 'tiempos' con la duración de cada juego
        ArrayList<Integer> tiempos = obtenerTiempos();

        // Crear un TextView por cada tiempo y agregarlo al LinearLayout
        for (long tiempo : tiempos) {
            TextView textViewTiempo = new TextView(this);
            textViewTiempo.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            textViewTiempo.setText("Duración del juego: " + tiempo + " segundos");
            textViewTiempo.setTextSize(16);
            linearLayoutEstadisticas.addView(textViewTiempo);
        }

        Button button = findViewById(R.id.button_jugarDeNuevo);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(Estadisiticas_Activity.this, GameAhorcadoActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private ArrayList<Integer> obtenerTiempos(){

        return gameAhorcadoActivity.tiemposJuegos;
    }
}