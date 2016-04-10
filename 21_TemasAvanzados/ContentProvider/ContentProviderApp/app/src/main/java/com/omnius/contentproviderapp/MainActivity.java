package com.omnius.contentproviderapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        listView.setAdapter(adapter);
        obtenerRegistros();
    }

    private void obtenerRegistros(){
        Cursor c = database.query("usuario", new String[] { "id", "nombre", "password" }, null, null, null, null, null);
        if (c.moveToFirst()){
            do {
                adapter.add("\n" +
                        "id: 	 	" + c.getString(0) + "\n" +
                        "nombre: 	" + c.getString(1) + "\n" +
                        "password:  " + c.getString(2) + "\n");
            } while (c.moveToNext());
        }
        database.close();
    }
}
