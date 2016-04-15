package mx.com.omnius.servidorbluetoothapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private TextView textMensajes;
    private Button buttonVisible;
    private static final UUID MI_UUID = UUID.fromString("6049a354-3df0-11e3-8e7a-ce3f5508acd9");
    private static final int PETICION_BLUETOOTH = 0;
    private BluetoothServerSocket bluetoothServerSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMensajes = (TextView)findViewById(R.id.text_mensajes);
        buttonVisible = (Button)findViewById(R.id.button_visible);

        buttonVisible.setOnClickListener(onClickListener);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Toast.makeText(MainActivity.this, getString(R.string.mensaje_no_bluetooth), Toast.LENGTH_LONG).show();
            finish();
        }else {
            if(bluetoothAdapter.isEnabled()){
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, PETICION_BLUETOOTH);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PETICION_BLUETOOTH:
                if (resultCode == Activity.RESULT_OK) {
                    textMensajes.append("\n" + getResources().getString(R.string.mensaje_bluetooth_on));
                }
                else if  (resultCode == Activity.RESULT_CANCELED)  {
                    textMensajes.append("\n" + getResources().getString(R.string.mensaje_bluetooth_off));
                    finish();
                }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!iniciarBluetooth()){
                textMensajes.append("\n"+getString(R.string.mensaje_error_modo_visible));
            }
        }
    };

    class AceptarSolcitudesAsync extends AsyncTask<Integer, String, Integer>{

        private BluetoothServerSocket bluetoothServerSocketTemporal = null;

        @Override
        protected Integer doInBackground(Integer... params) {


            try {
                bluetoothServerSocketTemporal = bluetoothAdapter.
                        listenUsingRfcommWithServiceRecord("mx.com.omnius.servidorbluetoothapp", MI_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bluetoothServerSocket = bluetoothServerSocketTemporal;

            BluetoothSocket bluetoothSocket = null;
            try {
                bluetoothSocket = bluetoothServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }




            if (bluetoothSocket != null) {

                CrearConexionAsync crearConexionAsync = new CrearConexionAsync();
                crearConexionAsync.execute(bluetoothSocket);
                publishProgress(getString(R.string.mensaje_cliente_conectado));
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textMensajes.append("\n"+values[0]);
        }
    }

    class CrearConexionAsync extends AsyncTask<BluetoothSocket, String, Integer>{

        private BluetoothSocket bluetoothSocket;
        private BufferedReader bufferedReader;

        @Override
        protected Integer doInBackground(BluetoothSocket... params) {
            bluetoothSocket = params[0];
            try{
                bufferedReader = new BufferedReader(new InputStreamReader(bluetoothSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            while (true){
                try{
                    String mensaje = bufferedReader.readLine();
                    publishProgress(mensaje);

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textMensajes.append("\n" + values[0]);
        }
    }

    private boolean iniciarBluetooth(){
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 500);
            startActivity(intent);
            textMensajes.append("\n"+getString(R.string.mensaje_visible_on));
            AceptarSolcitudesAsync aceptarSolcitudesAsync = new AceptarSolcitudesAsync();
            aceptarSolcitudesAsync.execute(0);
            textMensajes.append("\n"+getString(R.string.mensaje_visible_off));
            return true;
        }
        return false;
    }
}
