package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class Partida extends AppCompatActivity {

    SQLiteDatabase db;
    AlertDialog dialogFinPartida;
    ImageView imgP1, imgP2;
    ImageButton btnPiedra, btnPapel, btnTijeras;
    TextView tvScoreP1, tvScoreP2, nameP1, nameP2;
    int player1, player2, turno, p1Score, p2Score, nGames = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        DBManager dbManager = new DBManager(this);
        db = dbManager.getWritableDatabase();

        imgP1 = (ImageView) findViewById(R.id.imagenJugador1);
        imgP2 = (ImageView) findViewById(R.id.imagenJugador2);

        btnPiedra = (ImageButton) findViewById(R.id.btnPiedra);
        btnPapel = (ImageButton) findViewById(R.id.btnPapel);
        btnTijeras = (ImageButton) findViewById(R.id.btnTijeras);

        tvScoreP1 = (TextView) findViewById(R.id.scoreJugador1);
        tvScoreP2 = (TextView) findViewById(R.id.scoreJugador2);
        nameP1 = (TextView) findViewById(R.id.nomJugador1);
        nameP2 = (TextView) findViewById(R.id.nomJugador2);

        tvScoreP1.setText(getString(R.string.TextViewPuntuacion) + p1Score);
        tvScoreP2.setText(getString(R.string.TextViewPuntuacion) + p2Score);

        Random random = new Random();

        btnPiedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if("Clasico".equals(intent.getStringExtra("ModoJuego"))) {
                    imgP1.setImageResource(R.drawable.piedra);
                    int player1 = 1;
                    int player2 = random.nextInt(2) + 1;
                    int result = checkResults(player1, player2);
                    results(result);
                } else {
                    if(turno == 0) {
                        player1 = 1;
                        turno++;
                    } else {
                        player2 = 1;
                        switch (player1){
                            case 1:
                                imgP1.setImageResource(R.drawable.piedra);
                                break;
                            case 2:
                                imgP1.setImageResource(R.drawable.papel);
                                break;
                            case 3:
                                imgP1.setImageResource(R.drawable.tijeras);
                                break;
                        }
                        int result = checkResults(player1, player2);
                        turno = 0;
                        results(result);
                    }
                }
            }
        });
        btnPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if("Clasico".equals(intent.getStringExtra("ModoJuego"))) {
                    imgP1.setImageResource(R.drawable.papel);
                    int player1 = 2;
                    int player2 = random.nextInt(2) + 1;
                    int result = checkResults(player1, player2);
                    results(result);
                } else {
                    if(turno == 0) {
                        player1 = 2;
                        turno++;
                    } else {
                        player2 = 2;
                        switch (player1){
                            case 1:
                                imgP1.setImageResource(R.drawable.piedra);
                                break;
                            case 2:
                                imgP1.setImageResource(R.drawable.papel);
                                break;
                            case 3:
                                imgP1.setImageResource(R.drawable.tijeras);
                                break;
                        }
                        int result = checkResults(player1, player2);
                        turno = 0;
                        results(result);
                    }
                }
            }
        });
        btnTijeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if("Clasico".equals(intent.getStringExtra("ModoJuego"))) {
                    imgP1.setImageResource(R.drawable.tijeras);
                    int player1 = 3;
                    int player2 = random.nextInt(2) + 1;
                    int result = checkResults(player1, player2);
                    results(result);
                } else {
                    if(turno == 0) {
                        player1 = 3;
                        turno++;
                    } else {
                        player2 = 3;
                        switch (player1){
                            case 1:
                                imgP1.setImageResource(R.drawable.piedra);
                                break;
                            case 2:
                                imgP1.setImageResource(R.drawable.papel);
                                break;
                            case 3:
                                imgP1.setImageResource(R.drawable.tijeras);
                                break;
                        }
                        int result = checkResults(player1, player2);
                        turno = 0;
                        results(result);
                    }
                }
            }
        });
    }

    public int checkResults(int player1, int player2) {
        int result = 0;
        switch(player1) {
            case 1:
                // El jugador ha elegido piedra
                switch (player2) {
                    case 1:
                        imgP2.setImageResource(R.drawable.piedra);
                        return result = 2;
                    case 2:
                        imgP2.setImageResource(R.drawable.papel);
                        return result = 0;
                    case 3:
                        imgP2.setImageResource(R.drawable.tijeras);
                        return result = 1;

                }
            case 2:
                // El jugador ha elegido papel
                switch (player2) {
                    case 1:
                        imgP2.setImageResource(R.drawable.piedra);
                        return result = 1;

                    case 2:
                        imgP2.setImageResource(R.drawable.papel);
                        return result = 2;

                    case 3:
                        imgP2.setImageResource(R.drawable.tijeras);
                        return result = 0;
                }
            case 3:
                // El jugador ha elegido tijera
                switch (player2) {
                    case 1:
                        imgP2.setImageResource(R.drawable.piedra);
                        return result = 0;
                    case 2:
                        imgP2.setImageResource(R.drawable.papel);
                        return result = 1;
                    case 3:
                        imgP2.setImageResource(R.drawable.tijeras);
                        return result = 2;
                }
        }
        return result;
    }

    public void results(int result){
        switch (result){
            case 0:
                nGames++;
                p2Score++;
                tvScoreP2.setText(getString(R.string.TextViewPuntuacion) + p2Score);
                try {
                    endGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 1:
                nGames++;
                p1Score++;
                tvScoreP1.setText(getString(R.string.TextViewPuntuacion) + p1Score);
                try {
                    endGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }                break;
            case 2:
                Toast.makeText(Partida.this, "Empate", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void endGame() throws IOException {
        if (nGames == 3 || p1Score == 2 || p2Score == 2){
            String query = "INSERT INTO games(player1, scoreP1, player2, ScoreP2) VALUES(?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindString(1, nameP1.getText().toString());
            statement.bindString(2, Integer.toString(p1Score));
            statement.bindString(3, nameP2.getText().toString());
            statement.bindString(4, Integer.toString(p2Score));
            long result = statement.executeInsert();
            if (result != -1) {
                Toast.makeText(Partida.this, "Fin de partida", Toast.LENGTH_SHORT).show();
                // Creas el dialogo con un reto aleatorio de retos.txt
                AlertDialog.Builder builder = new AlertDialog.Builder(Partida.this).setCancelable(false);
                TextView textView = new TextView(this);
                String texto = readRandomLine();

                Intent intentExtras = getIntent();
                if (intentExtras.getStringExtra("ModoJuego").equals("Clasico")) {
                    if (p1Score>p2Score){
                        textView.setText("Felicidades " + nameP1.getText().toString() + ", has ganado");
                    } else {
                        textView.setText("Felicidades " + nameP2.getText().toString() + " has ganado");
                    }
                } else {
                    if (p1Score>p2Score){
                        textView.setText("Felicidades " + nameP1.getText().toString() + ", has ganado.\n"
                                + nameP2.getText().toString() + " ahora deberas cumplir el siguiente reto:\n\n"
                                + texto);
                    } else {
                        textView.setText("Felicidades " + nameP2.getText().toString() + ", has ganado.\n"
                                + nameP1.getText().toString() + " ahora deberas cumplir el siguiente reto:\n\n"
                                + texto);
                    }
                }
                builder.setView(textView);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Partida.this, ModoJuego.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(Partida.this, "Error al guardar el resultado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Partida.this, ModoJuego.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private String readRandomLine(){
        String randomLine = "";
        try {
            InputStream inputStream = getAssets().open("retos.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int numLines = 0;
            while (reader.readLine() != null) {
                numLines++;
            }

            Random random = new Random();
            int randomLineNum = random.nextInt(numLines);

            inputStream.reset();

            for (int i = 0; i < randomLineNum; i++) {
                randomLine = reader.readLine();
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return randomLine;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bitmap imagen1 = ((BitmapDrawable) imgP1.getDrawable()).getBitmap();
        Bitmap imagen2 = ((BitmapDrawable) imgP2.getDrawable()).getBitmap();
        TextView titulo = (TextView) findViewById(R.id.titulo);

        outState.putString("Title", titulo.getText().toString());
        outState.putString("ScoreName", getString(R.string.TextViewPuntuacion));

        outState.putParcelable("image1", imagen1);
        outState.putParcelable("image2", imagen2);

        outState.putInt("Score1", p1Score);
        outState.putInt("Score2", p2Score);

        outState.putString("Name1", nameP1.getText().toString());
        outState.putString("Name2", nameP2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bitmap imagen1 = outState.getParcelable("image1");
        Bitmap imagen2 = outState.getParcelable("image2");
        TextView titulo = (TextView) findViewById(R.id.titulo);

        titulo.setText(outState.getString("Title"));

        imgP1.setImageBitmap(imagen1);
        imgP2.setImageBitmap(imagen2);

        p1Score = outState.getInt("Score1");
        p2Score = outState.getInt("Score2");

        tvScoreP1.setText(outState.getString("ScoreName") + Integer.toString(outState.getInt("Score1")));
        tvScoreP2.setText(outState.getString("ScoreName") + Integer.toString(outState.getInt("Score2")));

        nameP1.setText(outState.getString("Name1"));
        nameP2.setText(outState.getString("Name2"));
    }
}