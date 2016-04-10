    package com.omnius.contentproviderusoapp;

    import android.content.ContentValues;
    import android.database.Cursor;
    import android.net.Uri;
    import android.support.v7.app.ActionBarActivity;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.Toast;


    public class MainActivity extends AppCompatActivity {

        public static final String NOMBRE_PROVEEDOR = "com.omnius.contentproviderapp.provider.Usuarios";
        private EditText edit_usuario, edit_contrasena;
        private Button buttonNuevo;
        private ListView listView;
        private ArrayAdapter<String> adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            edit_usuario = (EditText) findViewById(R.id.edit_usuario);
            edit_contrasena = (EditText) findViewById(R.id.edit_contrasena);
            buttonNuevo = (Button) findViewById(R.id.button_nuevo);
            listView = (ListView) findViewById(R.id.listview);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            listView.setAdapter(adapter);

            buttonNuevo.setOnClickListener(clickListenerNuevo);

            obtenerRegistros();
        }

        private View.OnClickListener clickListenerNuevo = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("nombre", edit_usuario.getText().toString());
                values.put("password", edit_contrasena.getText().toString());

                Uri uri = getContentResolver().insert(Uri.parse("content://" + NOMBRE_PROVEEDOR + "/usuarios"), values);
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
        };

        private void obtenerRegistros() {

            Uri consulta = Uri.parse("content://" + NOMBRE_PROVEEDOR + "/usuarios");
            Cursor c = getContentResolver().query(consulta, null, null, null, "id desc");
            if (c.moveToFirst()) {
                do {
                    adapter.add("\n" +
                            "id: 	 	" + c.getString(0) + "\n" +
                            "usuario: 	" + c.getString(1) + "\n" +
                            "contraseña:  " + c.getString(2) + "\n");
                } while (c.moveToNext());
            } else {
                Toast.makeText(MainActivity.this, "Sin resultados", Toast.LENGTH_LONG).show();
            }


        }
    }
