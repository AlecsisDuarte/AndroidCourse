package mx.com.omnius.preferenciasyalmacenamientoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import mx.com.omnius.preferenciasyalmacenamientoapp.BD.SQLiteActivity;
import mx.com.omnius.preferenciasyalmacenamientoapp.SP.SharedPreferencesActivity;
import mx.com.omnius.preferenciasyalmacenamientoapp.SPA.SharedPreferencesScreenActivity;
import mx.com.omnius.preferenciasyalmacenamientoapp.archivos.AlmacenamientoInternoExterno;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(android.R.id.list);
        String[] opciones = {
                "Shared preferences",
                "Shared preferences activity",
                "Almacenamiento de archivos",
                "Bases de datos"
        };

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intentSP = new Intent(MainActivity.this, SharedPreferencesActivity.class);
                        startActivity(intentSP);
                        break;
                    case 1:
                        Intent intentSPS = new Intent(MainActivity.this, SharedPreferencesScreenActivity.class);
                        startActivity(intentSPS);
                        break;
                    case 2:
                        Intent intentArchivos = new Intent(MainActivity.this, AlmacenamientoInternoExterno.class);
                        startActivity(intentArchivos);
                        break;
                    case 3:
                        Intent intentBD = new Intent(MainActivity.this, SQLiteActivity.class);
                        startActivity(intentBD);
                        break;
                }
            }
        });
    }
}
