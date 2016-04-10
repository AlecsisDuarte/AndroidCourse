package mx.com.omnius.animacionygraficosapp;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    private ImageView imagenFrameAninmation;
    private Animation animacion;
    private Button buttonTweenAnimation, buttonFrameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagenFrameAninmation = (ImageView)findViewById(R.id.imageViewFrameAnimation);
        buttonFrameAnimation = (Button)findViewById(R.id.button_frame_animation);
        buttonTweenAnimation = (Button)findViewById(R.id.button_tween_animation);

        buttonFrameAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenFrameAninmation.setBackgroundResource(R.drawable.frame_animation);
                AnimationDrawable animationDrawable = (AnimationDrawable) imagenFrameAninmation.getBackground();
                animationDrawable.start();
                buttonFrameAnimation.setBackgroundResource(R.drawable.shape_button);
                buttonTweenAnimation.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        buttonTweenAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacion = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tween_animation);
                imagenFrameAninmation.startAnimation(animacion);
                buttonFrameAnimation.setBackgroundResource(android.R.drawable.btn_default);
                buttonTweenAnimation.setBackgroundResource(R.drawable.shape_button);
            }
        });



    }
}
