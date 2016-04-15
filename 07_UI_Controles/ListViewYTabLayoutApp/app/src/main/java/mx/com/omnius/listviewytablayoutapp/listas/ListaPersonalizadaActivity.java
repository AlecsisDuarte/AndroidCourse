package mx.com.omnius.listviewytablayoutapp.listas;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import mx.com.omnius.listviewytablayoutapp.R;

public class ListaPersonalizadaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personalizada);

        ListView listView = (ListView)findViewById(R.id.listView);
        String[] elementos_nombres = getResources().getStringArray(R.array.elementos_lista);
        String[] elementos_descripcion = getResources().getStringArray(R.array.elementos_descripcion);
        int[] elementos_imagenes = {R.drawable.ic_android,R.drawable.ic_ios,R.drawable.ic_windows, R.drawable.ic_bb, R.drawable.ic_sym};

        final AdaptadorLista adaptadorLista = new AdaptadorLista(this, R.layout.vista_lista_personalizada, elementos_nombres);
        adaptadorLista.setNombres(elementos_nombres);
        adaptadorLista.setDescripciones(elementos_descripcion);
        adaptadorLista.setIdFotos(elementos_imagenes);

        listView.setAdapter(adaptadorLista);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(ListaPersonalizadaActivity.class.getSimpleName(), "Elemento precionado " + parent.getItemAtPosition(position));
            }
        });
    }
}
