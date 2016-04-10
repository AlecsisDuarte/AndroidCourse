package mx.com.omnius.preferenciasyalmacenamientoapp.BD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import mx.com.omnius.preferenciasyalmacenamientoapp.R;

public class SQLiteActivity extends AppCompatActivity {

    private EditText editNombre, editApellido, editEdad;
    private Button buttonGuardar;
    private ListView listView;
    private PersonaDataSource personaDS;
    private ArrayAdapter<Persona> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        editNombre = (EditText)findViewById(R.id.edit_nombre);
        editApellido = (EditText)findViewById(R.id.edit_apellido);
        editEdad = (EditText)findViewById(R.id.edit_edad);
        buttonGuardar = (Button)findViewById(R.id.button_guardar);
        listView = (ListView)findViewById(android.R.id.list);

        crearBaseDatos();

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarRegistro();
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new ArrayAdapter<Persona>(this,
                android.R.layout.simple_list_item_1,
                obtenerRegistros());

        listView.setAdapter(adapter);
    }

    protected void crearBaseDatos() {
        personaDS = new PersonaDataSource(this);
    }

    protected void insertarRegistro() {
        personaDS.open();
        personaDS.insertarRegistro(editNombre.getText().toString(), editApellido.getText().toString(),
                Integer.valueOf(editEdad.getText().toString()));
        personaDS.close();

        editNombre.setText("");
        editApellido.setText("");
        editEdad.setText("");
    }

    protected List<Persona> obtenerRegistros() {
        personaDS.open();
        List<Persona> personas = personaDS.obtenerRegistros();
        personaDS.close();
        return personas;
    }
}
