package mx.com.omnius.preferenciasyalmacenamientoapp.archivos;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import mx.com.omnius.preferenciasyalmacenamientoapp.R;

public class AlmacenamientoInternoExterno extends AppCompatActivity {

    EditText editTextoInterna, editTextoExterna;
    Button buttonGuardarInterna, buttonGuardarExterna, buttonLeerInterna, buttonLeerExterna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento_interno_externo);

        editTextoInterna = (EditText)findViewById(R.id.edit_texto_archivo_uno);
        editTextoExterna = (EditText)findViewById(R.id.edit_texto_archivo_dos);
        buttonGuardarInterna = (Button)findViewById(R.id.button_guardar_interna);
        buttonGuardarExterna = (Button)findViewById(R.id.button_guardar_externa);
        buttonLeerInterna = (Button)findViewById(R.id.button_recuperar_interna);
        buttonLeerExterna = (Button)findViewById(R.id.button_recuperar_externa);

        buttonGuardarInterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarArchivoMemInterna(editTextoInterna.getText().toString());
            }
        });

        buttonLeerInterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contenidoArchivo = leerArchivoMemInterna();
                Toast.makeText(AlmacenamientoInternoExterno.this, contenidoArchivo, Toast.LENGTH_LONG).show();
            }
        });

        buttonGuardarExterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarArchivoMemExterna(editTextoExterna.getText().toString());
            }
        });

        buttonLeerExterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contenidoArchivo = leerArchivoMemExterna();
                Toast.makeText(AlmacenamientoInternoExterno.this, contenidoArchivo, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void guardarArchivoMemInterna(String textoArchivo){
        String nombreArchivo = "archivoAndroid.txt";

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if(fos != null){
                fos.write(textoArchivo.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(fos != null){
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String leerArchivoMemInterna(){
        String nombreArchivo = "archivoAndroid.txt";
        String contenidoArchivo = "";
        try{
            BufferedReader buffered = new BufferedReader(new InputStreamReader(openFileInput(nombreArchivo)));
            contenidoArchivo = buffered.readLine();
            buffered.close();
        }
        catch (Exception ex)
        {
            return  "Error al leer el archivo " + ex;
        }
        return contenidoArchivo;
    }

    private void guardarArchivoMemExterna(String textoArchivo){
        String nombreArchivo = "archivoAndroid.txt";

        String rutaSDCard = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rutaSDCard = getExternalFilesDir(null).getAbsolutePath();
        }

        File file = new File(rutaSDCard + "/" + nombreArchivo);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            if (fileOutputStream != null) {
                fileOutputStream.write(textoArchivo.getBytes());
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String leerArchivoMemExterna(){
        String nombreArchivo = "archivoAndroid.txt";
        String rutaSDCard = "";
        String contenidoArchivo = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rutaSDCard = getExternalFilesDir(null).getAbsolutePath();
        }
        try
        {
            File file = new File(rutaSDCard + "/" + nombreArchivo);
            InputStreamReader inputStreamReader= new InputStreamReader(new FileInputStream(file));
            BufferedReader buffered= new BufferedReader(inputStreamReader);
            contenidoArchivo = buffered.readLine();
            buffered.close();
        }
        catch (Exception ex)
        {
            return "Error al leer el archivo "+ex;
        }
        return contenidoArchivo;
    }
}
