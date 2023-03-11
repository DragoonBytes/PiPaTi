package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Historico extends AppCompatActivity {

    ListView listView;
    ImageButton btnBack;
    SQLiteDatabase db;
    Cursor cursor;
    CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        listView = (ListView) findViewById(R.id.list_historico);
        btnBack = (ImageButton) findViewById(R.id.historicoFlechaAtras);

        DBManager dbHelper = new DBManager(this);
        db = dbHelper.getWritableDatabase();
        String[] columnas = {"_id", "player1", "scoreP1", "player2", "scoreP2"};
        Cursor cursor = db.query("games", columnas, null, null, null, null, null);
        cursorAdapter = new MiAdaptador(
                Historico.this,
                cursor);
        listView.setAdapter(cursorAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Historico.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }
}