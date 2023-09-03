package com.example.lab1_20200643;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lab1_20200643.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflarlo
        binding = ActivityMainBinding.inflate(getLayoutInflater()); //obtener el layout
        setContentView(binding.getRoot()); //establecer el contentView a mostrar conel binding

        //Pregunta 1
        registerForContextMenu((TextView) findViewById(R.id.titulo_juego)); // se le pasa el textViwe al que se mantendrá presionado

        //Pregunta 2
        binding.buttonJugar.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameAhorcadoActivity.class);
            startActivity(intent);
        });
    }


    //Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        TextView titulo = findViewById(R.id.titulo_juego);
        int id = item.getItemId();

        if(id==R.id.cambiar_azul){
            int colorAzul = ContextCompat.getColor(this, R.color.color_azul);
            titulo.setTextColor(colorAzul);
            return true;
        } else if (id==R.id.cambiar_verde) {
            int colorVerde = ContextCompat.getColor(this, R.color.color_verde);
            titulo.setTextColor(colorVerde);
            return true; // Agrega esta línea
        } else if (id==R.id.cambiar_rojo) {
            int colorRojo = ContextCompat.getColor(this, R.color.color_rojo);
            titulo.setTextColor(colorRojo);
            return true; // Agrega esta línea
        }

        return super.onContextItemSelected(item);

    }




    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }*/
}