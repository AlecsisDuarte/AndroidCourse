package mx.com.omnius.broadcastreceiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

public class Receptor extends BroadcastReceiver{
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String origen = intent.getExtras().getString("origen");
        if(origen != null){
            Toast toast = Toast.makeText(context, "Broadcast difundida desde :\n"+origen, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else{
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
                telephonyManager.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

            } catch (Exception e) {}
        }


    }

    private class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == 1) {
                String msg = "Nueva llamada del número : "+incomingNumber;
                Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
}
