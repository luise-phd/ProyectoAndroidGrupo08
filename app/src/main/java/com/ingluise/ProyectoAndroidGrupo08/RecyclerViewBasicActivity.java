package com.ingluise.ProyectoAndroidGrupo08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class RecyclerViewBasicActivity extends AppCompatActivity {
    private final int tam = 100;
    private final int cols = 1;

    private CustomAdapter ca;
    private RecyclerView rv;

    private LinearLayoutManager llm;
    private GridLayoutManager glm;

    private String[] mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_basic);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv = findViewById(R.id.rv_list);
        mDataset = initDataset();
        Toast.makeText(this, ""+mDataset[49], Toast.LENGTH_SHORT).show();
        ca = new CustomAdapter(mDataset);
        rv.setAdapter(ca);
        setRecyclerViewLayoutManager();
    }

    private String[] initDataset() {
        mDataset = new String[tam];
        for (int i=0; i < tam; i++) {
            mDataset[i] = "Este es el elemento # " + i;
        }
        return mDataset;
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        glm = new GridLayoutManager(this, cols);

//        rv.setLayoutManager(llm);
        rv.setLayoutManager(glm);
        rv.scrollToPosition(scrollPosition);
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