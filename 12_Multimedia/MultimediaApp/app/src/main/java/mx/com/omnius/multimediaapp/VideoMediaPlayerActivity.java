package mx.com.omnius.multimediaapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


public class VideoMediaPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_media_player);

        Uri myUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);

        try {
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setKeepScreenOn(true);

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(surfaceHolder);
                try {
                    mediaPlayer.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                                       int arg3) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder arg0) {
            }
        });
    }
}
