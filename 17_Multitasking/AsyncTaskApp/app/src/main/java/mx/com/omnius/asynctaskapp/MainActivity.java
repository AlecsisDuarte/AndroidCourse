package mx.com.omnius.asynctaskapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {

    private TextView textResultado;
    private WebView webView;
    private Button  buttonIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResultado = (TextView)findViewById(R.id.text_numero_lineas);
        webView = (WebView)findViewById(R.id.webView);
        buttonIniciar = (Button)findViewById(R.id.button_iniciar);

        buttonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConeccionAsincrona coneccionAsincrona = new ConeccionAsincrona();
                coneccionAsincrona.execute("http://google.com");


            }
        });
    }

    class ConeccionAsincrona extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),
                    "Iniciando descarga",
                    Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            webView.loadData(s, "text/html", "UTF-8");
            Toast.makeText(getApplicationContext(),
                    "Descarga finalizada",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textResultado.setText(getResources().getText(R.string.text_textview_lineas).toString()+values[0]);
        }

        @Override
        protected String doInBackground(String... params) {
            Integer contadorLineas =0;
            String resultado = "";

            try{
                URL url =null;
                url = new URL(params[0]);
                URLConnection con = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line ="";

                while ((line = bufferedReader.readLine()) != null){
                    resultado+=line;
                    contadorLineas++;
                    publishProgress(contadorLineas);
                    Thread.sleep(200);
                }
                return resultado;
            }catch (Exception e){
                Log.e("Exception", "Error = " + e.toString());
            }
            return "";
        }
    }
}
