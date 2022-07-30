package org.techtown.kakaotalksampling;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CallService extends Service {
    public static final String STAG = "CallService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(STAG, "onCreate() 호출됨.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(STAG, "onStartCommand() 호출됨.");

        if (intent == null) {
            return Service.START_STICKY;
        } else {
            //전화번호 가져오기
            String number = intent.getStringExtra("number");
            Log.i(STAG, "Given Number: " + number);
        }

        return super.onStartCommand(intent, flags, startId);
    }


    public CallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}