package com.example.pipati;

/**
 * Extraido de: https://developer.android.com/reference/android/widget/CursorAdapter
 * Modificado por: Hugo Robles para adaptarlo a mi aplicacion
 */

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdaptadorLinea extends CursorAdapter {

    public AdaptadorLinea(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_element, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView p1Name = (TextView) view.findViewById(R.id.histP1Name);
        TextView p1Score = (TextView) view.findViewById(R.id.histP1Score);
        TextView p2Name = (TextView) view.findViewById(R.id.histP2Name);
        TextView p2Score = (TextView) view.findViewById(R.id.histP2Score);

        String dataP1Name = cursor.getString(cursor.getColumnIndexOrThrow("player1"));
        p1Name.setText(dataP1Name);
        String dataP1Score = cursor.getString(cursor.getColumnIndexOrThrow("scoreP1"));
        p1Score.setText(dataP1Score);
        String dataP2Name = cursor.getString(cursor.getColumnIndexOrThrow("player2"));
        p2Name.setText(dataP2Name);
        String dataP2Score = cursor.getString(cursor.getColumnIndexOrThrow("scoreP2"));
        p2Score.setText(dataP2Score);
    }
}
