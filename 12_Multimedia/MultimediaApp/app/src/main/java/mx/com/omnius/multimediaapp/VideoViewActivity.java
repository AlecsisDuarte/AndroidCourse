package mx.com.omnius.multimediaapp;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        Uri uriVideo =Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid);
		VideoView videoView = (VideoView) findViewById(R.id.videoView);
		videoView.setVideoURI(uriVideo);
		videoView.setMediaController(new MediaController(this));
		videoView.start();
		videoView.requestFocus();
    }
}
