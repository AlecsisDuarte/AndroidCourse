package mx.com.omnius.dialogosynotificacionesapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(android.R.id.list);
        String[] opciones = getResources().getStringArray(R.array.opciones_lista);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        MiDialogoAlerta miDialogoAlerta = new MiDialogoAlerta();
                        miDialogoAlerta.show(getSupportFragmentManager(), "miDialogo");
                        break;
                    case 1:
                        MiDialogoOpciones miDialogoOpciones = new MiDialogoOpciones();
                        miDialogoOpciones.show(getSupportFragmentManager(), "miDialogo");
                        break;
                    case 2:
                        MiDialogoPersonalizado miDialogoPersonalizado = new MiDialogoPersonalizado();
                        miDialogoPersonalizado.show(getSupportFragmentManager(), "miDialogo");
                        break;
                    case 3:
                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setMessage("Espere un momento");
                        progressDialog.setProgress(0);
                        progressDialog.setMax(100);
                        progressDialog.show();
                        Hilo hilo = new Hilo(progressDialog);
                        hilo.start();
                        break;
                    case 4:
                        mostrarNotificacion();
                        break;
                    case 5:
                        Toast toastSimple = Toast.makeText(MainActivity.this, getString(R.string.texto_toast_simple), Toast.LENGTH_LONG);
                        toastSimple.show();
                        break;
                    case 6:
                        LayoutInflater layoutInflater = getLayoutInflater();
                        View viewToast = layoutInflater.inflate(R.layout.toast_layout, null);
                        Toast toastPersonalizado = new Toast(getApplicationContext());
                        toastPersonalizado.setDuration(Toast.LENGTH_LONG);
                        toastPersonalizado.setView(viewToast);
                        toastPersonalizado.show();
                        break;
                }
            }
        });
    }

    private void mostrarNotificacion(){
        int notificationId = 1;

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getText(R.string.string_titulo_notificacion))
                .setContentText(getText(R.string.string_mensaje_notificacion));

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationCompat.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationCompat.setAutoCancel(true);
        notificationManager.notify(notificationId, notificationCompat.build());

    }

    class MiDialogoAlerta extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.string_mensaje_dialogo));
            return builder.create();
        }
    }

    class  MiDialogoOpciones extends  DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.string_titulo_dialogo));
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setSingleChoiceItems(getResources().getStringArray(R.array.opciones_dialogo), 1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.e("Opción", "Opción selecionada " + which);
                }
            });

            builder.setNegativeButton(getString(R.string.string_titulo_boton_cancelar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.e("Opción", "Cancelar diálogo");
                }
            });

            return builder.create();
        }
    }

    class MiDialogoPersonalizado extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialogo_layout, null);
            builder.setView(view);
            return builder.create();
        }
    }
}
