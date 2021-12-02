package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class EncuestaActivity extends AppCompatActivity {
    private TextView tv1;
    private EditText et1, et2, et3, et4;
    private Spinner sp1;
    private Switch sw1;
    private CheckBox ch1, ch2, ch3, ch4, ch5, ch6;
    private RadioButton rb1, rb2, rb3;
    private SeekBar sk1;
    private TextInputEditText ti1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et1 = (EditText) findViewById(R.id.editTextTextPersonName);
        et2 = (EditText) findViewById(R.id.editTextDate);
        et3 = (EditText) findViewById(R.id.editTextPhone);
        et4 = (EditText) findViewById(R.id.editTextTextEmailAddress);
        tv1 = (TextView) findViewById(R.id.textView14);
        ti1 = findViewById(R.id.textInputEditText);
        sp1 = (Spinner) findViewById(R.id.spinner);
        sw1 = (Switch) findViewById(R.id.switch1);
        ch1 = (CheckBox) findViewById(R.id.checkBox);
        ch2 = (CheckBox) findViewById(R.id.checkBox2);
        ch3 = (CheckBox) findViewById(R.id.checkBox3);
        ch4 = (CheckBox) findViewById(R.id.checkBox4);
        ch5 = (CheckBox) findViewById(R.id.checkBox5);
        ch6 = (CheckBox) findViewById(R.id.checkBox6);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        sk1 = (SeekBar) findViewById(R.id.seekBar);
        sk1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv1.setText(""+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void obtenerDatos(View view) {
        String nom = "Nombre: " + et1.getText().toString();
        String fnac = "Fecha de nacimiento: " + et2.getText().toString();
        String tel = "Teléfono: " + et3.getText().toString();
        String email = "Correo electrónico: " + et4.getText().toString();
        String dir = "Dirección: " + ti1.getText().toString();
        String niv_ing = "Nivel en inglés: " + sp1.getSelectedItem();
        String gus_prog = "¿Te gusta programar?: ";
        if(sw1.isChecked()) {
            gus_prog += "Si";
        }
        else {
            gus_prog += "No";
        }
        String leng = "Lenguajes de programación: ";
        if(ch1.isChecked()) {
            leng += ch1.getText() + ", ";
        }
        if(ch2.isChecked()) {
            leng += ch2.getText() + ", ";
        }
        if(ch3.isChecked()) {
            leng += ch3.getText() + ", ";
        }
        if(ch4.isChecked()) {
            leng += ch4.getText() + ", ";
        }
        if(ch5.isChecked()) {
            leng += ch5.getText() + ", ";
        }
        if(ch6.isChecked()) {
            leng += ch6.getText();
        }
        String tiempo_exp = "Tiempo de experiencia: ";
        if(rb1.isChecked()) {
            tiempo_exp += rb1.getText() + " año";
        }
        if(rb2.isChecked()) {
            tiempo_exp += rb2.getText() + " años";
        }
        if(rb3.isChecked()) {
            tiempo_exp += rb3.getText() + " años";
        }
        String niv_sat = "Nivel de satisfacción: " + tv1.getText();
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Datos")
            .setMessage(nom + "\n" + fnac + "\n" + tel + "\n" + email + "\n" + dir +
                    "\n" + niv_ing + "\n" + gus_prog + "\n" + leng +
                    "\n" + tiempo_exp + "\n" + niv_sat)
            .setPositiveButton("Aceptar", null).show();
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