package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Locale;

public class Historico extends AppCompatActivity {

    ListView listView;
    Button btnDelete;
    ImageButton btnBack;
    SQLiteDatabase db;
    CursorAdapter cursorAdapter;
    SharedPreferences sharedPreferences;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenemos las preferencias
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Si hay cambios en las preferencias se actualizara
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Historico.this);
                // Si se ha cambiado el color de los botones se actualiza
                if (key.equals("button_color")) {
                    // Obtener el nuevo color de los botones
                    String colorValue = preferences.getString("button_color", "#F46666"); // El valor por defecto es azul
                    btnDelete.setBackgroundColor(Color.parseColor(colorValue));

                    // Si se ha cambiado el idioma se actualiza
                } else if (key.equals("language")) {
                    String language = preferences.getString("language", "es");
                    Locale locale = new Locale(language);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.setLocale(locale);
                    getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                }
                recreate();
            }
        });

        String language = sharedPreferences.getString("language", "es");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_historico);

        // Inicializacion de las variables
        listView = (ListView) findViewById(R.id.list_historico);
        btnBack = (ImageButton) findViewById(R.id.historicoFlechaAtras);
        btnDelete = (Button) findViewById(R.id.btnBorrarHistorial);

        DBManager dbHelper = new DBManager(this);

        // Conseguimos la tabla "games" de la BD para mostrarla en el historial
        db = dbHelper.getWritableDatabase();
        String[] columnas = {"_id", "player1", "scoreP1", "player2", "scoreP2"};
        Cursor cursor = db.query("games", columnas, null, null, null, null, null);
        cursorAdapter = new AdaptadorLinea(Historico.this, cursor);
        listView.setAdapter(cursorAdapter);


        // Boton que carga la actividad anterior
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Historico.this, MenuPrincipal.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(intent);
                finish();
            }
        });

        // Boton que elimina todas las partidas guardadas de la base de datos
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
        loadPreferences();
    }

    public void createNotification(){
        /**
         * Extraido de: https://developer.android.com/training/notify-user/build-notification?hl=es-419
         * Modificado por: Hugo Robles, para adaptarlo a mi aplicacion
         */

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

    private void createNotificationChannel() {
        /**
         * Extraido de: https://developer.android.com/training/notify-user/build-notification?hl=es-419
         * Modificado por: Hugo Robles, para adaptarlo a mi aplicacion
         */

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("ChannelID", "Canal", importance);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void loadPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Historico.this);
        String colorValue = preferences.getString("button_color", "#F46666"); // El color por defecto es "Rojo"
        btnDelete.setBackgroundColor(Color.parseColor(colorValue));


        String language = preferences.getString("language", "es"); // El idioma por defecto es "Español"
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}