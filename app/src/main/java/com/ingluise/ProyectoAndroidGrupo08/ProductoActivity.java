package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ProductoActivity extends AppCompatActivity {
    private MyDBSQLiteHelper admin;
    private SQLiteDatabase db;
    private ContentValues cv;
    private Cursor filas;

    private EditText et1, et2;
    private Spinner sp1, sp2, sp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        //Activar soporte para la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getParametros();

        admin = new MyDBSQLiteHelper(this, vars.nomDB, null, vars.version);

        et1 = findViewById(R.id.input_nombre);
        et2 = findViewById(R.id.input_descripcion);
        sp1 = findViewById(R.id.spinner_categoria);
        sp2 = findViewById(R.id.spinner_marca);
        sp3 = findViewById(R.id.spinner_lista_proveedores);
    }

    public void agregarDatos(View view) {
        String nom = et1.getText().toString();
        String des = et2.getText().toString();
        String cat = sp1.getSelectedItem().toString();
        String mar = sp2.getSelectedItem().toString();
        String pro = sp3.getSelectedItem().toString();

        if (!nom.equals("") && !des.equals("") &&
                !cat.equals("Seleccione...") && !mar.equals("") && !pro.equals("")) {
            db = admin.getWritableDatabase();
            cv = new ContentValues();
            cv.put("nombre", nom);
            cv.put("descripcion", des);
            cv.put("categoria", cat);
            cv.put("marca", mar);
            cv.put("proveedor", pro);
            long reg = db.insert("producto", null, cv);
            if (reg != -1) {
                Toast.makeText(this, "Registro almacenado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et1.requestFocus();
                et2.setText("");
                sp1.setSelection(0);
                sp2.setSelection(0);
                sp3.setSelection(0);
            } else
                Toast.makeText(this, "El registro no se pudo almacenar", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
    }

    public void listarDatos(View view) {
//        db = admin.getReadableDatabase();
//        filas = db.rawQuery("SELECT * FROM producto", null);
//        while (filas.moveToNext()) {
//            Toast.makeText(this, filas.getInt(0) + "-" + filas.getString(1) + "-" + filas.getString(2), Toast.LENGTH_SHORT).show();
//        }
//        db.close();
        Intent intent = new Intent(this, ListViewActivity.class);
        intent.putExtra("nomTabla", "producto");
        startActivity(intent);
    }

    public void eliminarDatos(View view) {
        String nom = et1.getText().toString();
        String des = et2.getText().toString();

        db = admin.getWritableDatabase();
        if (!nom.equals("")) {
//            int reg = db.delete("producto", "nombre=" + "'" + nom + "'", null);
            String[] args = new String[]{nom};
            int reg = db.delete("producto", "nombre=?", args);
            if (reg == 0)
                Toast.makeText(this, "El registro no se pudo eliminar", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Por favor, ingrese el nombre del producto", Toast.LENGTH_SHORT).show();
    }

    public void editarDatos(View view) {
        String nom = et1.getText().toString();
        String des = et2.getText().toString();
        String cat = sp1.getSelectedItem().toString();
        String mar = sp2.getSelectedItem().toString();
        String pro = sp3.getSelectedItem().toString();

        db = admin.getWritableDatabase();
        cv = new ContentValues();
        cv.put("descripcion", des);
        cv.put("categoria", cat);
        cv.put("marca", mar);
        cv.put("proveedor", pro);
        int reg = db.update("producto", cv, "nombre=" + "'" + nom + "'", null);
        if (reg == 0)
            Toast.makeText(this, "El registro no se pudo editar", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Registro editado", Toast.LENGTH_SHORT).show();
            et1.setText("");
            et2.setText("");
            sp1.setSelection(0);
            sp2.setSelection(0);
            sp3.setSelection(0);
        }
    }

    public void buscarDatos(View view) {
        String nom = et1.getText().toString();

        if(!nom.equals("")) {
            db = admin.getReadableDatabase();
            filas = db.rawQuery("SELECT * FROM producto WHERE nombre='" + nom + "'", null);
            if (filas.moveToFirst()) {
                et2.setText(filas.getString(2));
                Adapter adapter = sp1.getAdapter();
                for (int i=0; i<adapter.getCount(); i++) {
                    if(filas.getString(3).equals(adapter.getItem(i))) {
//                        Toast.makeText(this, ""+adapter.getItem(i), Toast.LENGTH_SHORT).show();
                        sp1.setSelection(i);
                    }
                }
                adapter = sp2.getAdapter();
                for (int i=0; i<adapter.getCount(); i++) {
                    if(filas.getString(4).equals(adapter.getItem(i))) {
                        sp2.setSelection(i);
                    }
                }
                adapter = sp3.getAdapter();
                for (int i=0; i<adapter.getCount(); i++) {
                    if(filas.getString(5).equals(adapter.getItem(i))) {
                        sp3.setSelection(i);
                    }
                }
            } else {
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
        else
            Toast.makeText(this, "Por favor, ingrese el nombre del producto", Toast.LENGTH_SHORT).show();
    }

    public void limpiarDatos(View view) {
        et1.setText("");
        et2.setText("");
    }

    public void goToActivityMain(View view) {
        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);
    }

    public void getParametros() {
//        Bundle extras = getIntent().getExtras();
//        String msg = extras.getString("msg");
//        Integer year = extras.getInt("year");
//        Toast.makeText(this, msg + " " + year, Toast.LENGTH_SHORT).show();
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