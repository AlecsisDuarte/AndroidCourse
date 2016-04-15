package mx.com.omnius.preferenciasyalmacenamientoapp.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperPersona extends SQLiteOpenHelper {

	private static HelperPersona helperPersona = null;
	
	private static final String NOMBRE_BD = "BD";
	private static final int VERSION_BD = 1;
	
	public static class TablaPersona{
		public static String TABLA = "tablaPersona";
		public static String COLUMNA_ID= "id";
		public static String COLUMNA_NOMBRE= "nombre";
		public static String COLUMNA_APELLIDO= "apellido";
		public static String COLUMNA_EDAD= "edad";
	}

	private static final String CONSULTA_CREA_TABLA = "create table "
			+ TablaPersona.TABLA + "("
			+ TablaPersona.COLUMNA_ID + " integer primary key autoincrement, "
			+ TablaPersona.COLUMNA_NOMBRE + " VARCHAR, "
			+ TablaPersona.COLUMNA_APELLIDO + " VARCHAR, "
			+ TablaPersona.COLUMNA_EDAD + " integer );";

    private static final String CONSULTA_ELIMINAR_TABLA = "delete table if exists " + TablaPersona.TABLA;

	public static HelperPersona getInstance(Context context){
		if (helperPersona == null){
			helperPersona = new HelperPersona(context.getApplicationContext());
		}
		return helperPersona;
	}
	
	private HelperPersona(Context context) {
		super(context, NOMBRE_BD, null, VERSION_BD);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONSULTA_CREA_TABLA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CONSULTA_ELIMINAR_TABLA);
		onCreate(db);
	}

}
