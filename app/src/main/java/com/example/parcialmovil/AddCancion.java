package com.example.parcialmovil;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddCancion extends AppCompatActivity {

    public static Activity my_activity;
    public static TextView txt_nombre_cancion, txt_artista_cancion, txt_Album, txt_Duracion_Cancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cancion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt_nombre_cancion = (TextView) findViewById(R.id.txt_nombre_cancion);
        txt_artista_cancion = (TextView) findViewById(R.id.txt_artista_cancion);
        txt_Album = (TextView) findViewById(R.id.txt_Album);
        txt_Duracion_Cancion = (TextView) findViewById(R.id.txt_Duracion_Cancion);
        my_activity = this;


    }

    public void BuscarCancion(View view){
        TextView search_cancion = findViewById(R.id.search_cancion);
        String nombre_cancion = search_cancion.getText().toString();
        HttpBuscar http = new HttpBuscar(this, nombre_cancion);
        http.execute();
    }

    public void GuardarCancion(View view){

        String nombre_cancion = txt_nombre_cancion.getText().toString();
        String artista = txt_artista_cancion.getText().toString();
        String album = txt_Album.getText().toString();
        String duracion = txt_Duracion_Cancion.getText().toString();
        Clase_Cancion c =new Clase_Cancion();
        c.titulo = nombre_cancion;
        c.artista = artista;
        c.album = album;
        c.duracion = duracion;
        String respuesta = new Controller().guardar(this, c);
        Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
        HttpListar http = new HttpListar(this);
        http.execute();
    }



}
