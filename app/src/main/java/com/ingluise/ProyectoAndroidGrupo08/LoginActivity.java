package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextView t1, t2;
    EditText et1, et2;
    ImageView iv1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Ocultar ActionBar
        getSupportActionBar().hide();

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView3);
        et1 = (EditText) findViewById(R.id.editTextTextPersonName);
        et2 = (EditText) findViewById(R.id.editTextTextPassword);
        iv1 = (ImageView) findViewById(R.id.imageView);
        b1 = (Button) findViewById(R.id.button7);
        b1.setText(R.string.iniciar_sesion);
        t1.setText("Proyecto\nGrupo 08");
        t1.setTextSize(20);
//        String link = "<a href='https://imaster.academy/course/view.php?id=1001&section=3'>iMaster</a>";
//        t2.setMovementMethod(LinkMovementMethod.getInstance());
        String texto = "Recordar contraseña";
        t2.setText(Html.fromHtml(texto));
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et1.getText().toString().equals("admin")) {
                    Toast.makeText(LoginActivity.this,
                            "Su contraseña es: admin",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void iniciarSesion(View view) {
        if(et1.getText().toString().equals("admin") && et2.getText().toString().equals("admin")) {
            Intent newIntent = new Intent(this, MainActivity.class);
            startActivity(newIntent);
            finish();
        }
        else {
            Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            et1.requestFocus();
        }
        if(et1.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Por favor, ingrese el usuario", Toast.LENGTH_SHORT).show();
            et1.requestFocus();
        }
        else if(et2.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Por favor, ingrese la contraseña", Toast.LENGTH_SHORT).show();
            et2.requestFocus();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Toast.makeText(this,
//                "Top: "+iv1.getTop()+
//                        "\nLeft: "+iv1.getLeft(),
//                Toast.LENGTH_SHORT).show();
        Toast.makeText(this,
                "Width: "+b1.getWidth()+
                        "\nHeight: "+b1.getHeight(),
                Toast.LENGTH_SHORT).show();
    }
}