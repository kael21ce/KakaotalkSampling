package org.techtown.kakaotalksampling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {
    public static final String TAG = "CallReceiver";
    public static final String STAG = "CallService";

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.i(TAG, "onReceive() 메서드 호출됨.");
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            String callingNum = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.i(TAG, "Calling number: " + callingNum);

            //서비스로 인텐트 전송
            Intent serviceIntent = new Intent(context, CallService.class);
            serviceIntent.putExtra("mobile", callingNum);
            context.startService(serviceIntent);
            Log.i(STAG, "서비스 시작");
        }
    }
}