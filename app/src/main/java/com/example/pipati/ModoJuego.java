package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ModoJuego extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    Button btnClassicMode, btnChallengeMode;
    ImageButton btnBack;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_juego);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        btnClassicMode = (Button) findViewById(R.id.btnModoClasico);
        btnChallengeMode = (Button) findViewById(R.id.btnModoRetos);

        btnBack = (ImageButton) findViewById(R.id.modoJuegoFlechaAtras);

        btnClassicMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModoJuego.this, Partida.class);
                startActivity(intent);
                finish();
            }
        });
        btnChallengeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModoJuego.this, Partida.class);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModoJuego.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
        //cargarPreferencias();
    }
    private void cargarPreferencias(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ModoJuego.this);
        String colorValue = preferences.getString("button_color", "#0000FF"); // El valor por defecto es azul
        btnClassicMode.setBackgroundColor(Color.parseColor(colorValue));
        btnChallengeMode.setBackgroundColor(Color.parseColor(colorValue));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("button_color")) {
            // Obtener el nuevo color de los botones
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ModoJuego.this);
            String colorValue = preferences.getString("button_color", "#0000FF"); // El valor por defecto es azul
            btnClassicMode.setBackgroundColor(Color.parseColor(colorValue));
            btnChallengeMode.setBackgroundColor(Color.parseColor(colorValue));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar el listener
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}