package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.se.omapi.Channel;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Historico extends AppCompatActivity {

    ListView listView;
    Button btnDelete;
    ImageButton btnBack;
    SQLiteDatabase db;
    Cursor cursor;
    CursorAdapter cursorAdapter;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        listView = (ListView) findViewById(R.id.list_historico);
        btnBack = (ImageButton) findViewById(R.id.historicoFlechaAtras);
        btnDelete = (Button) findViewById(R.id.btnBorrarHistorial);

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

        // Este boton elimina el resultado de las partidas guardadas en la base de datos
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "DELETE FROM games";
                SQLiteStatement statement = db.compileStatement(query);
                long result = statement.executeInsert();
                if (result != -1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        createNotificationChannel();
                        createNotification();
                        recreate();
                    } else {
                        createNotification();
                        recreate();
                    }
                }
            }
        });
    }

    // Funcion que crea una notificación que indica si se ha borrado el historial sin problemas
    public void createNotification(){
        // Crear un intent para abrir la aplicación cuando se hace clic en la notificación
        Intent intent = new Intent(Historico.this, MenuPrincipal.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(Historico.this, 0, intent, 0);

        // Crear la notificación usando NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Historico.this, "ChannelID")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Icono de la notificación
                .setContentTitle("Borrado Historial") // Título de la notificación
                .setContentText("El historial se ha borrado con exito") // Texto de la notificación
                .setContentIntent(pendingIntent) // Intent al hacer clic en la notificación
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // Prioridad de la notificación

        // Mostrar la notificación usando NotificationManager
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    // Funcion que crea un canal para las notificaciones, obligatorio para versiones superiores a la 26
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("ChannelID", "Canal", importance);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}