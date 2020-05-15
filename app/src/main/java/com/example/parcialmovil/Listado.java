package com.example.parcialmovil;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class Listado extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ListadoDeCanciones adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar(view);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_cancion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListadoDeCanciones(new Controller().buscarTodos(this), this);
        recyclerView.setAdapter(adapter);

    }
    public void agregar(View view){
        Intent intent = new Intent(this, AddCancion.class);
        startActivity(intent);
    }

}
