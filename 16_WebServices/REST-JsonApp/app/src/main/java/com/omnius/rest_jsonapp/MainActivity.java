package com.omnius.rest_jsonapp;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        webServiceRest();
    }

    private void webServiceRest(){
        try {
            URL url = new URL("http://omnius.com.mx:8080/sevenwin/webresources/service/getAllEntidadFederativa");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String resultadoWebService ="";
            while ((line = bufferedReader.readLine()) != null) {
                resultadoWebService+=line;
            }
            bufferedReader.close();
            parseInformacion(resultadoWebService);
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }

    private void parseInformacion(String resultadoJson){
        JSONArray jsonArray = null;
        String nombre, clave;

        try {
            jsonArray = new JSONArray(resultadoJson);
        } catch (JSONException e) {
            Log.e("JSON", e.toString());
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                clave = jsonObject.getString("clave");
                nombre = jsonObject.getString("nombre");
                adapter.add("Clave : "+clave+" Nombre : "+ nombre);
            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        }
    }
}
