package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModoJuego extends AppCompatActivity {

    Button btnClassicMode, btnChallengeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_juego);

        btnClassicMode = (Button) findViewById(R.id.btnModoClasico);
        btnChallengeMode = (Button) findViewById(R.id.btnModoRetos);

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
    }
}