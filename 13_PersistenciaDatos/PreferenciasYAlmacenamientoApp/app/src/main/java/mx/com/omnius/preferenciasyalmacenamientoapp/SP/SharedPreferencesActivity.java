package mx.com.omnius.preferenciasyalmacenamientoapp.SP;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import mx.com.omnius.preferenciasyalmacenamientoapp.R;


public class SharedPreferencesActivity extends AppCompatActivity {

    EditText editCorreo, editTelefono;
    CheckBox checkBoxNoticias;
    Button buttonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        editCorreo = (EditText)findViewById(R.id.edit_correo);
        editTelefono = (EditText)findViewById(R.id.edit_telefono);
        checkBoxNoticias = (CheckBox)findViewById(R.id.checkbox_noticias);
        buttonGuardar = (Button)findViewById(R.id.button_guardar);

        SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String correo = preferences.getString("correo", "");
        int telefono = preferences.getInt("telefono", 0);
        boolean noticias = preferences.getBoolean("noticias", false);

        editCorreo.setText(correo);
        editTelefono.setText(String.valueOf(telefono));
        if(noticias){
            checkBoxNoticias.setChecked(true);
        }else{
            checkBoxNoticias.setChecked(false);
        }

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("correo", editCorreo.getText().toString());
                editor.putInt("telefono", Integer.valueOf(editTelefono.getText().toString()));
                editor.putBoolean("noticias", checkBoxNoticias.isChecked());
                editor.commit();
                Toast.makeText(SharedPreferencesActivity.this, "Datos guardados", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}