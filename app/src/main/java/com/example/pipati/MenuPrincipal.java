package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Locale;

public class MenuPrincipal extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    Button btnJugar, btnHistorial, btnAjustes;
    ImageButton btnBack;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Obtenemos las preferencias
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Inicializamos los recursos
        btnJugar = (Button) findViewById(R.id.botonJugar);
        btnHistorial = (Button) findViewById(R.id.botonHistorial);
        btnAjustes = (Button) findViewById(R.id.botonAjustes);
        btnBack = (ImageButton) findViewById(R.id.menuPrincipalFlechaAtras);

        // El boton jugar carga la actividad ModoJuego
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, ModoJuego.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(intent);
                finish();
            }
        });

        // El boton historial carga la actividad Historico
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Historico.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(intent);
                finish();
            }
        });

        // El boton ajustes carga la actividad Ajustes
        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Ajustes.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(intent);
            }
        });

        // El boton atras carga la actividad anterior
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Login.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(intent);
                finish();
            }
        });

        // Cargamos las preferencias establecidas por el ususario
        cargarPreferencias();
    }

    // Funcion que carga las preferencias del usuario
    private void cargarPreferencias(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        String colorValue = preferences.getString("button_color", "#0000FF"); // El valor por defecto es azul
        btnJugar.setBackgroundColor(Color.parseColor(colorValue));
        btnHistorial.setBackgroundColor(Color.parseColor(colorValue));
        btnAjustes.setBackgroundColor(Color.parseColor(colorValue));

        String language = preferences.getString("language", "es");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        if (key.equals("button_color")) {
            // Obtener el nuevo color de los botones
            String colorValue = preferences.getString("button_color", "#0000FF"); // El valor por defecto es azul
            btnJugar.setBackgroundColor(Color.parseColor(colorValue));
            btnHistorial.setBackgroundColor(Color.parseColor(colorValue));
            btnAjustes.setBackgroundColor(Color.parseColor(colorValue));
        } else if (key.equals("language")) {
            String language = preferences.getString("language", "es");
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
        recreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("Jugar", btnJugar.getText().toString());
        outState.putString("Historial", btnHistorial.getText().toString());
        outState.putString("Ajustes", btnAjustes.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        btnJugar.setText(outState.getString("Jugar"));
        btnHistorial.setText(outState.getString("Historial"));
        btnAjustes.setText(outState.getString("Ajustes"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar el listener
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}