package com.ingluise.ProyectoAndroidGrupo08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductoFirebaseActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private EditText et1, et2, et3;
    private Producto producto;
    private String productos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_firebase);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et1 = findViewById(R.id.input_ref);
        et2 = findViewById(R.id.input_producto);
        et3 = findViewById(R.id.input_precio);
    }

    public void testCrash(View view) {
        throw new RuntimeException("Test Crash"); // Force a crash
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
            String ref = et1.getText().toString();
            String nom = et2.getText().toString();
            String pre = et3.getText().toString();
            if (!ref.equals("") && !nom.equals("") && !pre.equals("")) {
                producto = new Producto(ref, nom, Integer.parseInt(pre));
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference().child("producto").push();
                myRef.setValue(producto);
                et1.setText("");
                et2.setText("");
                et3.setText("");
                Toast.makeText(this, "Registro almacenado", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.mnu_Listar) {
            myRef = FirebaseDatabase.getInstance().getReference();
            myRef.child("producto").orderByChild("ref").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nom = "", ref = "";
                    int pre = 0;
                    productos = "";
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Producto producto = snapshot.getValue(Producto.class);
                        ref = producto.getRef();
                        nom = producto.getNom();
                        pre = producto.getPrecio();
                        productos += ref + " - " + nom + " - " + pre + "\n";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Productos")
                    .setMessage(productos)
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
        else if(id == R.id.mnu_eliminar) {
            String ref = et1.getText().toString();
            if (!ref.equals("")) {
                myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("producto").orderByChild("ref").equalTo(ref).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() > 0) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                dataSnapshot.getRef().removeValue();
                            }
                            Toast.makeText(getApplicationContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                            et1.setText("");
                        } else
                            Toast.makeText(getApplicationContext(), "El producto no existe", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else
                Toast.makeText(this, "Por favor, ingrese la referencia", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.mnu_buscar) {
            String ref = et1.getText().toString();
            if (!ref.equals("")) {
                myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("producto").orderByChild("ref").equalTo(ref).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Producto producto = dataSnapshot.getValue(Producto.class);
                            et2.setText(producto.getNom());
                            et3.setText("" + producto.getPrecio());
                        }
                        if (snapshot.getChildrenCount() == 0) {
                            Toast.makeText(getApplicationContext(), "El producto no existe", Toast.LENGTH_SHORT).show();
                            et1.requestFocus();
                            et2.setText("");
                            et3.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {
                Toast.makeText(this, "Por favor, ingrese la referencia", Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.mnu_editar) {
            String ref = et1.getText().toString();
            String nom = et2.getText().toString();
            String pre = et3.getText().toString();
            if (!ref.equals("")) {
                myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("producto").orderByChild("ref").equalTo(ref).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String key = "";
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            key = dataSnapshot.getKey();
                        }
                        myRef.child("producto").child(key).child("nom").setValue(nom);
                        myRef.child("producto").child(key).child("precio").setValue(Integer.parseInt(pre));
                        Toast.makeText(getApplicationContext(), "Producto editado", Toast.LENGTH_SHORT).show();
                        et1.requestFocus();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {
                Toast.makeText(this, "Por favor, ingrese la referencia", Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.mnu_limpiar) {
            et1.setText("");
            et2.setText("");
            et3.setText("");
        }

        return super.onOptionsItemSelected(menuItem);
    }
}