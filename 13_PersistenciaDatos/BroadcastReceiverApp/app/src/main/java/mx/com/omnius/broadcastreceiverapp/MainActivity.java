package mx.com.omnius.broadcastreceiverapp;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button buttonBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBroadcast = (Button)findViewById(R.id.buttonBroadcast);
        buttonBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.omnius.broadcastreceiverapp.broadcast");
                intent.putExtra("origen", "click botón");
                sendBroadcast(intent);
            }
        });

        BroadcastAsincrono broadcastAsincrono = new BroadcastAsincrono();
        broadcastAsincrono.execute();
    }

    class BroadcastAsincrono extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i=0; i<3; i++){
                try{
                    Thread.sleep(5000);
                }catch (Exception e){
                    Log.e("Error","Error en AsyncTask");
                }
                Intent intent = new Intent();
                intent.setAction("com.omnius.broadcastreceiverapp.broadcast");
                intent.putExtra("origen", "AsyncTask "+i);
                sendBroadcast(intent);
            }
            return null;
        }
    }
}
