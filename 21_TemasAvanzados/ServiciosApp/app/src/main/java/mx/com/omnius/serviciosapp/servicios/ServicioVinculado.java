    package mx.com.omnius.serviciosapp.servicios;

    import android.app.Service;
    import android.content.Intent;
    import android.os.Binder;
    import android.os.IBinder;
    import android.os.SystemClock;
    import android.widget.Chronometer;
    import android.widget.Toast;

    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
    import java.util.Date;

    public class ServicioVinculado extends Service {
        private IBinder iBinder = new MiBinder();

        @Override
        public void onCreate() {
            super.onCreate();
            Toast.makeText(this, "Servicio vinculado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return iBinder;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            return Service.START_STICKY;
        }


        public String getTimestamp(){
            String hora;
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SS");
            hora = dateFormat.format(new Date());
            return hora;
        }

        public class MiBinder extends Binder {
            public ServicioVinculado getService(){
                return ServicioVinculado.this;
            }
        }
    }
