package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Historico extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase db;
    Cursor cursor;
    CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        listView = findViewById(R.id.list_historico);
        DBManager dbHelper = new DBManager(this);
        db = dbHelper.getWritableDatabase();
        String[] columnas = {"player1", "scoreP1", "player2", "scoreP2"};
        Cursor cursor = db.query("games", columnas, null, null, null, null, null);
        cursorAdapter = new MiAdaptador(
                Historico.this,
                cursor);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*cursor.close();
        db.close();*/
    }
}