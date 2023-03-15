package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

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
                intent.putExtra("ModoJuego", "Clasico");
                startActivity(intent);
                finish();
            }
        });
        btnChallengeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModoJuego.this, Partida.class);
                intent.putExtra("ModoJuego", "Retos");
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

        cargarPreferencias();
    }
    private void cargarPreferencias(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ModoJuego.this);
        String colorValue = preferences.getString("button_color", "#0000FF"); // El valor por defecto es azul
        btnClassicMode.setBackgroundColor(Color.parseColor(colorValue));
        btnChallengeMode.setBackgroundColor(Color.parseColor(colorValue));

        String language = sharedPreferences.getString("language", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("button_color")) {
            // Obtener el nuevo color de los botones
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ModoJuego.this);
            String colorValue = preferences.getString("button_color", "#0000FF"); // El valor por defecto es azul
            btnClassicMode.setBackgroundColor(Color.parseColor(colorValue));
            btnChallengeMode.setBackgroundColor(Color.parseColor(colorValue));
        } else if (key.equals("language")) {
            String language = sharedPreferences.getString("language", "es");
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        TextView titulo = (TextView) findViewById(R.id.textoModoJuego);
        outState.putString("Title", titulo.getText().toString());

        outState.putString("Clasico", btnClassicMode.getText().toString());
        outState.putString("Retos", btnChallengeMode.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        TextView titulo = (TextView) findViewById(R.id.textoModoJuego);
        titulo.setText(outState.getString("Title"));

        btnClassicMode.setText(outState.getString("Clasico"));
        btnChallengeMode.setText(outState.getString("Retos"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar el listener
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}