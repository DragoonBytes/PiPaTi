package com.example.pipati;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Ajustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        FragmentoAjustes fragmentoPreferencias = new FragmentoAjustes();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerAjustes, fragmentoPreferencias)
                .commit();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnAtrasAjustes:
                Intent intent = new Intent(Ajustes.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}