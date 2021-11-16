package com.ingluise.ProyectoAndroidGrupo08;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBSQLiteHelper extends SQLiteOpenHelper {
    public MyDBSQLiteHelper(Context context, String nomDB, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nomDB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE producto(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT," +
                "categoria TEXT, marca TEXT, proveedor TEXT)");
        db.execSQL("CREATE TABLE imagenes(_id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion TEXT, img BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int verDBAnterior, int verDBNueva) {
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS imagenes");
        db.execSQL("CREATE TABLE producto(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT," +
                "categoria TEXT, marca TEXT, proveedor TEXT)");
        db.execSQL("CREATE TABLE imagenes(_id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion TEXT, img BLOB)");
    }
}
