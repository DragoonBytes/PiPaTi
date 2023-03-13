package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.SQLData;
import java.util.Locale;

public class Login extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    SQLiteDatabase db;
    AlertDialog dialogRegistro;
    Button btnLogin, btnRegistro;
    EditText editTextUser, editTextPass;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        String language = sharedPreferences.getString("language", "es");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_login);

        DBManager dbManager = new DBManager(this);
        db = dbManager.getWritableDatabase();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        editTextUser = (EditText) findViewById(R.id.textInputUsuario);
        editTextPass = (EditText) findViewById(R.id.textInputContrasenia);

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setView(R.layout.dialog_registro);
        builder.setPositiveButton("Registrarse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editUsername = dialogRegistro.findViewById(R.id.edit_username);
                EditText editPassword = dialogRegistro.findViewById(R.id.edit_password);
                EditText editPassword2 = dialogRegistro.findViewById(R.id.edit_password2);
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                String password2 = editPassword2.getText().toString();

                if(checkSignIn(password, password2)) {
                    String query = "INSERT INTO users(nomUser, pass) VALUES(?, ?)";
                    SQLiteStatement statement = db.compileStatement(query);
                    statement.bindString(1, username);
                    statement.bindString(2, password);
                    long result = statement.executeInsert();
                    if (result != -1) {
                        Toast.makeText(Login.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Error al intentar registrarse", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        dialogRegistro = builder.create();

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRegistro.show();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUser.getText().toString();
                String password = editTextPass.getText().toString();
                if (checkLogin(username, password)) {
                    // Inicia sesión y muestra la actividad principal
                    Intent intent = new Intent(Login.this, MenuPrincipal.class);
                    startActivity(intent);
                } else {
                    // Muestra un mensaje de error
                    Toast.makeText(Login.this, "Datos incorrectos, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cargarPreferencias();
    }
    public boolean checkLogin(String nomUser, String pass) {
        String query = "SELECT * FROM users WHERE nomUser = ? AND pass = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nomUser, pass});
        boolean result = cursor.moveToFirst();
        cursor.close();
        //db.close();
        return result;
    }

    public boolean checkSignIn(String pass, String pass2){
        boolean result = false;
        if (pass.equals(pass2)){
            result = true;
        }
        return result;
    }

    private void cargarPreferencias(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        String colorValue = preferences.getString("button_color", "#F46666"); // El valor por defecto es azul
        btnLogin.setBackgroundColor(Color.parseColor(colorValue));
        btnRegistro.setBackgroundColor(Color.parseColor(colorValue));

        String language = preferences.getString("language", "es");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        if (key.equals("button_color")) {
            // Obtener el nuevo color de los botones
            String colorValue = preferences.getString("button_color", "#F46666"); // El valor por defecto es azul
            btnLogin.setBackgroundColor(Color.parseColor(colorValue));
            btnRegistro.setBackgroundColor(Color.parseColor(colorValue));
        }  else if (key.equals("language")) {
            String language = preferences.getString("language", "es");
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
        recreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("User", editTextUser.getText().toString());
        outState.putString("Password", editTextPass.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        editTextUser.setText(outState.getString("User"));
        editTextPass.setText(outState.getString("Password"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar el listener
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}