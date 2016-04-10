package mx.com.omnius.wifiapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    WifiManager wifiManager;
    private BroadcastReceiverWifi broadcastReceiverWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(android.R.id.list);
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        broadcastReceiverWifi = new BroadcastReceiverWifi();

        estadoWifi();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiverWifi);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void estadoWifi(){
        if(wifiManager.isWifiEnabled()){
            wifiManager.startScan();
        }
    }


    class BroadcastReceiverWifi extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> listaEscaneo = wifiManager.getScanResults();
            String[] listaWifi = new String[listaEscaneo.size()];

            for (int i = 0; i < listaEscaneo.size(); i++) {
                listaWifi[i] = (listaEscaneo.get(i).SSID+"\n"
                        +listaEscaneo.get(i).frequency +"\n"
                        +listaEscaneo.get(i).capabilities+"\n"
                        +String.valueOf(listaEscaneo.get(i).timestamp)+"\n");
            }

            listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    listaWifi));

        }
    }

}
