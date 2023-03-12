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

public class MenuPrincipal extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    Button btnJugar, btnHistorial, btnAjustes;
    ImageButton btnBack;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        btnJugar = (Button) findViewById(R.id.botonJugar);
        btnHistorial = (Button) findViewById(R.id.botonHistorial);
        btnAjustes = (Button) findViewById(R.id.botonAjustes);

        btnBack = (ImageButton) findViewById(R.id.menuPrincipalFlechaAtras);

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, ModoJuego.class);
                startActivity(intent);
                finish();
            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Historico.class);
                startActivity(intent);
                finish();
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Ajustes.class);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        //cargarPreferencias();
    }
    private void cargarPreferencias(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        String colorValue = preferences.getString("button_color", "#0000FF"); // El valor por defecto es azul
        btnJugar.setBackgroundColor(Color.parseColor(colorValue));
        btnHistorial.setBackgroundColor(Color.parseColor(colorValue));
        btnAjustes.setBackgroundColor(Color.parseColor(colorValue));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("button_color")) {
            // Obtener el nuevo color de los botones
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
            String colorValue = preferences.getString("button_color", "#0000FF"); // El valor por defecto es azul
            btnJugar.setBackgroundColor(Color.parseColor(colorValue));
            btnHistorial.setBackgroundColor(Color.parseColor(colorValue));
            btnAjustes.setBackgroundColor(Color.parseColor(colorValue));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar el listener
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}