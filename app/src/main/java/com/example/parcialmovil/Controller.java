package com.example.parcialmovil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    public String guardar(Context context, Clase_Cancion cancion){
        try {
            CancionBD bd = new CancionBD(context, CancionBD.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("titulo",cancion.titulo);
            registro.put("artista",cancion.artista);
            registro.put("album", cancion.album);
            registro.put("duracion", cancion.duracion);
            basededatos.insert("canciones", null, registro);
            basededatos.close();
            return "registro exitoso";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error";
        }
    }

    public List<Clase_Cancion> buscarTodos(Context context){
        List<Clase_Cancion> lista = new ArrayList<>();
        try {
            CancionBD bd = new CancionBD(context, CancionBD.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from canciones", null);

            while(fila.moveToNext()) {
                Clase_Cancion cancion = new Clase_Cancion();
                cancion.id_cancion = fila.getInt(fila.getColumnIndex("id_cancion"));
                cancion.titulo = fila.getString(fila.getColumnIndex("titulo"));
                cancion.artista = fila.getString(fila.getColumnIndex("artista"));
                cancion.album = fila.getString(fila.getColumnIndex("album"));
                cancion.duracion = fila.getString(fila.getColumnIndex("duracion"));
                lista.add(cancion);
            }

        }catch (Exception e){
            return null;
        }
        return lista;
    }
}
