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
    int resourceId;

    public AdaptadorLista(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.resourceId = resource;
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
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vista_lista_personalizada, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cargarDatos(position);

        return convertView;
    }

    class ViewHolder{
        ImageView imagen;
        TextView  nombre, descripcion;

        ViewHolder(View vista){
            nombre = (TextView) vista.findViewById(R.id.text_nombre);
            descripcion = (TextView) vista.findViewById(R.id.text_descripcion);
            imagen = (ImageView) vista.findViewById(R.id.image_lista);
        }

        void cargarDatos(int position){
            nombre.setText(nombres[position]);
            descripcion.setText(descripciones[position]);
            imagen.setImageResource(idFotos[position]);
        }

    }
}
