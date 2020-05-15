package com.example.parcialmovil;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CancionBD extends SQLiteOpenHelper {


    public static String nombreBD = "bdparcial1";
    public CancionBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table canciones(" +
                "id_cancion integer PRIMARY KEY autoincrement," +
                "titulo text," +
                "artista text," +
                "album text," +
                "duracion text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists terceros");
        db.execSQL("drop table if exists sucursales");
        onCreate(db);
    }
}
