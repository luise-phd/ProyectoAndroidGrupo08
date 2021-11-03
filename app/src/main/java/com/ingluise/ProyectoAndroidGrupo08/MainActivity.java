package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toast.makeText(this, "Método onCreate()", Toast.LENGTH_LONG).show();
        Log.i("Información", "Esto es una Prueba");
    }

    public void goToActivityProducto(View view) {
        Intent newIntent = new Intent(this, ProductoActivity.class);
//        newIntent.putExtra("msg", "Hola MinTIC");
//        newIntent.putExtra("year", 2021);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.mnu_producto) {
            Intent newIntent = new Intent(this, ProductoActivity.class);
            newIntent.putExtra("msg", "Hola MinTIC");
            newIntent.putExtra("year", 2021);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
        }
        else if(id == R.id.mnu_categoria) {
            Intent newIntent = new Intent(this, CategoriaActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
        }
        else if(id == R.id.mnu_marca) {
            Intent newIntent = new Intent(this, MarcaActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Información")
                    .setMessage("¿Desea cerrar la aplicación?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            MainActivity.this.finish();
                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onStart() {
        super.onStart();
//        Toast.makeText(this, "Método onStart()", Toast.LENGTH_LONG).show();
    }

    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, "Método onResume()", Toast.LENGTH_LONG).show();
    }

    protected void onPause() {
        super.onPause();
//        Toast.makeText(this, "Método onPause()", Toast.LENGTH_LONG).show();
    }

    protected void onStop() {
        super.onStop();
//        Toast.makeText(this, "Método onStop()", Toast.LENGTH_LONG).show();
    }

    protected void onRestart() {
        super.onRestart();
//        Toast.makeText(this, "Método onRestart()", Toast.LENGTH_LONG).show();
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Información", "Método onDestroy()");
    }
}