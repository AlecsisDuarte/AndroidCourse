package com.omnius.soapapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends AppCompatActivity {

    Button buttonSOAP;
    TextView textRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        buttonSOAP = (Button)findViewById(R.id.button_soap);
        textRespuesta = (TextView)findViewById(R.id.text_respuesta);

        buttonSOAP.setOnClickListener(clickListenerSOAP);
    }

    private View.OnClickListener clickListenerSOAP = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                LlamadaSOAP llamadaSOAP = new LlamadaSOAP();
                String respuesta = llamadaSOAP.getResultado("mexico");
                textRespuesta.setText(respuesta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    class LlamadaSOAP {

        private static final String metodo = "GetCitiesByCountry";
        private static final String namespace = "http://www.webserviceX.NET";
        private static final String accionSoap = "http://www.webserviceX.NET/GetCitiesByCountry";
        private static final String url = "http://www.webservicex.net/globalweather.asmx";

        public String getResultado(String parametro) throws Exception {

            try {
                SoapObject peticion = new SoapObject(namespace, metodo);
                peticion.addProperty("CountryName", parametro);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(peticion);
                HttpTransportSE transporte = new HttpTransportSE(url);
                transporte.call(accionSoap, sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                return resultado.toString() ;
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
            return "";

        }
    }
}
