package mx.com.omnius.serviciosapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mx.com.omnius.serviciosapp.servicios.ServicioVinculado;


public class ServicioVinculadoActivity extends AppCompatActivity {

    private ServicioVinculado serviceVinculado;
    private boolean serviceBound = false;
    private TextView textTiempo;
    private Button buttonTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_vinculado);

        textTiempo = (TextView) findViewById(R.id.text_tiempo);
        buttonTiempo = (Button) findViewById(R.id.button_obtener_tiempo);
        buttonTiempo.setOnClickListener(clickListenerTiempo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ServicioVinculado.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceBound) {
            unbindService(serviceConnection);
            serviceBound = false;
        } else {
            Toast.makeText(this,
                    "Servicio no vinculado",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener clickListenerTiempo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (serviceBound) {
                textTiempo.append(serviceVinculado.getTimestamp() + "\n");
            }

        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServicioVinculado.MiBinder miBinder = (ServicioVinculado.MiBinder) service;
            serviceVinculado = miBinder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };
}
