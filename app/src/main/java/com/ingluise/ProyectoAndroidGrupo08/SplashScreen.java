package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    private  static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bloquear orientación de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Ocultar barra de notificaciones
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //Ocultar ActionBar
        getSupportActionBar().hide();

        //Crear una tarea de temporización
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent newIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(newIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, SPLASH_SCREEN_DELAY);
    }
}