package com.omnius.contentproviderapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION_BASEDATOS = 1;
    private static final String NOMBRE_BASEDATOS = "base.db";
    private static final String TABLA_USUARIO = "usuario";
    private static final String CREAR_TABLA = "create table " + TABLA_USUARIO
            + " (id  integer primary key autoincrement, "
            + "nombre  text not null, "
            + "password text not null );";

    private Context context;

    public DBHelper(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREAR_TABLA);
        } catch (SQLException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(context, "Base de datos creada", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_USUARIO);
        onCreate(db);
    }
}
