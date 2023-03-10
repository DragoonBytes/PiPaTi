package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity {

    Button btnJugar, btnHistorial, btnAjustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnJugar = (Button) findViewById(R.id.botonJugar);
        btnHistorial = (Button) findViewById(R.id.botonHistorial);
        btnAjustes = (Button) findViewById(R.id.botonAjustes);

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
    }
}