package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class Historial extends AppCompatActivity {

    SQLiteDatabase db;
    ListView listView;
    Cursor cursor;
    CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial2);

        listView = findViewById(R.id.listaHistorial);
        DBManager dbHelper = new DBManager(this);
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM games", null);
        cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_element,
                cursor,
                new String[] {"player1", "scoreP1", "player2", "scoreP2"},
                new int[] {R.id.histP1Name, R.id.histP1Score, R.id.histP2Name, R.id.histP2Score},
                0
        );
        listView.setAdapter(cursorAdapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}