package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class Partida extends AppCompatActivity {

    SQLiteDatabase db;
    Button btnPiedra, btnPapel, btnTijeras;
    TextView tvScoreP1, tvScoreP2, nameP1, nameP2;
    int p1Score, p2Score, nGames, result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        DBManager dbManager = new DBManager(this);
        db = dbManager.getWritableDatabase();

        btnPiedra = (Button) findViewById(R.id.btnPiedra);
        btnPapel = (Button) findViewById(R.id.btnPapel);
        btnTijeras = (Button) findViewById(R.id.btnTijeras);

        tvScoreP1 = (TextView) findViewById(R.id.scoreJugador1);
        tvScoreP2 = (TextView) findViewById(R.id.scoreJugador2);
        nameP1 = (TextView) findViewById(R.id.nomJugador1);
        nameP2 = (TextView) findViewById(R.id.nomJugador2);

        tvScoreP1.setText("Score: " + p1Score);
        tvScoreP2.setText("Score: " + p2Score);

        Random random = new Random();

        btnPiedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int player1 = 1;
                int player2 = random.nextInt(2) + 1;
                int result = checkResults(player1, player2);
                results(result);
            }
        });
        btnPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int player1 = 2;
                int player2 = random.nextInt(2) + 1;
                int result = checkResults(player1, player2);
                results(result);
            }
        });
        btnTijeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int player1 = 3;
                int player2 = random.nextInt(2) + 1;
                result = checkResults(player1, player2);
                results(result);

            }
        });
    }

    public int checkResults(int player1, int player2) {
        result = 0;
        switch(player1) {
            case 1:
                // El jugador ha elegido piedra
                switch (player2) {
                    case 1:
                        result = 2;
                        break;
                    case 2:
                        result = 0;
                        break;
                    case 3:
                        result = 1;
                        break;
                }
            case 2:
                // El jugador ha elegido papel
                switch (player2) {
                    case 1:
                        result = 1;
                        break;
                    case 2:
                        result = 2;
                        break;
                    case 3:
                        result = 0;
                        break;
                }
            case 3:
                // El jugador ha elegido tijera
                switch (player2) {
                    case 1:
                        result = 0;
                        break;
                    case 2:
                        result = 1;
                        break;
                    case 3:
                        result = 2;
                        break;
                }
        }
        return result;
    }

    public void results(int result){
        switch (result){
            case 0:
                nGames++;
                p2Score++;
                tvScoreP2.setText("Score: " + p2Score);
                endGame();
                break;
            case 1:
                nGames++;
                p1Score++;
                tvScoreP1.setText("Score: " + p1Score);
                endGame();
                break;
            case 2:
                Toast.makeText(Partida.this, "Empate", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void endGame(){
        if (nGames == 3 || p1Score == 2 || p2Score == 3){
            String query = "INSERT INTO games(player1, scoreP1, player2, ScoreP2) VALUES(?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindString(1, nameP1.getText().toString());
            statement.bindString(2, Integer.toString(p1Score));
            statement.bindString(3, nameP2.getText().toString());
            statement.bindString(4, Integer.toString(p2Score));
            long result = statement.executeInsert();
            if (result != -1) {
                //Toast.makeText(Partida.this, "Fin de partida", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Partida.this, ModoJuego.class);
                startActivity(intent);
                finish();
            } else {
                //Toast.makeText(Partida.this, "Error al guardar el resultado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Partida.this, ModoJuego.class);
                startActivity(intent);
                finish();
            }
        }
    }
}