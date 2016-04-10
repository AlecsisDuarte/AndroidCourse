package mx.com.omnius.preferenciasyalmacenamientoapp.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import mx.com.omnius.preferenciasyalmacenamientoapp.BD.HelperPersona.TablaPersona;
import java.util.ArrayList;
import java.util.List;



public class PersonaDataSource {
	private SQLiteDatabase database;
	private HelperPersona helperPersona;
	
	private String[] columnasTablaPersona = {
            TablaPersona.COLUMNA_ID,
			TablaPersona.COLUMNA_NOMBRE,
			TablaPersona.COLUMNA_APELLIDO,
			TablaPersona.COLUMNA_EDAD
	};

	public PersonaDataSource(Context context) {
		helperPersona = HelperPersona.getInstance(context);
	}

	public void open() {
		database = helperPersona.getWritableDatabase();
	}

	public void close() {
		helperPersona.close();
	}
	
	public void insertarRegistro(String nombre, String apellido, int edad) {
		ContentValues values = new ContentValues();
		values.put(TablaPersona.COLUMNA_NOMBRE, nombre);
		values.put(TablaPersona.COLUMNA_APELLIDO, apellido);
		values.put(TablaPersona.COLUMNA_EDAD, Integer.toString(edad));
		database.insert(TablaPersona.TABLA, null, values);
	}

	public List<Persona> obtenerRegistros() {
		List<Persona> listaPersonas = new ArrayList<Persona>();
		Cursor cursor = database.query(TablaPersona.TABLA, columnasTablaPersona, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Persona nuevaPosicion = parseCursorPersona(cursor);
			listaPersonas.add(nuevaPosicion);
			cursor.moveToNext();
		}

		cursor.close();
		return listaPersonas;
	}

	public void borrarRegistros(Persona posicion) {
		database.delete(TablaPersona.TABLA, TablaPersona.COLUMNA_EDAD + " = " + posicion.getEdad(), null);
	}
	
	private Persona parseCursorPersona(Cursor cursor) {
		try{
			Persona persona = new Persona();
			persona.setId(cursor.getInt(0));
			persona.setNombre(cursor.getString(1));
			persona.setApellido(cursor.getString(2));
			persona.setEdad(cursor.getInt(3));
			return persona;
		}catch (CursorIndexOutOfBoundsException e){
			return null;
		}
		
	}
}
