package mx.com.omnius.multimediaapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;


public class AudioActivity extends AppCompatActivity {

    private ImageButton buttonPlay, buttonPause, buttonStop;
    private TextView textoEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        buttonPlay = (ImageButton)findViewById(R.id.button_play);
        buttonPause = (ImageButton)findViewById(R.id.button_pause);
        buttonStop = (ImageButton)findViewById(R.id.button_stop);
        textoEstado = (TextView)findViewById(R.id.text_estado);

        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mediaPlayer.start();
        textoEstado.setText("Reproduciendo música");


        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    textoEstado.setText("Música en pausa");
                } else {
                    mediaPlayer.start();
                    textoEstado.setText("Reproduciendo música");
                }
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    textoEstado.setText("Música en pausa");
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    textoEstado.setText("Música detenida");
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}
