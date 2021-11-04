package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class ScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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