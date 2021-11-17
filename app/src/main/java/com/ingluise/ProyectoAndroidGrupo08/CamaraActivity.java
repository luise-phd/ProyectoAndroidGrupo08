package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import android.util.Base64;

public class CamaraActivity extends AppCompatActivity {
    private EditText txtDescripcion;
    private ImageButton btnCamara;
    private ImageView imgView;

    private MyDBSQLiteHelper admin;
    private SQLiteDatabase db;
    private ContentValues cv;
    private Cursor filas;

    private Bitmap bmp, bmp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        admin = new MyDBSQLiteHelper(this, vars.nomDB, null, vars.version);

        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imageView);
        imgView.setImageDrawable(null);
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara(view);
            }
        });
    }

    public void abrirCamara(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_imagenes, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.mnu_guardar) {
            String des = txtDescripcion.getText().toString();
            if (!des.equals("")) {
                if (imgView.getDrawable() != null) {
                    String imgCodificada = "";
                    imgView.buildDrawingCache(true);
                    bmp = imgView.getDrawingCache(true);

                    bmp2 = Bitmap.createScaledBitmap(bmp, imgView.getWidth(), imgView.getHeight(), true);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp2.compress(Bitmap.CompressFormat.PNG, 25, baos);
                    byte[] imagen = baos.toByteArray();
                    imgCodificada = Base64.encodeToString(imagen, Base64.DEFAULT);

                    //Almacenar los datos
                    db = admin.getWritableDatabase();
                    cv = new ContentValues();
                    cv.put("descripcion", des);
                    cv.put("img", imgCodificada);
                    long reg = db.insert("imagenes", null, cv);
                    if (reg != -1) {
                        Toast.makeText(this, "Registro almacenado", Toast.LENGTH_SHORT).show();
                        txtDescripcion.setText("");
                        imgView.setImageBitmap(null);
                        imgView.destroyDrawingCache();
                        imgView.setImageDrawable(null);
                    } else
                        Toast.makeText(this, "El registro no se pudo almacenar", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "Por favor, capture la fotografía", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Por favor, ingrese la descripción", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.mnu_buscar) {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.input_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptsView);
            final EditText et1 = promptsView.findViewById(R.id.input_buscar_img);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Bitmap decodedBytes = null;
                            byte[] decodeString;
                            db = admin.getWritableDatabase();
                            filas = db.rawQuery("SELECT * FROM imagenes WHERE descripcion='"+et1.getText().toString()+"'", null);
                            String des = "";
                            if (filas.moveToFirst()) {
                                des = filas.getString(1);
                                if (!filas.getString(2).equals("")) {
                                    decodeString = Base64.decode(filas.getString(2), Base64.DEFAULT);
                                    decodedBytes = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
                                }
                                txtDescripcion.setText(des);
                                imgView.setImageBitmap(decodedBytes);
                            } else
                                Toast.makeText(getApplicationContext(), "El registro no existe", Toast.LENGTH_SHORT).show();
                            db.close();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(menuItem);
    }
}