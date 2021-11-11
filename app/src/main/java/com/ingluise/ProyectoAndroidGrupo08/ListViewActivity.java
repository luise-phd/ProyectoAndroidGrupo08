package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private MyDBSQLiteHelper admin;
    private SQLiteDatabase db;
    private Cursor filas;

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        admin = new MyDBSQLiteHelper(this, vars.nomDB, null, vars.version);

        lv = findViewById(R.id.listView);
        ArrayList<String> listado = new ArrayList<String>();
//        listado.add("Uno");
//        listado.add("Dos");
//        for(int i=3; i<=20; i++) {
//            listado.add("Elemento " + i);
//        }

        Bundle extras = getIntent().getExtras();
        String nomTabla = extras.getString("nomTabla");

        //Convertir la primera letra a mayusculas
        String tabla = nomTabla.substring(0,1).toUpperCase() + nomTabla.substring(1);

        //Cambiar título de la actividad
        setTitle(tabla);

        db = admin.getReadableDatabase();
        if (nomTabla.equals("producto")) {
            filas = db.rawQuery("SELECT * FROM producto", null);
            while (filas.moveToNext()) {
                listado.add(filas.getInt(0) + "-" + filas.getString(1) + "\n" + filas.getString(2));
            }
            db.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, listado);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(getApplicationContext(), "Pos: "+pos+" - "+lv.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OnBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == android.R.id.home) {
            OnBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}