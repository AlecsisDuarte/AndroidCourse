package mx.com.omnius.multimediaapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(android.R.id.list);
        String[] opciones = {
                "Audio",
                "Video MediaPlayer",
                "Video VideoView",
                "Cámara"
        };

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intentAudio = new Intent(getApplicationContext(), AudioActivity.class);
                        startActivity(intentAudio);
                        break;
                    case 1:
                        Intent intentVideoMP = new Intent(getApplicationContext(), VideoMediaPlayerActivity.class);
                        startActivity(intentVideoMP);
                        break;
                    case 2:
                        Intent intentVideoView = new Intent(getApplicationContext(), VideoViewActivity.class);
                        startActivity(intentVideoView);
                        break;
                    case 3:
                        Intent intentCamara = new Intent(getApplicationContext(), CamaraActivity.class);
                        startActivity(intentCamara);
                        break;
                }
            }
        });
    }
}
