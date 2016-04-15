package mx.com.omnius.serviciosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mx.com.omnius.serviciosapp.servicios.ServicioIniciado;


public class ServicioIniciadoActivity extends AppCompatActivity {

    Button buttonIniciarServicio;
    Button buttonDetenerServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_iniciado);

        buttonIniciarServicio = (Button)findViewById(R.id.button_iniciar_servicio);
        buttonDetenerServicio = (Button)findViewById(R.id.button_detener_servicio);

        buttonIniciarServicio.setOnClickListener(clickListenerIniciciar);
        buttonDetenerServicio.setOnClickListener(clickListenerDetener);
    }

    private View.OnClickListener clickListenerIniciciar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ServicioIniciadoActivity.this, ServicioIniciado.class);
            startService(intent);
        }
    };

    private View.OnClickListener clickListenerDetener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ServicioIniciadoActivity.this, ServicioIniciado.class);
            stopService(intent);
        }
    };
}
