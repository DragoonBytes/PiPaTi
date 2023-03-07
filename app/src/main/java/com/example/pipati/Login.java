package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    SQLiteDatabase db;
    AlertDialog dialogRegistro;
    Button btnLogin, btnRegistro;
    EditText editTextUser, editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DBManager dbManager = new DBManager(this);
        db = dbManager.getWritableDatabase();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        editTextUser = (EditText) findViewById(R.id.textInputUsuario);
        editTextPass = (EditText) findViewById(R.id.textInputContrasenia);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setView(R.layout.dialog_registro);
                builder.setPositiveButton("Iniciar sesión", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Gestionamos el registro del usuario
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                dialogRegistro = builder.create();

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

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public boolean checkLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE nomUser = ? AND pass = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }
}