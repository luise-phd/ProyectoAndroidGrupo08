package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Método onCreate()", Toast.LENGTH_LONG).show();
        Log.i("Información", "Esto es una Prueba");
    }

    public void goToActivityProducto(View view) {
        Intent newIntent = new Intent(this, ProductoActivity.class);
        newIntent.putExtra("msg", "Hola MinTIC");
        newIntent.putExtra("year", 2021);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);
    }

    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Método onStart()", Toast.LENGTH_LONG).show();
    }

    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Método onResume()", Toast.LENGTH_LONG).show();
    }

    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Método onPause()", Toast.LENGTH_LONG).show();
    }

    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Método onStop()", Toast.LENGTH_LONG).show();
    }

    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Método onRestart()", Toast.LENGTH_LONG).show();
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Información", "Método onDestroy()");
    }
}