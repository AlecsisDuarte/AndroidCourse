package mx.com.omnius.listviewytablayoutapp.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import mx.com.omnius.listviewytablayoutapp.R;

public class AdaptadorLista extends ArrayAdapter<String>{

    Context context;
    String[] nombres;
    String[] descripciones;
    int[] idFotos;

    public AdaptadorLista(Context context, int resource, int textViewResourceId, String[] strings) {
        super(context, resource, textViewResourceId, strings);
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }

    public void setDescripciones(String[] descripciones) {
        this.descripciones = descripciones;
    }

    public void setIdFotos(int[] idFotos) {
        this.idFotos = idFotos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vista = inflater.inflate(R.layout.vista_lista_personalizada, parent, false);

        TextView nombreText = (TextView) vista.findViewById(R.id.text_nombre);
        TextView descripcionText = (TextView) vista.findViewById(R.id.text_descripcion);
        ImageView imagen = (ImageView) vista.findViewById(R.id.image_lista);

        nombreText.setText(nombres[position]);
        descripcionText.setText(descripciones[position]);
        imagen.setImageResource(idFotos[position]);
        return vista;
    }
}
