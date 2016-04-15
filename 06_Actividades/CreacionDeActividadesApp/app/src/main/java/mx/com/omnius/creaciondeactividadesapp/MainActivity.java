package mx.com.omnius.creaciondeactividadesapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    Button miBoton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout miLinearLayout = new LinearLayout(this);
        miLinearLayout.setOrientation(LinearLayout.VERTICAL);

        miBoton = new Button(this);
        miBoton.setText("Botón creado desde código");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        miBoton.setLayoutParams(layoutParams);
        miBoton.setOnClickListener(eventoOnClick);
        miLinearLayout.addView(miBoton);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        miLinearLayout.setLayoutParams(layoutParams2);
        setContentView(miLinearLayout);

        /*DISEÑO DE INTERFAZ DESDE XML*/
        /*setContentView(R.layout.activity_main);
        miBoton = (Button)findViewById(R.id.button);
        miBoton.setOnClickListener(eventoOnClick);*/
    }

    private View.OnClickListener eventoOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == miBoton){
                Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
