package mx.com.omnius.interfaz_vistaspersonalizadasapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    MiVista miVista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miVista = (MiVista)findViewById(R.id.miVista);
        miVista.setColorCirculo(Color.parseColor("#26B238"));
        miVista.setColorEtiqueta(Color.parseColor("#976AFF"));
        miVista.setTextoEtiqueta("Hola");

        Button buttonPressed = (Button)findViewById(R.id.buttonPressed);
        buttonPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomColor();
                randomLabel();
            }
        });
    }

    protected void randomColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        miVista.setColorCirculo(color);
    }

    protected void randomLabel(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        miVista.setColorEtiqueta(color);
    }
}
