package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Ocultar ActionBar
        getSupportActionBar().hide();

        TextView t1 = (TextView) findViewById(R.id.textView);
        t1.setText("Proyecto\nGrupo 08");
        t1.setTextSize(20);
        Toast.makeText(this, ""+t1.getTop(), Toast.LENGTH_SHORT).show();
    }
}