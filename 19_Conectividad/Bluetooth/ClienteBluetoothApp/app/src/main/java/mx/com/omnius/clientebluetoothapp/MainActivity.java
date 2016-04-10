package mx.com.omnius.clientebluetoothapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> dispositivosArrayAdapter;
    public BluetoothSocket bluetoothSocket;
    private PrintWriter printWriter;
    private EditText editMensaje;
    private Button buttonEnviar;
    private ListView listView;
    private static final int PETICION_BLUETOOTH = 0;
    private static final UUID MI_UUID = UUID.fromString("6049a354-3df0-11e3-8e7a-ce3f5508acd9");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMensaje = (EditText)findViewById(R.id.edit_mensaje);
        buttonEnviar = (Button)findViewById(R.id.button_enviar);
        listView = (ListView)findViewById(R.id.listView);

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

        dispositivosArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(dispositivosArrayAdapter);

        listView.setOnItemClickListener(itemClickListener);
        buttonEnviar.setOnClickListener(onClickListener);
        buttonEnviar.setEnabled(false);

        registrarBroadcast();
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        private BluetoothDevice bluetoothDevice;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            bluetoothAdapter.cancelDiscovery();
            String nombreBluetooth = ((TextView) view).getText().toString();
            String direccionBluetooth = nombreBluetooth.substring(nombreBluetooth.length() - 17);

            bluetoothDevice = bluetoothAdapter.getRemoteDevice(direccionBluetooth);
            Toast.makeText(MainActivity.this, getString(R.string.mensaje_intento_conexion), Toast.LENGTH_LONG).show();
            ConexionClienteAsync conexionClienteAsync = new ConexionClienteAsync();
            conexionClienteAsync.execute(bluetoothDevice);
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            enviarMensaje();
        }
    };

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String accion = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(accion)){
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(bluetoothDevice.getBondState() != BluetoothDevice.BOND_BONDED){
                    dispositivosArrayAdapter.add(bluetoothDevice.getName()+"\n"+bluetoothDevice.getAddress());
                }
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(accion)) {
                Toast.makeText(MainActivity.this, getString(R.string.mensaje_busqueda_finalizada), Toast.LENGTH_SHORT).show();
                if (dispositivosArrayAdapter.getCount() == 0) {
                    dispositivosArrayAdapter.add(getString(R.string.mensaje_no_dispositivos));
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actualizar) {
            iniciarDescubrimiento();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PETICION_BLUETOOTH:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, getString(R.string.mensaje_bluetooth_on), Toast.LENGTH_SHORT).show();
                    iniciarDescubrimiento();
                }
                else if  (resultCode == Activity.RESULT_CANCELED)  {
                    Toast.makeText(this, getString(R.string.mensaje_bluetooth_off), Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(bluetoothAdapter != null){
            bluetoothAdapter.cancelDiscovery();
            this.unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registrarBroadcast();
    }

    private void registrarBroadcast(){
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(broadcastReceiver, intentFilter);

        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void iniciarDescubrimiento(){
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
        }
        Toast.makeText(MainActivity.this, getString(R.string.mensaje_descubriendo), Toast.LENGTH_LONG).show();
        bluetoothAdapter.startDiscovery();
    }

    private void enviarMensaje(){
        if(printWriter != null){
            try{
                String mensaje = editMensaje.getText().toString();
                printWriter.println(mensaje);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(MainActivity.this, getString(R.string.mensaje_sin_conexion), Toast.LENGTH_LONG).show();
        }
    }

    class ConexionClienteAsync extends AsyncTask<BluetoothDevice, String, Boolean>{
        BluetoothDevice bluetoothDevice;
        BluetoothSocket bluetoothSocketTemporal = null;
        @Override
        protected Boolean doInBackground(BluetoothDevice... params) {
            this.bluetoothDevice = params[0];
            try{
                bluetoothSocketTemporal = bluetoothDevice.createRfcommSocketToServiceRecord(MI_UUID);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            bluetoothSocket = bluetoothSocketTemporal;
            bluetoothAdapter.cancelDiscovery();
            try{
                bluetoothSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean resp) {
            super.onPostExecute(resp);
            if(resp){
                Toast.makeText(MainActivity.this, getString(R.string.mensaje_conexion_ok), Toast.LENGTH_SHORT).show();
                buttonEnviar.setEnabled(true);
            }else{
                Toast.makeText(MainActivity.this, getString(R.string.mensaje_conexion_error), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
