package mx.com.omnius.interfazusuarioycontrolesapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    Button botonPresionar;
    Spinner spinnerColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerColor = (Spinner)findViewById(R.id.spinner);
        botonPresionar = (Button)findViewById(R.id.button_presionar);
        /*Implementación de evento por definición de interfaz*/
        botonPresionar.setOnClickListener(this);
        /*Implementación de evento por definición de una clase anónima*/
        botonPresionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*Implementación de evento por definición de clase interna*/
        botonPresionar.setOnClickListener(eventoBoton);
    }

    private View.OnClickListener eventoBoton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onClick(View v) {
        finish();
    }
}
