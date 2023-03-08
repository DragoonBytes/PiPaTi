package com.example.pipati;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.SQLData;

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
    }
    public boolean checkLogin(String nomUser, String pass) {
        String query = "SELECT * FROM users WHERE nomUser = ? AND pass = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nomUser, pass});
        boolean result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }

    public boolean checkSignIn(String pass, String pass2){
        boolean result = false;
        if (pass.equals(pass2)){
            result = true;
        }
        return result;
    }

}