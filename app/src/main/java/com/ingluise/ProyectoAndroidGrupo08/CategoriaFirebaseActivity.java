package com.ingluise.ProyectoAndroidGrupo08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CategoriaFirebaseActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private EditText et1;
    String categorias = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_firebase);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et1 = findViewById(R.id.input_categoria);
        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hola mundo2!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crud, menu);
        return true;
    }

    public void OnBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == android.R.id.home) {
            OnBackPressed();
        }

        if(id == R.id.mnu_agregar) {
            String des = et1.getText().toString();
            if (!des.equals("")) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("categoria").push();
                myRef.setValue(des);
                et1.setText("");
                Toast.makeText(this, "Registro almacenado", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Por favor, ingrese la categoría", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.mnu_Listar) {
            myRef = FirebaseDatabase.getInstance().getReference();
            myRef.child("categoria").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String cat = "";
                    categorias = "";
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        cat = snapshot.getValue(String.class);
                        categorias += cat + "\n";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Categorías")
                    .setMessage(categorias)
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
        else if(id == R.id.mnu_eliminar) {

        }
        else if(id == R.id.mnu_buscar) {

        }
        else if(id == R.id.mnu_editar) {

        }
        else if(id == R.id.mnu_limpiar) {
            et1.setText("");
        }

        return super.onOptionsItemSelected(menuItem);
    }
}